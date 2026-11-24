/*
 * VantaCode Desktop — system tray notifications via java.awt.SystemTray + TrayIcon.
 * Used where the Android app used notification channels (agent completion, approval
 * pending). Falls back silently when no tray is available (e.g. some Linux sessions).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import com.vantacode.core.util.FileLogger
import java.awt.Image
import java.awt.SystemTray
import java.awt.TrayIcon
import java.io.File
import javax.imageio.ImageIO
import java.util.concurrent.atomic.AtomicBoolean

object TrayNotifier {
    private const val TAG = "TrayNotifier"
    private val supported = SystemTray.isSupported()
    private val added = AtomicBoolean(false)
    private var trayIcon: TrayIcon? = null

    private fun ensureIcon(): TrayIcon? {
        if (!supported) return null
        if (trayIcon != null && added.get()) return trayIcon
        trayIcon = runCatching {
            val image = loadIcon() ?: return@runCatching null
            TrayIcon(image, "VantaCode").apply {
                isImageAutoSize = true
            }
        }.getOrNull() ?: return null
        return runCatching {
            SystemTray.getSystemTray().add(trayIcon)
            added.set(true)
            trayIcon
        }.onFailure { FileLogger.w(TAG, "系统托盘添加图标失败", it) }.getOrNull()
    }

    private fun loadIcon(): Image? {
        // 1) from the packaged PNG (if present on disk next to the app)
        runCatching {
            val candidates = listOf(
                File("icon.png"),
                File("icons/icon.png"),
                File("desktop/icons/icon.png"),
                File(System.getProperty("user.dir"), "desktop/icons/icon.png"),
            )
            for (f in candidates) {
                if (f.isFile) return ImageIO.read(f)
            }
        }
        // 2) from classpath (resources on the module classpath)
        runCatching {
            javaClass.getResourceAsStream("/icon.png")?.use { ImageIO.read(it) }
        }.getOrNull()?.let { return it }
        runCatching {
            javaClass.getResourceAsStream("/drawable/icon.png")?.use { ImageIO.read(it) }
        }.getOrNull()?.let { return it }
        // 3) last-resort generated icon
        return runCatching {
            val img = java.awt.image.BufferedImage(32, 32, java.awt.image.BufferedImage.TYPE_INT_ARGB)
            val g = img.createGraphics()
            g.color = java.awt.Color(0x16, 0x0B, 0x2E)
            g.fillRect(0, 0, 32, 32)
            g.color = java.awt.Color(0xB3, 0x8B, 0xFF)
            g.font = java.awt.Font("SansSerif", java.awt.Font.BOLD, 22)
            g.drawString("V", 7, 25)
            g.dispose()
            img
        }.getOrNull()
    }

    /** Show a tray balloon (or no-op when the tray is unavailable). */
    fun notify(title: String, detail: String, isError: Boolean = false) {
        val icon = ensureIcon() ?: run {
            FileLogger.i(TAG, "托盘不可用，降级记录: [$title] $detail")
            return
        }
        runCatching {
            icon.displayMessage(
                title.take(60),
                detail.take(250),
                if (isError) TrayIcon.MessageType.ERROR else TrayIcon.MessageType.INFO
            )
        }.onFailure { FileLogger.w(TAG, "托盘通知失败", it) }
    }
}
