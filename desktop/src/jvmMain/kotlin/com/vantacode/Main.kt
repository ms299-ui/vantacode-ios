/*
 * VantaCode Desktop — JVM entry point. DesktopApp.onCreate() wires the DI graph,
 * logging, crash handler and startup orchestration; App() is the Compose root.
 * Undecorated window; title bar lives inside App() so it shares the app theme.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import java.awt.Dimension
import java.awt.Toolkit
import javax.imageio.ImageIO

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
fun main() = application {
    DesktopApp.onCreate()

    // Window/taskbar icon from the packaged PNG resource
    val iconImage = runCatching {
        javaClass.getResourceAsStream("/icon.png")?.use { ImageIO.read(it) }
    }.getOrNull()

    // 每次按「正常桌面窗口」打开：1440x900；屏幕不够大就按屏幕撑满；始终居中于屏幕
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val defaultWindowState = rememberWindowState(
        width = minOf(1440, screenSize.width).dp,
        height = minOf(900, screenSize.height).dp,
        position = WindowPosition(Alignment.Center),
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "VantaCode",
        state = defaultWindowState,
        decoration = WindowDecoration.Undecorated(),
    ) {
        if (iconImage != null) {
            window.iconImage = iconImage
        }
        window.minimumSize = Dimension(1120, 700)
        App(window = window, onRequestClose = ::exitApplication)
    }
}
