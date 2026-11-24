/*
 * VantaCode Desktop — SystemClock 替身。
 *
 * Android 的 (System.nanoTime() / 1_000_000L) 是单调时钟，桌面端用
 * System.nanoTime() 等价实现（同样不受系统时间调整影响）。
 *
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

object SystemClock {
    /** 单调毫秒计数，进程内相对值。 */
    fun elapsedRealtime(): Long = System.nanoTime() / 1_000_000L

    /** 与 [elapsedRealtime] 同源；Android 上两者都只保证单调。 */
    fun uptimeMillis(): Long = elapsedRealtime()
}
