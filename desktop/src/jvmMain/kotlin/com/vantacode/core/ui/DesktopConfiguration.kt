/*
 * VantaCode Desktop — screen size shim.
 * Android's LocalConfiguration does not exist on desktop; window metrics come from
 * Compose's LocalWindowInfo (container size in dp).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalWindowInfo

/**
 * Desktop replacement for `androidx.compose.ui.platform.LocalConfiguration`.
 *
 * Only the window metrics are needed by the ported code (screen width / height in dp).
 */
object DesktopConfiguration {
    /** 当前窗口宽度（dp）。 */
    val screenWidthDp: Int
        @Composable @ReadOnlyComposable get() = LocalWindowInfo.current.containerDpSize.width.value.toInt()

    /** 当前窗口高度（dp）。 */
    val screenHeightDp: Int
        @Composable @ReadOnlyComposable get() = LocalWindowInfo.current.containerDpSize.height.value.toInt()
}
