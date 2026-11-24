/*
 * VantaCode Desktop — local shell resolution for the PTY terminal and AI command sessions.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.domain

import com.vantacode.core.net.AppProxy
import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.platform.PlatformInfo
import java.io.File

object LocalShell {

    enum class Kind { BASH, POWERSHELL, CMD, POSIX_SH }

    data class Resolution(
        val kind: Kind,
        /** interactive PTY argv (shell only, no command) */
        val interactiveArgv: List<String>,
        /** whether AI background commands run through a POSIX interpreter */
        val isPosix: Boolean,
    ) {
        val isWindows: Boolean get() = kind == Kind.POWERSHELL || kind == Kind.CMD
    }

    private fun isWindows(): Boolean =
        PlatformInfo.operatingSystem.contains("windows", ignoreCase = true)

    private fun firstExisting(candidates: List<String>): String? =
        candidates.firstOrNull { File(it).isFile }

    private fun gitBash(): String? {
        val localAppData = System.getenv("LOCALAPPDATA") ?: ""
        val programFiles = System.getenv("ProgramFiles") ?: "C:\\Program Files"
        val programFilesX86 = System.getenv("ProgramFiles(x86)") ?: "C:\\Program Files (x86)"
        return firstExisting(listOf(
            "$programFiles\\Git\\bin\\bash.exe",
            "$programFilesX86\\Git\\bin\\bash.exe",
            "$localAppData\\Programs\\Git\\bin\\bash.exe",
        ))
    }

    private fun powerShell(): String? {
        val systemRoot = System.getenv("SystemRoot") ?: "C:\\Windows"
        val programFiles = System.getenv("ProgramFiles") ?: "C:\\Program Files"
        return firstExisting(listOf(
            "$systemRoot\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
            "$programFiles\\PowerShell\\7\\pwsh.exe",
            "C:\\pwsh\\pwsh.exe",
        ))
    }

    /** Resolve the shell used on this machine. On Windows: Git Bash > PowerShell > cmd. */
    fun resolve(): Resolution {
        if (!isWindows()) {
            val sh = if (File("/bin/bash").isFile) "/bin/bash" else "/bin/sh"
            return Resolution(
                kind = if (sh.endsWith("bash")) Kind.BASH else Kind.POSIX_SH,
                interactiveArgv = listOf(sh, "-i"),
                isPosix = true,
            )
        }
        gitBash()?.let {
            return Resolution(Kind.BASH, listOf(it, "-i"), isPosix = true)
        }
        powerShell()?.let {
            return Resolution(Kind.POWERSHELL, listOf(it, "-NoLogo"), isPosix = false)
        }
        return Resolution(
            Kind.CMD,
            listOf(System.getenv("ComSpec") ?: "C:\\Windows\\System32\\cmd.exe"),
            isPosix = false,
        )
    }

    /** argv for a one-shot command line (non-interactive). */
    fun commandArgv(command: String): List<String> = when (resolve().kind) {
        Kind.BASH, Kind.POSIX_SH -> resolve().interactiveArgv.take(1) + listOf("-c", command)
        Kind.POWERSHELL -> resolve().interactiveArgv + listOf("-NoProfile", "-Command", command)
        Kind.CMD -> resolve().interactiveArgv + listOf("/c", command)
    }

    /** Command to write into an interactive shell to move to the workspace. */
    fun cdCommand(path: String): String {
        val escaped = path.replace("\\", "\\\\").replace("\"", "\\\"")
        return "cd \"$escaped\"\n"
    }

    /**
     * One-shot command line for a backgrounded AI task inside a persistent shell.
     * Must print the shared exit marker `[command exited: N]` line.
     * POSIX shells get the upstream wrapper; PowerShell/cmd get the equivalent.
     */
    fun backgroundCommand(command: String, notify: Boolean, kind: Kind): String = when {
        kind == Kind.BASH || kind == Kind.POSIX_SH -> {
            val tail = if (notify) "; exit \$ec" else "; exec ${resolve().interactiveArgv.first()} -i"
            "$command; ec=\$?; echo \"[command exited: \$ec]\"$tail\n"
        }
        kind == Kind.POWERSHELL -> {
            val tail = if (notify) "; exit \$ec" else ""
            "$command; \$ec = \$LASTEXITCODE; Write-Output \"[command exited: \$ec]\"$tail\n"
        }
        else -> {
            val tail = if (notify) " & exit /b %ec%" else ""
            "$command & set ec=%errorlevel% & echo [command exited: %ec%]$tail\r\n"
        }
    }

    /** Environment for spawned shells: parent env + configured proxy variables. */
    fun environment(context: DesktopContext): Map<String, String> {
        val proxy = AppProxy.proxyEnv(context)
        return System.getenv() + proxy
    }

    /** Environment array for pty4j / ProcessBuilder. */
    fun environmentArray(context: DesktopContext): Array<String> =
        environment(context).map { (k, v) -> "$k=$v" }.toTypedArray()
}
