/*
 * VantaCode Desktop — Compose integration points.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import androidx.compose.runtime.staticCompositionLocalOf

/** Desktop counterpart of LocalContext.current. */
val LocalDesktopContext = staticCompositionLocalOf { DesktopContext.instance }
