/*
 * VantaCode Desktop — default-network availability observer.
 * Android's ConnectivityManager callbacks have no desktop equivalent; this polls a
 * couple of well-known endpoints and pushes [recovered] on offline -> online edges
 * (the SSH / sync / agent reconnect trigger the Android build got from the system).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.net

import com.vantacode.core.util.FileLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 网络可用性监听。
 *
 * 桌面端没有系统网络回调，这里用后台轮询：每 5s 尝试向几个固定端点发起 TCP 连接
 * （443 端口的 DNS-over-HTTPS 与公共 DNS），任一成功即视为在线。
 * 从离线恢复到在线时推送 [recovered]，供 SSH、同步与 Agent 即时重连——
 * 切换网络同样需要重连：旧 TCP 通道的 NAT 映射已失效。
 */
@Singleton
class NetworkConnectivityObserver @Inject constructor() {
    private val _isOnline = MutableStateFlow(probeOnce()).also { NetworkStatus.update(it.value) }
    /** 当前是否具备互联网能力。 */
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _recovered = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    /** 网络可用性发生变化（恢复 / 切换链路）时发射，消费方据此立即重建连接。 */
    val recovered: SharedFlow<Unit> = _recovered.asSharedFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch {
            while (true) {
                delay(POLL_INTERVAL_MS)
                val online = probeOnce()
                val was = _isOnline.value
                if (online != was) {
                    _isOnline.value = online
                    NetworkStatus.update(online)
                    if (online) {
                        FileLogger.i(TAG, "网络恢复，触发重连")
                        _recovered.tryEmit(Unit)
                    } else {
                        FileLogger.w(TAG, "网络断开")
                    }
                }
            }
        }
    }

    /** 向两个固定端点各建一次短连接；任一成功即认为在线。 */
    private fun probeOnce(): Boolean = PROBES.any { (host, port) ->
        runCatching {
            java.net.Socket().use { socket ->
                socket.connect(java.net.InetSocketAddress(host, port), PROBE_TIMEOUT_MS)
            }
        }.isSuccess
    }

    private companion object {
        const val TAG = "NetworkConnectivity"
        const val POLL_INTERVAL_MS = 5_000L
        const val PROBE_TIMEOUT_MS = 2_000
        val PROBES = listOf("1.1.1.1" to 443, "8.8.8.8" to 53)
    }
}
