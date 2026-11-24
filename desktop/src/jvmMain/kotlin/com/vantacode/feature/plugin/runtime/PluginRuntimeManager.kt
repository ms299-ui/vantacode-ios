package com.vantacode.feature.plugin.runtime

import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.domain.container.CommandEngine
import com.vantacode.feature.agent.domain.container.ContainerInstaller
import com.vantacode.feature.agent.domain.container.NativeCommandEngine
import com.vantacode.feature.plugin.domain.PluginHostException
import com.vantacode.feature.plugin.domain.PluginHostPaths
import com.vantacode.feature.plugin.domain.PluginManager
import com.vantacode.feature.plugin.domain.PluginRuntime
import com.vantacode.feature.plugin.domain.PluginRuntimeTypes
import com.vantacode.feature.settings.data.repository.ExecutionMode
import com.vantacode.feature.settings.data.repository.ExecutionModeHolder
import com.vantacode.feature.workspace.data.repository.WorkspaceRepository
import com.vantacode.feature.workspace.domain.FileAccessProvider
import com.vantacode.feature.workspace.domain.PathHomeResolver
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.put

/**
 * 插件运行时的宿主门面：按插件版本管理容器内进程，把工具/hook 调用转成 JSON-RPC。
 *
 * 进程在本地容器中运行（SSH 模式下同样在手机端），cwd 是插件自己的安装目录，
 * 因此可以直接读取包内文件、`node_modules` 或虚拟环境。插件主动发来的 host API
 * 请求复用 [PluginHostPaths]，与页面桥的文件限制保持一致。
 */
