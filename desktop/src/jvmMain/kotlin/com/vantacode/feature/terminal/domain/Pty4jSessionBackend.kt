/*
 * VantaCode Desktop — SessionBackend on top of pty4j (ConPTY on Windows).
 * Mirrors the upstream SubprocessBackend contract: the PTY is spawned eagerly.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.domain

import com.pty4j.PtyProcess
import com.pty4j.PtyProcessBuilder
import com.pty4j.WinSize
import com.termux.terminal.SessionBackend
import com.vantacode.core.util.FileLogger
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Local PTY backend backed by [PtyProcess]. The process is spawned eagerly — mirrors
 * [com.termux.terminal.SubprocessBackend] semantics, minus JNI.
 *
 * @param argv argv for the shell (see [LocalShell.resolve])
 * @param workingDirectory initial cwd; null = unchanged
 * @param environment environment as a map (or null to inherit the parent's)
 * @param rows initial PTY rows
 * @param columns initial PTY columns
 */
class Pty4jSessionBackend(
    argv: List<String>,
    workingDirectory: File?,
    environment: Map<String, String>?,
    rows: Int,
    columns: Int
) : SessionBackend {

    private val process: PtyProcess

    init {
        val builder = PtyProcessBuilder()
            .setCommand(argv.toTypedArray())
            .setInitialColumns(columns)
            .setInitialRows(rows)
            .setDirectory(workingDirectory?.absolutePath ?: System.getProperty("user.dir"))
        if (environment != null) {
            builder.setEnvironment(environment)
        }
        process = try {
            builder.start()
        } catch (e: Exception) {
            FileLogger.e(TAG, "PTY 创建失败: argv=${argv.joinToString(" ")} cwd=$workingDirectory", e)
            throw IllegalStateException("无法启动本地终端进程: ${e.message}", e)
        }
        FileLogger.i(
            TAG,
            "PTY 子进程已启动: pid=${pid()} argv=${argv.joinToString(" ")} " +
                "cwd=${workingDirectory?.absolutePath} size=${columns}x$rows"
        )
    }

    /** Pid of the shell; -1 if unknown. */
    fun pid(): Long = runCatching { process.pid() }.getOrDefault(-1L)

    override fun getInputStream(): InputStream = process.inputStream

    override fun getOutputStream(): OutputStream = process.outputStream

    override fun resize(columns: Int, rows: Int) {
        runCatching {
            process.setWinSize(WinSize(columns, rows))
        }.onFailure { FileLogger.w(TAG, "PTY resize 失败 ${columns}x${rows}", it) }
    }

    override fun waitForExit(): Int = try {
        process.waitFor()
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
        -1
    } catch (e: Exception) {
        FileLogger.w(TAG, "PTY waitFor 异常", e)
        -1
    }

    override fun close() {
        runCatching { process.destroyForcibly() }
            .onFailure { FileLogger.w(TAG, "PTY destroy 失败", it) }
    }

    private companion object {
        const val TAG = "Pty4jSessionBackend"
    }
}
