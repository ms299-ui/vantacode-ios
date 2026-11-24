/*
 * VantaCode Desktop — Android 常用 API 的桌面替身。
 *
 * 这些类型在 Android 端是系统类（android.net.Uri / android.content.Intent /
 * android.content.ClipData / android.content.ClipboardManager / android.net.ConnectivityManager），
 * 桌面端没有对应物。这里按「端口真正用到的那部分 API」实现最小替身，
 * 让上层业务代码不用为了平台差异改写。
 *
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.util.concurrent.CopyOnWriteArrayList

/** android.net.Uri 替身：只实现端口用到的字段。 */
class Uri private constructor(private val raw: String) {

    val scheme: String? get() = raw.substringBefore(':', missingDelimiterValue = "").takeIf { it.isNotEmpty() }

    val authority: String?
        get() = raw.substringAfter("://", missingDelimiterValue = "")
            .substringBefore('/')
            .takeIf { it.isNotEmpty() }

    val host: String? get() = authority?.substringBefore(':')?.takeIf { it.isNotEmpty() }

    val port: Int get() = authority?.substringAfter(':', missingDelimiterValue = "")?.toIntOrNull() ?: -1

    /** 文件 URI 时是本地路径；http(s) 时是路径部分。 */
    val path: String
        get() = when {
            scheme == "file" -> raw.removePrefix("file://")
            else -> raw.substringAfter('/', missingDelimiterValue = "")
        }

    val rawPath: String get() = path

    val lastPathSegment: String? get() = path.substringAfterLast('/').takeIf { it.isNotEmpty() }

    override fun toString(): String = raw

    companion object {
        fun parse(value: String): Uri = Uri(value)
    }
}

/** android.content.Intent 替身：只实现「打开链接/文件」这一类动作。 */
class Intent() {
    var action: String? = null
    var data: Uri? = null
    var type: String? = null
    var flags: Int = 0

    constructor(action: String) : this() {
        this.action = action
    }

    constructor(action: String, data: Uri) : this() {
        this.action = action
        this.data = data
    }

    fun addFlags(extra: Int): Intent {
        flags = flags or extra
        return this
    }

    fun putExtra(key: String, value: String?): Intent {
        extras[key] = value
        return this
    }

    fun setDataAndType(data: Uri, type: String): Intent {
        this.data = data
        this.type = type
        return this
    }

    fun setType(type: String): Intent {
        this.type = type
        return this
    }

    val extras: MutableMap<String, String?> = mutableMapOf()

    companion object {
        const val ACTION_VIEW = "android.intent.action.VIEW"
        const val ACTION_SEND = "android.intent.action.SEND"
        const val FLAG_ACTIVITY_NEW_TASK = 0x10000000
        const val FLAG_GRANT_READ_URI_PERMISSION = 0x00000001
        const val FLAG_GRANT_WRITE_URI_PERMISSION = 0x00000002
    }
}

/** android.content.ClipData 替身。 */
class ClipData(val label: String?, val text: String?, val uri: Uri?) {
    companion object {
        fun newPlainText(label: String, text: String): ClipData = ClipData(label, text, null)
        fun newUri(resolved: Uri?, label: String, uri: Uri): ClipData = ClipData(label, null, uri)
    }
}

/** android.content.ClipboardManager 替身：走 AWT 系统剪贴板（见 [AppClipboard]）。 */
object ClipboardManager {
    fun setPrimaryClip(clip: ClipData) {
        clip.text?.let { AppClipboard.setText(it) }
    }

    fun getPrimaryClip(): ClipData? = AppClipboard.getText()?.let { ClipData.newPlainText("", it) }
}

/** android.net.Network 替身。 */
data class Network(val id: Int)

/** android.net.NetworkCapabilities 替身。 */
class NetworkCapabilities {
    fun hasCapability(capability: Int): Boolean = capability == NET_CAPABILITY_INTERNET

    companion object {
        const val NET_CAPABILITY_INTERNET = 4
    }
}

/**
 * android.net.ConnectivityManager 替身。
 *
 * 桌面端没有「默认网络回调」，改为后台轮询网卡状态（3s 一次），状态翻转时回调
 * onAvailable / onLost，供上层做「断线恢复 → 立即重连」。
 */
class ConnectivityManager {
    abstract class NetworkCallback {
        open fun onAvailable(network: Network) {}
        open fun onLost(network: Network) {}
    }

    private val callbacks = CopyOnWriteArrayList<NetworkCallback>()

    @Volatile
    private var online: Boolean = probe()
    private var lastNotified: Boolean? = null

    val activeNetwork: Network? get() = if (online) Network(1) else null

    fun registerDefaultNetworkCallback(callback: NetworkCallback) {
        callbacks += callback
        lastNotified = online
    }

    fun getNetworkCapabilities(network: Network?): NetworkCapabilities? =
        if (network != null && online) NetworkCapabilities() else null

    init {
        Thread({ pollLoop() }, "vantacode-connectivity").apply {
            isDaemon = true
            start()
        }
    }

    private fun pollLoop() {
        while (true) {
            try {
                Thread.sleep(POLL_INTERVAL_MS)
            } catch (e: InterruptedException) {
                return
            }
            val now = probe()
            if (now != online) {
                online = now
                if (lastNotified != now) {
                    lastNotified = now
                    val network = Network(1)
                    callbacks.forEach { cb ->
                        runCatching {
                            if (now) cb.onAvailable(network) else cb.onLost(network)
                        }
                    }
                }
            }
        }
    }

    private fun probe(): Boolean = runCatching {
        val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
        while (interfaces != null && interfaces.hasMoreElements()) {
            val ni = interfaces.nextElement()
            if (ni.isUp && !ni.isLoopback) return true
        }
        false
    }.getOrDefault(false)

    private companion object {
        const val POLL_INTERVAL_MS = 3_000L
    }
}
