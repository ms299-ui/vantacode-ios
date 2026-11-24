package com.vantacode.feature.settings.data.repository

/**
 * 应用更新通道。
 *
 * - [STABLE]：只跟正式版（稳定通道）。
 * - [LATEST]：跟最新发布（含 RC / 预发布通道）。
 *
 * 桌面端没有自建更新服务（APK 商店那套是 Android 侧的事），该枚举目前只用于设置界面的
 * 通道选择展示。
 */
enum class UpdateChannel {
    STABLE,
    LATEST;

    companion object {
        fun fromName(raw: String?): UpdateChannel =
            if (raw.equals(LATEST.name, ignoreCase = true)) LATEST else STABLE
    }
}

/** 默认下载镜像源：gh-proxy 的 CDN 节点。 */
const val DEFAULT_DOWNLOAD_MIRROR = "https://cdn.gh-proxy.com/"

/** 备选镜像源（设置页快捷项）：gh-proxy 的 IPv6 优选节点。 */
const val ALT_DOWNLOAD_MIRROR = "https://v6.gh-proxy.org/"

/** 镜像源是否合法：留空（直连）或以 http/https 开头。 */
fun isValidDownloadMirror(value: String): Boolean {
    val v = value.trim()
    return v.isEmpty() || v.startsWith("http://") || v.startsWith("https://")
}
