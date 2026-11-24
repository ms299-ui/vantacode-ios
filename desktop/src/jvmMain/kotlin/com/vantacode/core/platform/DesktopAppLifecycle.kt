/*
 * VantaCode Desktop — process foreground mirror.
 * Android's ProcessLifecycleOwner has no desktop equivalent; App.kt writes window
 * focus here and the agent notification path reads it.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

object DesktopAppLifecycle {
    /** 主窗口是否持有焦点。默认 true，避免启动瞬间误判为后台。 */
    @Volatile
    var windowFocused: Boolean = true
}
