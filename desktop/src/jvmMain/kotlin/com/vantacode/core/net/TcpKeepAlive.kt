package com.vantacode.core.net

import com.vantacode.core.util.FileLogger
import java.net.Socket

private const val TAG = "TcpKeepAlive"

/**
 * 为 TCP socket 开启 keepalive。
 *
 * Android 版还能通过 `android.system.Os` 把 TCP_KEEPIDLE / TCP_KEEPINTVL / TCP_KEEPCNT
 * 压到秒级（内核空闲阈值默认 2 小时，长连接会被 NAT 悄悄回收）。JVM 没有暴露这三个
 * 选项——`java.net.SocketOptions` 只提供 SO_KEEPALIVE——桌面端因此只做能做的部分：
 * 打开 SO_KEEPALIVE，间隔仍交给系统默认值。
 *
 * 如需在桌面端也压低间隔，可走 JNI/`setsockopt` 外部工具；当前保持静默降级。
 */
fun Socket.applyTcpKeepAlive() {
    runCatching { keepAlive = true }
        .onFailure { FileLogger.w(TAG, "开启 SO_KEEPALIVE 失败", it) }
}