@Singleton
class PluginRuntimeManager @Inject constructor(
    private val pluginManager: PluginManager,
    private val installer: ContainerInstaller,
    private val engine: NativeCommandEngine,
    private val fileAccess: FileAccessProvider,
    private val homeResolver: PathHomeResolver,
    private val commandEngine: CommandEngine,
    private val workspaceRepository: WorkspaceRepository,
    private val executionModeHolder: ExecutionModeHolder,
    private val eventBus: PluginEventBus,
    private val notifier: PluginNotifier,
    private val terminalRunner: PluginTerminalRunner,
    private val shizukuRunner: PluginShizukuRunner,
    private val tipManager: PluginTipManager,
    private val chatService: PluginChatService,
    private val modelService: PluginModelService,
    private val secretStore: PluginSecretStore,
    private val systemService: PluginSystemService,
    private val introspectionService: PluginIntrospectionService,
    private val toolService: PluginToolService,
    private val httpService: PluginHttpService,
    private val providerRepository: com.vantacode.feature.settings.domain.repository.AIProviderRepository
) {

    private class Entry(
        val pluginId: String,
        val key: String,
        val transport: PluginProcessTransport,
        var lastUsed: Long,
        var resident: Boolean
    )

    private val processes = ConcurrentHashMap<String, Entry>()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // 桌面端没有 overlay（Android 专有）；overlay.* host API 统一返回 UNSUPPORTED。
        scope.launch {
            while (isActive) {
                delay(IDLE_CHECK_MS)
                pruneIdle()
            }
        }
    }

    suspend fun invoke(
        pluginId: String,
        version: String,
        runtime: PluginRuntime,
        handler: String,
        args: JsonObject,
        method: String = "tool.invoke",
        timeoutMs: Long = TOOL_TIMEOUT_MS
    ): JsonElement {
        requireActive(pluginId)
        val entry = getOrCreate(pluginId, version, runtime)
        if (!isActive(pluginId)) {
            synchronized(processes) { processes.remove(entry.key, entry) }
            entry.transport.close()
            throw PluginProcessException("PLUGIN_INACTIVE", "[$pluginId] 插件已停用或卸载")
        }
        entry.lastUsed = System.currentTimeMillis()
        return try {
            entry.transport.request(
                method = method,
                params = buildJsonObject {
                    put("handler", handler)
                    put("args", args)
                },
                timeoutMs = timeoutMs
            )
        } catch (e: PluginProcessException) {
            if (e.code == "PLUGIN_EXITED" || (e.code == "PLUGIN_TIMEOUT" && !entry.resident)) {
                synchronized(processes) { processes.remove(entry.key, entry) }
                entry.transport.close()
            }
            throw e
        }
    }

    /** 插件版本替换后关闭旧版本进程，避免 resident 旧进程永远不回收。 */
    fun closeOtherVersions(pluginId: String, currentVersion: String) {
        val keepKey = "$pluginId@$currentVersion"
        synchronized(processes) {
            processes.entries
                .filter { (key, entry) -> entry.pluginId == pluginId && key != keepKey }
                .forEach { (key, entry) ->
                    processes.remove(key, entry)
                    entry.transport.close()
                }
        }
    }

    /** 插件停用、卸载或版本替换时关闭它的进程。 */
    fun closePlugin(pluginId: String) {
        synchronized(processes) {
            processes.entries
                .filter { (_, entry) -> entry.pluginId == pluginId }
                .forEach { (key, entry) ->
                    processes.remove(key, entry)
                    entry.transport.close()
                }
        }
    }

    private suspend fun getOrCreate(pluginId: String, version: String, runtime: PluginRuntime): Entry {
        val key = "$pluginId@$version"
        processes[key]?.let { entry ->
            entry.resident = runtime.resident
            return entry
        }
        commandEngine.notReadyHint()?.let {
            throw PluginProcessException("PLUGIN_RUNTIME_UNAVAILABLE", it)
        }
        ensureInterpreter(runtime)


        val dataDir = dataDirectory(pluginId)
        val wsRoot = workspaceRoot()
        // 上面的挂起调用期间可能已有并发调用创建了同一版本的进程。
        processes[key]?.let { entry ->
            entry.resident = runtime.resident
            return entry
        }
        var createdEntry: Entry? = null
        val transport = PluginProcessTransport(
            pluginId = pluginId,
            engine = engine,
            // 桌面端直接用插件安装目录的绝对路径作为子进程工作目录（无容器路径映射）。
            workingDir = runtime.installDir.absolutePath,
            program = runtime.command.firstOrNull()
                ?: throw PluginProcessException("PLUGIN_COMMAND_MISSING", "[$pluginId] 运行时没有启动命令"),
            programArgs = runtime.command.drop(1),
            extraEnv = mapOf(
                "AICODE_PLUGIN_ID" to pluginId,
                "AICODE_PLUGIN_VERSION" to version,
                "AICODE_DATA_DIR" to dataDir,
                "AICODE_WORKSPACE" to wsRoot
            ),
            onRequest = { method, params -> hostRequest(pluginId, runtime, method, params) },
            onNotification = { method, params -> handleNotification(pluginId, method, params) },
            onExit = {
                createdEntry?.let { entry ->
                    synchronized(processes) { processes.remove(key, entry) }
                }
            },
            closeOnTimeout = !runtime.resident
        )
        val entry = Entry(pluginId, key, transport, System.currentTimeMillis(), runtime.resident)
        createdEntry = entry
        val existing = synchronized(processes) { processes.putIfAbsent(key, entry) }
        if (existing != null) {
            transport.close()
            return existing
        }
        return entry
    }

    private suspend fun hostRequest(
        pluginId: String,
        runtime: PluginRuntime,
        method: String,
        params: JsonObject
    ): JsonElement {
        val paths = PluginHostPaths(
            fileAccess = fileAccess,
            homeResolver = homeResolver,
            workspaceRoot = ::workspaceRoot,
            pluginId = pluginId
        )
        return when (method) {
            "host.fs.read" -> hostCall { paths.read(PluginHostPaths.requireString(params, "path")) }
            "host.fs.write" -> hostCall {
                paths.write(
                    PluginHostPaths.requireString(params, "path"),
                    PluginHostPaths.requireString(params, "content")
                )
            }
            "host.fs.list" -> hostCall { paths.list(PluginHostPaths.requireString(params, "path")) }
            "host.fs.delete" -> hostCall { paths.delete(PluginHostPaths.requireString(params, "path")) }
            "host.fs.stat" -> hostCall { paths.stat(PluginHostPaths.requireString(params, "path")) }
            "host.fs.mkdir" -> hostCall { paths.mkdir(PluginHostPaths.requireString(params, "path")) }
            "host.fs.rename" -> hostCall {
                paths.rename(
                    PluginHostPaths.requireString(params, "from"),
                    PluginHostPaths.requireString(params, "to")
                )
            }
            "host.fs.copy" -> hostCall {
                paths.copy(
                    PluginHostPaths.requireString(params, "from"),
                    PluginHostPaths.requireString(params, "to"),
                    (params["overwrite"] as? JsonPrimitive)?.booleanOrNull ?: false
                )
            }
            "host.fs.append" -> hostCall {
                paths.append(
                    PluginHostPaths.requireString(params, "path"),
                    PluginHostPaths.requireString(params, "content")
                )
            }
            "host.fs.readBinary" -> hostCall {
                paths.readBinary(PluginHostPaths.requireString(params, "path"))
            }
            "host.fs.writeBinary" -> hostCall {
                paths.writeBinary(
                    PluginHostPaths.requireString(params, "path"),
                    PluginHostPaths.requireString(params, "base64")
                )
            }
            // 桌面端没有 overlay / Shizuku 后端，host API 以 UNSUPPORTED 明确失败，
            // 由 hostCall 统一转成 JSON-RPC 错误码回给插件。
            "host.app.isForeground" -> JsonPrimitive(true)
            "host.bash" -> bash(params)
            "host.notify" -> notifier.notify(pluginId, params)
            "host.shizuku.exec" -> hostCall<JsonElement> {
                throw PluginHostException("UNSUPPORTED", "桌面端不支持 host.shizuku.exec")
            }
            "host.overlay.create" -> hostCall<JsonElement> {
                throw PluginHostException("UNSUPPORTED", "桌面端不支持 host.overlay.create")
            }
            "host.overlay.close" -> hostCall<JsonElement> {
                throw PluginHostException("UNSUPPORTED", "桌面端不支持 host.overlay.close")
            }
            "host.overlay.postMessage" -> hostCall<JsonElement> {
                throw PluginHostException("UNSUPPORTED", "桌面端不支持 host.overlay.postMessage")
            }
            "host.overlay.update" -> hostCall<JsonElement> {
                throw PluginHostException("UNSUPPORTED", "桌面端不支持 host.overlay.update")
            }
            "host.tip" -> tip(pluginId, params)
            "host.terminal.run" -> terminalRun(pluginId, params)
            "host.terminal.poll" -> terminalPoll(pluginId, params)
            "host.terminal.stop" -> terminalStop(pluginId, params, close = false)
            "host.terminal.close" -> terminalStop(pluginId, params, close = true)
            "host.terminal.write" -> terminalWrite(pluginId, params)
            "host.terminal.list" -> terminalCall { terminalRunner.list(pluginId) }
            "host.chat.listSessions" -> hostCall { chatService.listSessions(pluginId, params) }
            "host.chat.createSession" -> hostCall { chatService.createSession(pluginId, params) }
            "host.chat.listMessages" -> hostCall { chatService.listMessages(pluginId, params) }
            "host.chat.sendMessage" -> hostCall { chatService.sendMessage(pluginId, params) }
            "host.chat.setDraft" -> hostCall { chatService.setDraft(pluginId, params) }
            "host.chat.stop" -> hostCall { chatService.stop(pluginId, params) }
            "host.chat.openSession" -> hostCall { chatService.openSession(pluginId, params) }
            "host.chat.getSession" -> hostCall { chatService.getSession(pluginId, params) }
            "host.chat.renameSession" -> hostCall { chatService.renameSession(pluginId, params) }
            "host.chat.pinSession" -> hostCall { chatService.pinSession(pluginId, params) }
            "host.chat.deleteSession" -> hostCall { chatService.deleteSession(pluginId, params) }
            "host.chat.getRunState" -> hostCall { chatService.getRunState(pluginId, params) }
            "host.chat.waitForRun" -> hostCall { chatService.waitForRun(pluginId, params) }
            "host.chat.listApprovals" -> hostCall { chatService.listApprovals(pluginId, params) }
            "host.chat.resolveToolApproval" -> hostCall { chatService.resolveToolApproval(pluginId, params) }
            "host.chat.resolveQuestion" -> hostCall { chatService.resolveQuestion(pluginId, params) }
            "host.chat.resolvePlanApproval" -> hostCall { chatService.resolvePlanApproval(pluginId, params) }
            "host.model.listProviders" -> hostCall { modelService.listProviders(pluginId) }
            "host.model.listModels" -> hostCall { modelService.listModels(pluginId, params) }
            "host.model.create" -> hostCall { modelService.create(pluginId, params) }
            "host.model.close" -> hostCall { modelService.close(pluginId, params) }
            "host.model.start" -> hostCall { modelService.start(pluginId, params, PROCESS_ORIGIN) }
            "host.model.read" -> hostCall { modelService.read(pluginId, params) }
            "host.model.cancel" -> hostCall { modelService.cancel(pluginId, params) }
            "host.clipboard.read" -> hostCall { systemService.clipboardRead(pluginId) }
            "host.clipboard.write" -> hostCall {
                systemService.clipboardWrite(pluginId, PluginHostPaths.requireString(params, "text"))
            }
            "host.share.text" -> hostCall {
                systemService.shareText(
                    pluginId,
                    PluginHostPaths.requireString(params, "text"),
                    (params["title"] as? JsonPrimitive)?.contentOrNull
                )
            }
            "host.ui.openUrl" -> hostCall {
                systemService.openUrl(pluginId, PluginHostPaths.requireString(params, "url"))
            }
            "host.app.info" -> hostCall { systemService.appInfo(pluginId) }
            "host.log" -> hostCall {
                systemService.log(
                    pluginId,
                    PluginHostPaths.requireString(params, "level"),
                    PluginHostPaths.requireString(params, "message"),
                    (params["tag"] as? JsonPrimitive)?.contentOrNull
                )
            }
            "host.secrets.get" -> hostCall {
                secretStore.get(pluginId, PluginHostPaths.requireString(params, "key"))
            }
            "host.secrets.set" -> hostCall {
                secretStore.set(
                    pluginId,
                    PluginHostPaths.requireString(params, "key"),
                    PluginHostPaths.requireString(params, "value")
                )
            }
            "host.secrets.delete" -> hostCall {
                secretStore.delete(pluginId, PluginHostPaths.requireString(params, "key"))
            }
            "host.secrets.list" -> hostCall { secretStore.list(pluginId) }
            // 插件把拉取到的模型同步进宿主供应商记录（VantaGate 等网关插件使用）。
            // 供应商不存在时自动创建，指向插件常驻的本地网关（默认 127.0.0.1:8888/v1，
            // 可由 params.baseUrl 覆盖），这样才能做到「自动同步模型到 AI 供应商」。
            "host.provider.syncModels" -> hostCall<JsonElement> {
                val providerId = PluginHostPaths.requireString(params, "providerId")
                val models = (params["models"] as? kotlinx.serialization.json.JsonArray)
                    ?.mapNotNull { (it as? kotlinx.serialization.json.JsonPrimitive)?.contentOrNull }
                    ?: emptyList()
                val baseUrl = (params["baseUrl"] as? kotlinx.serialization.json.JsonPrimitive)?.contentOrNull
                    ?: "http://127.0.0.1:8888/v1"
                val existing = providerRepository.getProviderById(providerId)
                if (existing == null) {
                    val displayName = (params["name"] as? kotlinx.serialization.json.JsonPrimitive)?.contentOrNull
                        ?: providerId
                    providerRepository.saveProvider(
                        com.vantacode.feature.settings.domain.model.AIProviderConfig(
                            id = providerId,
                            name = displayName,
                            type = com.vantacode.feature.settings.domain.model.ProviderType.OPENAI,
                            apiKey = "vantagate",
                            baseUrl = baseUrl,
                            defaultModel = models.firstOrNull().orEmpty(),
                            models = models
                        )
                    )
                } else {
                    providerRepository.updateModels(providerId, models)
                }
                kotlinx.serialization.json.buildJsonObject {
                    put("count", kotlinx.serialization.json.JsonPrimitive(models.size))
                }
            }
            "host.tools.list" -> hostCall {
                introspectionService.listTools(
                    pluginId,
                    includeSchema = (params["includeSchema"] as? JsonPrimitive)?.booleanOrNull ?: false
                )
            }
            "host.tools.execute" -> hostCall { toolService.execute(pluginId, params) }
            "host.tools.pending" -> hostCall { toolService.pending(pluginId, params) }
            "host.tools.resolve" -> hostCall { toolService.resolve(pluginId, params) }
            "host.mcp.status" -> hostCall { introspectionService.mcpStatus(pluginId) }
            "host.http.request" -> hostCall { httpService.request(pluginId, params) }
            else -> throw PluginProcessException("METHOD_UNKNOWN", "unknown host method: $method")
        }
    }

    private fun tip(pluginId: String, params: JsonObject): JsonElement = try {
        tipManager.show(pluginId, params)
    } catch (e: PluginTipException) {
        throw PluginProcessException(e.code, e.message.orEmpty())
    }

    private suspend fun bash(params: JsonObject): JsonElement {
        val command = PluginHostPaths.requireString(params, "command")
        if (command.isBlank()) throw PluginProcessException("PARAM_INVALID", "empty command")
        if (command.length > PluginHostPaths.MAX_COMMAND_CHARS) {
            throw PluginProcessException("PARAM_INVALID", "command too long")
        }
        val timeoutSeconds = (params["timeout"] as? JsonPrimitive)?.longOrNull
            ?: PluginHostPaths.DEFAULT_BASH_TIMEOUT_SECONDS
        val timeoutMs = timeoutSeconds.coerceIn(1L, PluginHostPaths.MAX_BASH_TIMEOUT_SECONDS) * 1000L
        val result = commandEngine.runCommandSyncWithExit(command, workspaceRepository.currentPath(), timeoutMs)
        return buildJsonObject {
            put("output", result.output)
            put("exitCode", result.exitCode)
            put("truncated", result.outputTruncated)
        }
    }

    private suspend fun terminalRun(pluginId: String, params: JsonObject): JsonElement {
        val command = PluginHostPaths.requireString(params, "command").trim()
        val title = (params["title"] as? JsonPrimitive)?.contentOrNull
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.take(MAX_TERMINAL_TITLE_CHARS)
        return try {
            val tabId = terminalRunner.start(pluginId, command, title)
            buildJsonObject {
                put("tabId", tabId)
                put("state", "running")
            }
        } catch (e: PluginTerminalException) {
            throw PluginProcessException(e.code, e.message)
        }
    }

    private suspend fun terminalPoll(pluginId: String, params: JsonObject): JsonElement {
        val tabId = PluginHostPaths.requireString(params, "tabId")
        val offset = (params["offset"] as? JsonPrimitive)?.longOrNull ?: 0L
        return try {
            val poll = terminalRunner.poll(pluginId, tabId, offset)
            buildJsonObject {
                put("output", poll.output)
                put("offset", poll.offset)
                put("state", poll.state)
                poll.exitCode?.let { put("exitCode", it) }
                put("truncated", poll.truncated)
            }
        } catch (e: PluginTerminalException) {
            throw PluginProcessException(e.code, e.message)
        }
    }

    private suspend fun terminalStop(pluginId: String, params: JsonObject, close: Boolean): JsonElement {
        val tabId = PluginHostPaths.requireString(params, "tabId")
        try {
            if (close) terminalRunner.close(pluginId, tabId) else terminalRunner.stop(pluginId, tabId)
        } catch (e: PluginTerminalException) {
            throw PluginProcessException(e.code, e.message)
        }
        return buildJsonObject { put("ok", true) }
    }

    private suspend fun <T> hostCall(block: suspend () -> T): T = try {
        block()
    } catch (e: PluginHostException) {
        throw PluginProcessException(e.code, e.message)
    }

    private suspend fun terminalCall(block: suspend () -> JsonElement): JsonElement = try {
        block()
    } catch (e: PluginTerminalException) {
        throw PluginProcessException(e.code, e.message)
    }

    private suspend fun terminalWrite(pluginId: String, params: JsonObject): JsonElement {
        val tabId = PluginHostPaths.requireString(params, "tabId")
        val text = PluginHostPaths.requireString(params, "text")
        val appendNewline = (params["appendNewline"] as? JsonPrimitive)?.booleanOrNull ?: true
        val written = try {
            terminalRunner.write(pluginId, tabId, text, appendNewline)
        } catch (e: PluginTerminalException) {
            throw PluginProcessException(e.code, e.message)
        }
        return buildJsonObject { put("ok", written) }
    }

    private fun isActive(pluginId: String): Boolean {
        pluginManager.ensureLoaded()
        return pluginManager.plugins.value.any { it.id == pluginId && it.isActive }
    }

    private fun requireActive(pluginId: String) {
        if (!isActive(pluginId)) {
            throw PluginProcessException("PLUGIN_INACTIVE", "[$pluginId] 插件已停用或卸载")
        }
    }

    private fun handleNotification(pluginId: String, method: String, params: JsonObject) {
        if (method != "plugin.event") {
            FileLogger.d(TAG, "[$pluginId] 忽略插件通知: $method")
            return
        }
        eventBus.publish(pluginId, params)
    }

    /**
     * 启动前确认解释器存在。
     *
     * 桌面端没有内置容器，Node/Python 由用户在宿主机安装。缺了解释器会让进程秒退，
     * 用户只看到「stdout 已关闭」。这里用跨平台的 `--version` 探测提前拦下并给出提示
     * （Android 的 `command -v` 在 Windows 下不可用）。
     */
    private suspend fun ensureInterpreter(runtime: PluginRuntime) {
        val (probe, hint) = when (runtime.type) {
            PluginRuntimeTypes.JAVASCRIPT -> "node --version" to "安装 Node.js"
            PluginRuntimeTypes.PYTHON -> "python --version" to "安装 Python"
            else -> return
        }
        val present = runCatching {
            commandEngine.runCommandSyncWithExit(probe, null, INTERPRETER_CHECK_TIMEOUT_MS).exitCode == 0
        }.getOrDefault(true)
        if (present) return
        throw PluginProcessException(
            "PLUGIN_RUNTIME_MISSING",
            "插件需要 $probe 对应的解释器，当前系统里没有。请先$hint，完成后重试。"
        )
    }

    /**
     * 插件数据目录在当前执行环境中的真实路径，并确保目录存在。本地落在
     * `/…/.muse/plugins/data/<id>`（与宿主 `museDir/plugins/data/<id>` 同一份），远程落在服务器同名路径。
     */
    private suspend fun dataDirectory(pluginId: String): String {
        val path = PluginHostPaths.dataRoot(homeResolver, pluginId)
        fileAccess.mkdirs(path)
        return path
    }

    /** 当前工作区在插件可见路径空间里的根：远程用远端绝对路径，本地用容器内 `$HOME/workspace`。 */
    private fun workspaceRoot(): String =
        if (executionModeHolder.currentMode() == ExecutionMode.REMOTE_SSH) {
            workspaceRepository.currentPath()
        } else {
            homeResolver.home().trimEnd('/') + "/workspace"
        }

    private fun pruneIdle() {
        val now = System.currentTimeMillis()
        synchronized(processes) {
            processes.entries
                .filter { !it.value.resident && now - it.value.lastUsed > IDLE_TIMEOUT_MS }
                .forEach { (key, entry) ->
                    processes.remove(key, entry)
                    entry.transport.close()
                }
        }
    }

    private companion object {
        const val TAG = "PluginRuntime"
        const val TOOL_TIMEOUT_MS = 120_000L
        const val IDLE_TIMEOUT_MS = 10 * 60_000L
        const val IDLE_CHECK_MS = 60_000L
        const val MAX_TERMINAL_TITLE_CHARS = 120
        const val INTERPRETER_CHECK_TIMEOUT_MS = 15_000L
        const val PROCESS_ORIGIN = "process"
    }
}
