/*
 * VantaCode Desktop — release packaged assets into the agent config directory (~/.aicode).
 * Desktop counterpart of ContainerInstaller's extract* steps.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.assets

import com.vantacode.core.platform.DesktopAssets
import com.vantacode.core.platform.PlatformPaths
import com.vantacode.core.util.FileLogger
import java.io.File

object BuiltinAssets {
    private const val TAG = "BuiltinAssets"

    private val agentDir: File get() = PlatformPaths.agentConfigDir

    /** Copy every packaged asset under [assetDir] into [targetDir], overwriting (release semantics). */
    private fun extractTreeOverwrite(assetDir: String, targetDir: File): Int {
        val entries = DesktopAssets.list(assetDir) ?: return 0
        var count = 0
        for (entry in entries) {
            val child = "$assetDir/$entry"
            try {
                val bytes = DesktopAssets.readBytes(child)
                val target = File(targetDir, entry)
                target.parentFile?.mkdirs()
                target.writeBytes(bytes)
                count++
            } catch (e: Exception) {
                // treat as directory and recurse into it
                val before = count
                count += extractTreeOverwrite(child, File(targetDir, entry))
                if (count == before) FileLogger.w(TAG, "解压内置资源失败: $child")
            }
        }
        return count
    }

    /** Copy only files that do not exist at the target yet (never overwrite user edits). */
    private fun extractTreeFillMissing(assetDir: String, targetDir: File): Int {
        val entries = DesktopAssets.list(assetDir) ?: return 0
        var count = 0
        for (entry in entries) {
            val child = "$assetDir/$entry"
            val target = File(targetDir, entry)
            if (target.exists()) continue
            try {
                val bytes = DesktopAssets.readBytes(child)
                target.parentFile?.mkdirs()
                target.writeBytes(bytes)
                count++
            } catch (e: Exception) {
                count += extractTreeFillMissing(child, target)
            }
        }
        return count
    }

    /** docs/ → ~/.aicode/docs/（覆盖式，随版本升级） */
    fun extractDocs() = extractTreeOverwrite("docs", File(agentDir, "docs")).also {
        FileLogger.i(TAG, "已提取内置文档到 ~/.aicode/docs ($it 个文件)")
    }

    /** prompts/ → ~/.aicode/prompts/（覆盖式，随版本升级） */
    fun extractPrompts() = extractTreeOverwrite("prompts", File(agentDir, "prompts")).also {
        FileLogger.i(TAG, "已提取内置提示词到 ~/.aicode/prompts ($it 个文件)")
    }

    /** aicode/scripts/ → ~/.aicode/scripts/（覆盖式，随版本升级） */
    fun extractScripts() = extractTreeOverwrite("aicode/scripts", File(agentDir, "scripts")).also {
        FileLogger.i(TAG, "已提取内置脚本到 ~/.aicode/scripts ($it 个文件)")
    }

    /** agents/ → ~/.aicode/agents/（仅补齐缺失，不覆盖用户修改） */
    fun extractAgents() = extractTreeFillMissing("agents", File(agentDir, "agents")).also {
        FileLogger.i(TAG, "已补齐内置子代理定义到 ~/.aicode/agents ($it 个新文件)")
    }
}
