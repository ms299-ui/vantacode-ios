/*
 * VantaCode Desktop — 桌面端的「返回」处理。
 *
 * Android 用 activity-compose 的 BackHandler 拦截系统返回键；桌面端没有系统返回键，
 * 这里退化为 Esc 键：对话框/抽屉/选择器打开时按 Esc 触发 onBack。
 *
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import java.awt.KeyEventDispatcher
import java.awt.event.KeyEvent

/**
 * 桌面版 BackHandler：Esc 等价于 Android 的系统返回键。
 *
 * @param enabled 是否接管 Esc；false 时不注册监听，Esc 走窗口默认行为。
 * @param onBack Esc 被按下时执行。
 */
@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    DisposableEffect(enabled, onBack) {
        if (!enabled) {
            onDispose { }
        } else {
            val dispatcher = KeyEventDispatcher { event ->
                if (event.id == KeyEvent.KEY_PRESSED && event.keyCode == KeyEvent.VK_ESCAPE) {
                    onBack()
                    true
                } else {
                    false
                }
            }
            val focusManager = java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
            focusManager.addKeyEventDispatcher(dispatcher)
            onDispose { focusManager.removeKeyEventDispatcher(dispatcher) }
        }
    }
}
