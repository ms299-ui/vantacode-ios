package com.vantacode.feature.plugin.runtime

import com.vantacode.feature.plugin.domain.PluginHostPaths
import com.vantacode.feature.plugin.domain.PluginManager
import com.vantacode.feature.terminal.domain.TerminalSessionProvider
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.CodingErrorAction
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** 插件终端调用失败；[code] 是稳定的错误码，页面桥与进程 API 原样转交。 */
class PluginTerminalException(val code: String, override val message: String) : Exception(message)

/** 一次插件终端轮询的结果；[offset] 是下一次轮询应传入的偏移。 */
data class PluginTerminalPoll(
    val output: String,
    val offset: Long,
    val state: String,
    val exitCode: Int?,
    val truncated: Boolean
)

/**
 * 有状态的 UTF-8 流式解码。终端按字节块回调，一个多字节字符可能被拆到相邻两块；
 * [decode] 把块尾不完整的序列留到下一块补齐，[flush] 在流结束时吐出残留字节。
 */
internal class Utf8StreamDecoder {
    private val decoder = Charsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPLACE)
        .onUnmappableCharacter(CodingErrorAction.REPLACE)
    private var pending = ByteArray(0)
    private var finished = false

    fun decode(bytes: ByteArray): String {
        if (finished) return ""
        val input = ByteBuffer.wrap(if (pending.isEmpty()) bytes else pending + bytes)
        pending = ByteArray(0)
        if (!input.hasRemaining()) return ""

        val out = StringBuilder(input.remaining())
        val chars = CharBuffer.allocate(CHARS_CHUNK)
        while (true) {
            val result = decoder.decode(input, chars, false)
            chars.flip()
            out.append(chars)
            chars.clear()
            if (result.isOverflow()) continue
            if (input.hasRemaining()) {
                pending = ByteArray(input.remaining()).also { input.get(it) }
            }
            return out.toString()
        }
    }

    fun flush(): String {
        if (finished) return ""
        finished = true

        val out = StringBuilder()
        val chars = CharBuffer.allocate(CHARS_CHUNK)
        val input = ByteBuffer.wrap(pending)
        pending = ByteArray(0)
        while (true) {
            val result = decoder.decode(input, chars, true)
            chars.flip()
            out.append(chars)
            chars.clear()
            if (!result.isOverflow()) break
        }
        while (true) {
            val result = decoder.flush(chars)
            chars.flip()
            out.append(chars)
            chars.clear()
            if (!result.isOverflow()) break
        }
        decoder.reset()
        return out.toString()
    }

    private companion object {
        const val CHARS_CHUNK = 1024
    }
}

/**
 * 管理插件创建的共享终端标签。桌面端没有 Android 的 `startPluginCommand/tabOutput/tabStatus`
 * 扩展方法，这里映射到 [TerminalSessionProvider] 现有的 `startBackgroundCommand/getTabOutput/listTabs`：
 * 输出用快照增量轮询累积到有界缓冲，插件按 offset 拉取增量，避免页面桥长连接。
 */
