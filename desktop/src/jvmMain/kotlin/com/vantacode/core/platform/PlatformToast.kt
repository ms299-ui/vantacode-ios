/*
 * VantaCode Desktop — transient user messages. Android's Toast is replaced by an
 * in-memory event bus; the root composable collects it into a Snackbar.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object PlatformToast {
    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 16, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val events = _events.asSharedFlow()

    fun show(message: String, long: Boolean = false) {
        com.vantacode.core.util.FileLogger.i("Toast", message)
        _events.tryEmit(message)
    }

    fun show(message: CharSequence?, long: Boolean = false) = show(message?.toString().orEmpty(), long)
}
