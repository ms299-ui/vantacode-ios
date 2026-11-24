/*
 * VantaCode Desktop — native command engine: runs commands on this machine's real
 * shell (Git Bash on Windows when present, otherwise PowerShell/cmd). Replaces the
 * Android PRoot container backend; SSH remote execution is untouched.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.container

import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.platform.PlatformInfo
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.terminal.domain.LocalShell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicBoolean

/**
 * [CommandEngine] backed by the local machine's native shell. Always "installed";
 * [ensureInstalled] is a no-op. Mirrors [LinuxContainerEngine]'s native-fallback
 * semantics (merged stderr, head+tail bounded capture, timeout destroys the process).
 */
class NativeCommandEngine(
    private val context: DesktopContext = DesktopContext.instance
) : CommandEngine {

    private companion object {
        const val TAG = "NativeCommandEngine"
    }

    override val initProgress: StateFlow<ContainerInitState> =
        MutableStateFlow(ContainerInitState.Ready).asStateFlow()

    override fun runCommandStream(
        command: String,
        projectPath: String?,
        timeoutMs: Long
    ): Flow<CommandEvent> = flow {
        val process = startProcess(command, projectPath)
        val killedByWatchdog = AtomicBoolean(false)
        val watchdog = kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
            kotlinx.coroutines.delay(timeoutMs)
            if (process.isAlive) {
                killedByWatchdog.set(true)
                FileLogger.w(TAG, "命令超时 ${timeoutMs}ms，强制结束: ${sanitizeCommandForLog(command)}")
                runCatching { process.destroyForcibly() }
            }
        }
        try {
            val reader = process.inputStream.bufferedReader(Charset.forName("UTF-8"))
            var line = reader.readLine()
            while (line != null) {
                emit(CommandEvent.Line(line))
                line = reader.readLine()
            }
            val code = runCatching { process.waitFor() }.getOrNull()
            if (killedByWatchdog.get()) {
                emit(CommandEvent.Line("[命令执行超时：超过 ${timeoutMs}ms 已被强制终止]"))
            }
            emit(CommandEvent.Exit(if (killedByWatchdog.get()) null else code))
        } finally {
            watchdog.cancelAndJoin()
            runCatching { process.destroy() }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun runCommandSync(command: String, projectPath: String?, timeoutMs: Long): String =
        runCommandSyncDetailed(command, projectPath, timeoutMs, unbounded = false).output

    override suspend fun runCommandSyncWithExit(command: String, projectPath: String?, timeoutMs: Long): CommandResult =
        runCommandSyncDetailed(command, projectPath, timeoutMs, unbounded = false)

    override suspend fun runCommandSyncUnbounded(command: String, projectPath: String?, timeoutMs: Long): CommandResult =
        runCommandSyncDetailed(command, projectPath, timeoutMs, unbounded = true)

    override suspend fun runCommandSyncIfReady(command: String, projectPath: String?, timeoutMs: Long): CommandResult? =
        runCommandSyncDetailed(command, projectPath, timeoutMs, unbounded = false)

    override fun isContainerInstalled(): Boolean = true

    override fun isProvisioned(): Boolean = true

    override fun defaultShell(): String = LocalShell.resolve().interactiveArgv.first()

    override suspend fun ensureInstalled() = Unit

    /**
     * Spawn a stdio MCP server as a child process (stdout = JSON-RPC stream, stderr = server logs,
     * both left separate per MCP transport requirements). On desktop there is no container; the
     * server runs on this machine with the user's PATH.
     */
    fun startStdioProcess(
        program: String,
        programArgs: List<String>,
        projectPath: String?,
        extraEnv: Map<String, String>? = null
    ): Process {
        val argv = listOf(program) + programArgs
        val builder = ProcessBuilder(argv)
        if (projectPath != null) {
            val dir = File(projectPath)
            if (dir.isDirectory) builder.directory(dir)
        }
        builder.redirectErrorStream(false)
        builder.environment().putAll(LocalShell.environment(context) + (extraEnv ?: emptyMap()))
        // MCP stdio: no stdin redirect — the client owns the pipe.
        FileLogger.d(TAG, "启动 MCP stdio server: ${argv.joinToString(" ")} cwd=${builder.directory()?.path}")
        return builder.start()
    }

    private suspend fun runCommandSyncDetailed(
        command: String,
        projectPath: String?,
        timeoutMs: Long,
        unbounded: Boolean
    ): CommandResult = withContext(Dispatchers.IO) {
        val process = try {
            startProcess(command, projectPath)
        } catch (e: IOException) {
            FileLogger.e(TAG, "启动命令失败: ${sanitizeCommandForLog(command)}", e)
            return@withContext CommandResult("[命令启动失败: ${e.message}]", null, outputTruncated = false)
        }
        val output = if (unbounded) BoundedOutput.hardCapped(MAX_UNBOUNDED_CHARS) else BoundedOutput()
        val killedByWatchdog = AtomicBoolean(false)
        val watchdog = kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
            kotlinx.coroutines.delay(timeoutMs)
            if (process.isAlive) {
                killedByWatchdog.set(true)
                FileLogger.w(TAG, "命令超时 ${timeoutMs}ms，强制结束: ${sanitizeCommandForLog(command)}")
                runCatching { process.destroyForcibly() }
            }
        }
        try {
            val text = process.inputStream.bufferedReader(Charset.forName("UTF-8")).readText()
            output.append(text)
            val code = runCatching { process.waitFor() }.getOrNull()
            var result = output.build()
            if (killedByWatchdog.get()) {
                result += "\n[命令执行超时：超过 ${timeoutMs}ms 已被强制终止]"
            }
            CommandResult(result, if (killedByWatchdog.get()) null else code, output.truncated)
        } finally {
            watchdog.cancelAndJoin()
            runCatching { process.destroy() }
        }
    }

    private fun startProcess(command: String, projectPath: String?): Process {
        val argv = LocalShell.commandArgv(command)
        val builder = ProcessBuilder(argv)
        if (projectPath != null) {
            val dir = File(projectPath)
            if (dir.isDirectory) builder.directory(dir)
        }
        builder.redirectErrorStream(true)
        builder.environment().putAll(LocalShell.environment(context))
        // Feed an empty stdin so commands never block waiting for input.
        runCatching {
            builder.redirectInput(if (isWindows()) File("NUL") else File("/dev/null"))
        }
        FileLogger.d(
            TAG,
            "执行命令 argv=${argv.joinToString(" ")} cwd=${builder.directory()?.path ?: "(inherit)"}: " +
                sanitizeCommandForLog(command)
        )
        return builder.start()
    }

    private fun isWindows(): Boolean =
        PlatformInfo.operatingSystem.contains("windows", ignoreCase = true)
}