@Singleton
class PluginTerminalRunner @Inject constructor(
    private val provider: TerminalSessionProvider,
    private val pluginManager: PluginManager
) {

    private class Run(
        val pluginId: String,
        val tabId: String,
        val title: String,
        val command: String
    ) {
        val buffer = StringBuilder()
        var totalChars: Long = 0
        var cancelled = false
        /** 上一次读到的 getTabOutput 快照长度，用于计算增量。 */
        var snapshotLen = 0

        fun appendText(text: String) {
            if (text.isEmpty()) return
            synchronized(this) {
                buffer.append(text)
                totalChars += text.length
                if (buffer.length > MAX_BUFFER_CHARS) {
                    buffer.delete(0, buffer.length - MAX_BUFFER_CHARS)
                }
            }
        }

        /** 拉取快照增量并累积到缓冲；桌面 getTabOutput 返回 emulator 屏幕全文快照。 */
        fun pollSnapshot(snapshot: String?) {
            if (snapshot.isNullOrEmpty()) return
            if (snapshot.length <= snapshotLen) return
            appendText(snapshot.substring(snapshotLen))
            snapshotLen = snapshot.length
        }
    }

    private val runs = ConcurrentHashMap<String, Run>()

    /** 新建插件命令标签；命令结束后标签保留为已结束，由插件调用 [close]。 */
    suspend fun start(pluginId: String, command: String, title: String?): String {
        requireActive(pluginId)
        if (command.isBlank()) throw PluginTerminalException("PARAM_INVALID", "empty command")
        if (command.length > PluginHostPaths.MAX_COMMAND_CHARS) {
            throw PluginTerminalException("PARAM_INVALID", "command too long")
        }
        val tabId = provider.startBackgroundCommand(
            command = command,
            title = title,
            notify = false,
            sourceSessionId = pluginId
        )
        runs[tabId] = Run(pluginId, tabId, title ?: tabId, command)
        return tabId
    }

    /** 从 [offset] 读取新增输出；输出缓冲超出上限时 [PluginTerminalPoll.truncated] 为 true。 */
    suspend fun poll(pluginId: String, tabId: String, offset: Long): PluginTerminalPoll {
        val run = owned(pluginId, tabId)
        run.pollSnapshot(provider.getTabOutput(tabId))
        val running = provider.listTabs().firstOrNull { it.id == tabId }?.running == true
        val from = offset.coerceAtLeast(0)
        var dropped = 0L
        var output = ""
        var next = 0L
        synchronized(run) {
            dropped = (run.totalChars - run.buffer.length).coerceAtLeast(0)
            val start = (from - dropped).coerceIn(0, run.buffer.length.toLong()).toInt()
            output = run.buffer.substring(start)
            next = run.totalChars
        }
        val state = when {
            run.cancelled -> "cancelled"
            running -> "running"
            else -> "finished"
        }
        return PluginTerminalPoll(
            output = output,
            offset = next,
            state = state,
            exitCode = null,
            truncated = from < dropped
        )
    }

    /** 发送 Ctrl-C；超时仍未退出则关闭标签。返回是否已发出停止。 */
    suspend fun stop(pluginId: String, tabId: String): Boolean {
        val run = owned(pluginId, tabId)
        run.cancelled = true
        provider.writeBytesToTab(tabId, 0x03)
        val stopped = withTimeoutOrNull(STOP_TIMEOUT_MS) {
            var running = true
            while (running) {
                running = provider.listTabs().firstOrNull { it.id == tabId }?.running == true
                if (running) delay(100)
            }
            true
        } ?: false
        if (!stopped) {
            provider.closeTab(tabId)
        }
        return true
    }

    /** 向自己的标签发送输入；[appendNewline] 为 true 时由终端补回车。 */
    suspend fun write(pluginId: String, tabId: String, text: String, appendNewline: Boolean = true): Boolean {
        owned(pluginId, tabId)
        if (text.isEmpty()) throw PluginTerminalException("PARAM_INVALID", "empty input")
        if (text.length > MAX_INPUT_CHARS) throw PluginTerminalException("PARAM_INVALID", "input too long")
        return if (appendNewline) provider.sendInput(tabId, text, true) else provider.writeToTab(tabId, text)
    }

    /** 列出当前插件自己创建的标签及其运行状态。 */
    suspend fun list(pluginId: String): JsonElement {
        val mine = runs.values.filter { it.pluginId == pluginId }.sortedBy { it.tabId }
        val runningIds = provider.listTabs().filter { it.running }.map { it.id }.toSet()
        val entries = mine.map { run ->
            val state = when {
                run.cancelled -> "cancelled"
                run.tabId in runningIds -> "running"
                else -> "finished"
            }
            buildJsonObject {
                put("tabId", run.tabId)
                put("title", run.title)
                put("command", run.command)
                put("state", state)
            }
        }
        return JsonArray(entries)
    }

    /** 关闭标签并释放缓冲；标签已结束也可以调用。 */
    suspend fun close(pluginId: String, tabId: String): Boolean {
        owned(pluginId, tabId)
        runs.remove(tabId)
        return provider.closeTab(tabId)
    }

    /** 插件停用或卸载时关闭它创建的全部标签。 */
    fun closePlugin(pluginId: String) {
        runs.values
            .filter { it.pluginId == pluginId }
            .map { it.tabId }
            .forEach { tabId ->
                runs.remove(tabId)
                provider.closeTab(tabId)
            }
    }

    private fun owned(pluginId: String, tabId: String): Run {
        val run = runs[tabId]
            ?: throw PluginTerminalException("TAB_NOT_FOUND", "unknown terminal tab: $tabId")
        if (run.pluginId != pluginId) {
            throw PluginTerminalException("TAB_FORBIDDEN", "terminal tab belongs to another plugin")
        }
        return run
    }

    private suspend fun requireActive(pluginId: String) {
        pluginManager.ensureLoaded()
        val active = pluginManager.plugins.value.any { it.id == pluginId && it.isActive }
        if (!active) throw PluginTerminalException("PLUGIN_INACTIVE", "plugin is not active: $pluginId")
    }

    private companion object {
        /** 每个插件标签保留的输出上限；超出部分丢弃最旧内容并标记 truncated。 */
        const val MAX_BUFFER_CHARS = 128 * 1024
        const val STOP_TIMEOUT_MS = 2_000L

        /** 单次写入的字符上限。 */
        const val MAX_INPUT_CHARS = 4000
    }
}
