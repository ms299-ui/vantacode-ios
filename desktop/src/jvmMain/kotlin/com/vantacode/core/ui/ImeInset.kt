package com.vantacode.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Desktop: there is no OS-drawn soft keyboard overlapping the window — the window
 * simply resizes, and Compose lays the input above it naturally. Kept for call-site
 * compatibility with the Android codebase.
 */
@Composable
fun rememberImeBottomInset(): Dp = 0.dp
