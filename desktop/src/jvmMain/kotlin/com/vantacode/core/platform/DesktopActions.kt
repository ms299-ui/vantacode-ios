/*
 * VantaCode Desktop — desktop equivalents of Intent/startActivity actions.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.awt.Desktop
import java.io.File
import java.net.URI

object DesktopActions {
    /**
     * 打开一个 URL 或本地路径：链接交给浏览器，本地路径交给系统默认程序。
     * 对应 Android 的 startActivity(Intent.ACTION_VIEW, uri)。
     */
    fun openUrl(target: String): Boolean {
        val lower = target.lowercase()
        if (lower.startsWith("http://") || lower.startsWith("https://") || lower.startsWith("mailto:")) {
            return viewUri(target)
        }
        val file = File(target.removePrefix("file://"))
        return if (file.exists()) open(file) else false
    }

    /** Open a URL in the system browser. Returns false if it could not be opened. */
    fun viewUri(url: String): Boolean = runCatching {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
            true
        } else {
            false
        }
    }.getOrDefault(false)

    /** Open a file/folder with the OS default handler. Returns false on failure. */
    fun open(file: File): Boolean = runCatching {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            Desktop.getDesktop().open(file)
            true
        } else {
            false
        }
    }.getOrDefault(false)

    /** Reveal a file in the OS file manager (Explorer). */
    fun reveal(file: File): Boolean = runCatching {
        val dir = file.absoluteFile
        if (dir.isDirectory) {
            Desktop.getDesktop().open(dir)
        } else {
            // Explorer selection flag
            ProcessBuilder("explorer.exe", "/select,", dir.absolutePath).start()
        }
        true
    }.getOrDefault(false)
}
