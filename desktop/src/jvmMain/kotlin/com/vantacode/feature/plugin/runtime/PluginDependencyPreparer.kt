package com.vantacode.feature.plugin.runtime

import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.domain.container.CommandEngine
import com.vantacode.feature.plugin.data.PluginPackageReader
import com.vantacode.feature.plugin.domain.PluginRuntime
import javax.inject.Inject
import javax.inject.Singleton

/** 依赖准备结果；UI 按类型展示状态与原因。 */
sealed interface PluginDependencyResult {
    /** 依赖已就绪；[hash] 是依赖输入文件的摘要，文件变化后需要重新准备。 */
    data class Ready(val hash: String) : PluginDependencyResult

    /** 缺少 node/npm 或 python，引导用户先安装解释器。 */
    data class MissingRuntime(val message: String) : PluginDependencyResult

    /** 安装命令失败，[message] 带截断后的输出。 */
    data class Failed(val message: String, val code: String? = null) : PluginDependencyResult
}

/**
 * 在插件自己的安装目录里准备依赖（桌面端直接在本机 shell 执行）。
 *
 * 命令完全由宿主构造，不接受插件传入；工作目录就是插件安装目录的绝对路径，
 * 所以 npm 的 `node_modules` 与 pip 的 `.venv` 都留在插件包目录里。
 */
@Singleton
class PluginDependencyPreparer @Inject constructor(
    private val commandEngine: CommandEngine
) {

    suspend fun prepare(runtime: PluginRuntime): PluginDependencyResult {
        val dependency = runtime.dependencies
            ?: return PluginDependencyResult.Ready("")
        val installDir = runtime.installDir.absolutePath
        commandEngine.notReadyHint()?.let {
            return PluginDependencyResult.MissingRuntime(it)
        }

        val missing = if (dependency.tool == "npm") checkCommands(installDir, "node", "npm")
        else checkCommands(installDir, "python")
        if (missing.isNotEmpty()) {
            return PluginDependencyResult.MissingRuntime(
                "缺少 ${missing.joinToString("、")}，请先安装后重试。"
            )
        }

        val file = shellQuote(dependency.file.absolutePath)
        // 工作目录已由 runCommandSyncWithExit 的第 2 参设为插件安装目录，无需再 cd。
        val command = when (dependency.tool) {
            "npm" -> "if [ -f package-lock.json ]; then npm ci; else npm install --no-audit --no-fund; fi"
            "pip" -> "python -m venv .venv && .venv/bin/pip install --disable-pip-version-check -r $file"
            else -> return PluginDependencyResult.Failed("不支持的依赖工具: ${dependency.tool}")
        }

        FileLogger.i(TAG, "[${runtime.pluginId}] 准备依赖: ${dependency.tool} ${dependency.file.name}")
        val result = runCatching {
            commandEngine.runCommandSyncWithExit(command, installDir, PREPARE_TIMEOUT_MS)
        }.getOrElse { error ->
            return PluginDependencyResult.Failed("依赖安装失败: ${error.message ?: error.javaClass.simpleName}")
        }

        if (result.exitCode != 0) {
            FileLogger.w(TAG, "[${runtime.pluginId}] 依赖安装失败 exitCode=${result.exitCode}")
            return PluginDependencyResult.Failed(tail(result.output))
        }
        val hash = sha256(dependency.file)
        FileLogger.i(TAG, "[${runtime.pluginId}] 依赖准备完成")
        return PluginDependencyResult.Ready(hash)
    }

    /** 跨平台用 `--version` 探测解释器是否存在（Windows 下 `command -v` 不可用）。 */
    private suspend fun checkCommands(installDir: String, vararg names: String): List<String> {
        val missing = mutableListOf<String>()
        names.forEach { name ->
            val result = runCatching {
                commandEngine.runCommandSyncWithExit("$name --version", installDir, CHECK_TIMEOUT_MS)
            }.getOrNull()
            if (result == null || result.exitCode != 0) missing += name
        }
        return missing
    }

    private fun sha256(file: java.io.File): String =
        PluginPackageReader.sha256Hex(file.readBytes())

    private fun tail(output: String): String {
        val lines = output.trim().lines().takeLast(MAX_TAIL_LINES).joinToString("\n")
        return lines.take(MAX_TAIL_CHARS).ifBlank { "依赖安装失败，未产生输出" }
    }

    private fun shellQuote(value: String): String = "'" + value.replace("'", "'\\''") + "'"

    private companion object {
        const val TAG = "PluginDeps"
        const val PREPARE_TIMEOUT_MS = 10 * 60_000L
        const val CHECK_TIMEOUT_MS = 30_000L
        const val MAX_TAIL_LINES = 20
        const val MAX_TAIL_CHARS = 2_000
    }
}
