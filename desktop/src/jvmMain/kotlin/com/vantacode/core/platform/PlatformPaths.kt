/*
 * VantaCode Desktop — platform paths & info.
 * Replaces Android's Context/filesDir with a stable per-user directory.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.io.File

/** All app-private state lives under ~/.vantacode (mirrors the Android filesDir layout). */
object PlatformPaths {
    /** App data root: ~/.vantacode */
    val root: File get() = File(System.getProperty("user.home"), ".vantacode")
    /** General app files (mirrors Context.filesDir). */
    val filesDir: File get() = File(root, "files")
    /** Cache (mirrors Context.cacheDir). */
    val cacheDir: File get() = File(root, "cache")
    /** Room database directory. */
    val databasesDir: File get() = File(root, "databases")
    /** Logs. */
    val logsDir: File get() = File(root, "logs")
    /** AI session request/response logs. */
    val aiLogsDir: File get() = File(root, "ai-logs")
    /** User agent config directory (skills, agents, prompts, teams, memory): ~/.aicode */
    val agentConfigDir: File get() = File(System.getProperty("user.home"), ".aicode")

    fun ensure() {
        listOf(root, filesDir, cacheDir, databasesDir, logsDir, aiLogsDir, agentConfigDir).forEach { it.mkdirs() }
    }
}

object PlatformInfo {
    const val PACKAGE_NAME = "com.vantacode.desktop"
    const val VERSION_NAME = "1.4.0"
    const val VERSION_CODE = 10400
    val operatingSystem: String get() = System.getProperty("os.name", "Windows") ?: "Windows"
    val osVersion: String get() = System.getProperty("os.version", "") ?: ""
    val arch: String get() = System.getProperty("os.arch", "amd64") ?: "amd64"
}
