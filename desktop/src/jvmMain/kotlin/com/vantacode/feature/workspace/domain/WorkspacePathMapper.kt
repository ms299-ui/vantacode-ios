/*
 * VantaCode Desktop — maps AI-facing paths (~/workspace/…, ~/.aicode/…, or absolute
 * host paths) to files on this machine. There is no rootfs indirection on desktop;
 * SSH remote mode keeps the same conventions for the AI's view.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.workspace.domain

import com.vantacode.core.platform.PlatformPaths
import com.vantacode.feature.workspace.data.repository.WorkspaceRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AI-facing path mapping for the desktop:
 * - `~/workspace[/…]` (or the expanded `$HOME/workspace[/…]`) → the current workspace
 * - `~/.aicode[/…]` (or `/root/.aicode[/…]`) → the agent config dir
 * - other absolute paths → host paths as-is
 * - relative paths → under the workspace
 */
@Singleton
class WorkspacePathMapper @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val pathHomeResolver: PathHomeResolver
) {
    companion object {
        const val CONTAINER_ROOT = "~/workspace"
        const val AICODE_ROOT = "~/.aicode"
        const val LEGACY_AICODE_ROOT = "/root/.aicode"
    }

    /** Windows 盘符绝对或 UNC 路径（如 `C:/x`、`D:\x`、`\\server\share`）。 */
    private fun isWindowsAbsolute(p: String): Boolean =
        Regex("^[A-Za-z]:[/\\\\]").containsMatchIn(p) || p.startsWith("\\\\") || p.startsWith("//")

    private fun hostRoot(): File = File(workspaceRepository.currentPath())
    private fun resolvedContainerRoot(): String =
        pathHomeResolver.home().trimEnd('/').replace('\\', '/') + "/workspace"
    private fun agentConfigDirNormalized(): String =
        PlatformPaths.agentConfigDir.absolutePath.replace('\\', '/').trimEnd('/')

    /**
     * 容器别名 → 宿主机路径（local 模式）。
     * 顺序：先匹配未展开的 `~/workspace` / `~/.aicode` 别名（本地为 app 工作区/配置目录，
     * 远程为远端 root 目录），展开 `~` 后再当宿主绝对路径处理；相对路径以工作区为根。
     */
    fun toHostFile(path: String): File {
        val raw = path.trim()
        val root = hostRoot()
        val aicode = PlatformPaths.agentConfigDir

        // 先匹配未展开的别名（每个分支直接 return）
        when {
            raw == CONTAINER_ROOT || raw == "$CONTAINER_ROOT/" -> return root
            raw.startsWith("$CONTAINER_ROOT/") ->
                return File(root, raw.removePrefix("$CONTAINER_ROOT/"))
            raw == AICODE_ROOT || raw == "$AICODE_ROOT/" ||
                raw == LEGACY_AICODE_ROOT || raw == "$LEGACY_AICODE_ROOT/" -> return aicode
            raw.startsWith("$AICODE_ROOT/") ->
                return File(aicode, raw.removePrefix("$AICODE_ROOT/"))
            raw.startsWith("$LEGACY_AICODE_ROOT/") ->
                return File(aicode, raw.removePrefix("$LEGACY_AICODE_ROOT/"))
        }

        val p = pathHomeResolver.expandHome(raw).replace('\\', '/')
        val wsRoot = resolvedContainerRoot()
        val acRoot = agentConfigDirNormalized()
        return when {
            p == wsRoot || p == "$wsRoot/" -> root
            p.startsWith("$wsRoot/") -> File(root, p.removePrefix("$wsRoot/"))
            p == acRoot.trimEnd('/') || p == "$acRoot/" -> aicode
            p.startsWith("$acRoot/") -> File(aicode, p.removePrefix("$acRoot/"))
            p.startsWith("/") || isWindowsAbsolute(raw) -> File(raw.replace('\\', '/'))
            else -> File(root, raw)
        }
    }

    /** Desktop 直接映射为宿主机 File；followFinalLink=true 时对宿主绝对路径做 symlink 解析。 */
    fun resolveFile(path: String, followFinalLink: Boolean = true): File {
        val f = toHostFile(path)
        val absolute = f.absoluteFile
        return if (followFinalLink) {
            runCatching { absolute.canonicalFile }.getOrElse { absolute }
        } else {
            absolute
        }
    }

    fun resolveHostFile(file: File, followFinalLink: Boolean = true): File {
        val path = toContainerPath(file.absolutePath)
        if (path == file.absolutePath) return file
        return resolveFile(path, followFinalLink)
    }

    fun toContainerPath(hostPath: String): String {
        val rootPath = hostRoot().absolutePath.replace('\\', '/')
        val aicodePath = PlatformPaths.agentConfigDir.absolutePath.replace('\\', '/')
        val abs = File(hostPath).absolutePath.replace('\\', '/')
        val raw = hostPath.trim().replace('\\', '/')
        val resolvedWs = resolvedContainerRoot().replace('\\', '/')
        return when {
            abs == rootPath -> CONTAINER_ROOT
            abs.startsWith("$rootPath/") -> CONTAINER_ROOT + "/" + abs.removePrefix("$rootPath/")
            raw == resolvedWs || abs == resolvedWs -> CONTAINER_ROOT
            raw.startsWith("$resolvedWs/") -> CONTAINER_ROOT + "/" + raw.removePrefix("$resolvedWs/")
            abs.startsWith("$resolvedWs/") -> CONTAINER_ROOT + "/" + abs.removePrefix("$resolvedWs/")
            abs == aicodePath -> AICODE_ROOT
            abs.startsWith("$aicodePath/") -> AICODE_ROOT + "/" + abs.removePrefix("$aicodePath/")
            else -> hostPath
        }
    }
}
