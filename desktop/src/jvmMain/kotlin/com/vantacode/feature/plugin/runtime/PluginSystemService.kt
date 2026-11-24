package com.vantacode.feature.plugin.runtime

import com.vantacode.core.platform.AppClipboard
import com.vantacode.core.platform.DesktopActions
import com.vantacode.core.platform.DesktopAppLifecycle
import com.vantacode.core.platform.PlatformInfo
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.plugin.domain.PluginHostException
import com.vantacode.feature.plugin.domain.PluginManager
import java.net.URI
import java.net.URISyntaxException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * 插件可用的宿主系统能力：剪贴板、系统分享、打开链接、宿主 App 信息与日志。
 *
 * 每个入口都以插件启用为前提（停用/损坏的插件不能借用宿主身份操作系统），参数校验在切主线程之前完成，
 * 失败统一抛 [PluginHostException]，code 稳定：`PLUGIN_INACTIVE`、`PARAM_INVALID`、`TOO_LARGE`、
 * `NO_ACTIVITY`、`SERVICE_UNAVAILABLE`、`APP_INFO_UNAVAILABLE`。
 *
 * 剪贴板与 Intent 都在主线程执行：剪贴板服务与 `startActivity` 都面向 UI，放主线程避免跨线程的时序问题。
 * 桌面端没有 Activity/主线程概念，剪贴板走 AWT 系统剪贴板，开链接走 [DesktopActions.openUrl]，
 * 系统分享面板无对应能力，降级为明确错误码 SERVICE_UNAVAILABLE。
 */
@Singleton
class PluginSystemService @Inject constructor(
    private val pluginManager: PluginManager
) {

    /** 读取剪贴板第一条文本；剪贴板为空（或内容不是文本）时返回空串。 */
    suspend fun clipboardRead(pluginId: String): JsonElement {
        ensureActive(pluginId)
        val text = AppClipboard.getText().orEmpty()
        if (text.toByteArray(Charsets.UTF_8).size > MAX_CLIPBOARD_BYTES) {
            throw PluginHostException("TOO_LARGE", "clipboard text too large")
        }
        return buildJsonObject { put("text", text) }
    }

    /** 写入剪贴板。即使在后台也允许写。 */
    suspend fun clipboardWrite(pluginId: String, text: String): JsonElement {
        ensureActive(pluginId)
        if (text.toByteArray(Charsets.UTF_8).size > MAX_CLIPBOARD_BYTES) {
            throw PluginHostException("TOO_LARGE", "clipboard text too large")
        }
        if (!AppClipboard.setText(text)) {
            throw PluginHostException("SERVICE_UNAVAILABLE", "clipboard service unavailable")
        }
        return buildJsonObject { put("ok", true) }
    }

    /** 拉起系统分享面板；[title] 非空时同时用作选择器标题与分享主题。 */
    suspend fun shareText(pluginId: String, text: String, title: String? = null): JsonElement {
        ensureActive(pluginId)
        if (text.length > MAX_SHARE_CHARS) {
            throw PluginHostException("TOO_LARGE", "share text too long")
        }
        val subject = title?.trim()?.takeIf { it.isNotEmpty() }
        // 桌面端没有 Android 的 ACTION_SEND 选择器，保留参数校验后返回明确错误码。
        throw PluginHostException(
            "SERVICE_UNAVAILABLE",
            "system share is not available on desktop" + (subject?.let { " (subject: $it)" } ?: "")
        )
    }

    /** 用系统浏览器或其他已注册的 App 打开 http/https 链接。 */
    suspend fun openUrl(pluginId: String, url: String): JsonElement {
        ensureActive(pluginId)
        val target = requireHttpUrl(url)
        if (!DesktopActions.openUrl(target)) {
            throw PluginHostException("NO_ACTIVITY", "no activity to open url")
        }
        return ok()
    }

    /** 宿主 App 的包名、版本、系统 API 级别、界面语言与前后台状态。 */
    suspend fun appInfo(pluginId: String): JsonElement {
        ensureActive(pluginId)
        return buildJsonObject {
            put("packageName", PlatformInfo.PACKAGE_NAME)
            put("versionName", PlatformInfo.VERSION_NAME)
            put("versionCode", PlatformInfo.VERSION_CODE)
            // 桌面端无 Android SDK 版本，填入等价的宿主 API 级别占位值。
            put("sdkInt", 0)
            put("operatingSystem", PlatformInfo.operatingSystem)
            put("languageTag", Locale.getDefault().toLanguageTag())
            put("isForeground", DesktopAppLifecycle.windowFocused)
        }
    }

    /** 写一行宿主日志；[tag] 缺省为 `plugin:<pluginId>`，便于按插件过滤。 */
    suspend fun log(pluginId: String, level: String, message: String, tag: String? = null): JsonElement {
        ensureActive(pluginId)
        if (message.length > MAX_LOG_MESSAGE_CHARS) {
            throw PluginHostException("TOO_LARGE", "log message too long")
        }
        val logTag = when {
            tag.isNullOrEmpty() -> DEFAULT_TAG_PREFIX + pluginId
            tag.length > MAX_LOG_TAG_CHARS -> throw PluginHostException("PARAM_INVALID", "log tag too long")
            else -> tag
        }
        when (level) {
            "debug" -> FileLogger.d(logTag, message)
            "info" -> FileLogger.i(logTag, message)
            "warn" -> FileLogger.w(logTag, message)
            "error" -> FileLogger.e(logTag, message)
            else -> throw PluginHostException("PARAM_INVALID", "unknown log level: $level")
        }
        return ok()
    }

    private fun ensureActive(pluginId: String) {
        pluginManager.ensureLoaded()
        pluginManager.plugins.value.firstOrNull { it.id == pluginId && it.isActive }
            ?: throw PluginHostException("PLUGIN_INACTIVE", "plugin is not active: $pluginId")
    }

    /** 只放行 http/https；URI 解析失败、缺 scheme 或没有 authority 都算参数非法。 */
    private fun requireHttpUrl(url: String): String {
        if (url.length > MAX_URL_CHARS) throw PluginHostException("TOO_LARGE", "url too long")
        val parsed = try {
            URI(url)
        } catch (e: URISyntaxException) {
            throw PluginHostException("PARAM_INVALID", "invalid url")
        }
        val scheme = parsed.scheme?.lowercase(Locale.ROOT)
        if (scheme != "http" && scheme != "https") {
            throw PluginHostException("PARAM_INVALID", "only http/https urls are allowed")
        }
        if (parsed.rawAuthority.isNullOrEmpty()) {
            throw PluginHostException("PARAM_INVALID", "url has no host")
        }
        return url
    }

    private fun ok(): JsonElement = buildJsonObject { put("ok", true) }

    private companion object {
        const val MAX_CLIPBOARD_BYTES = 1024 * 1024
        const val MAX_SHARE_CHARS = 100_000
        const val MAX_URL_CHARS = 4096
        const val MAX_LOG_MESSAGE_CHARS = 2000
        const val MAX_LOG_TAG_CHARS = 64
        const val DEFAULT_TAG_PREFIX = "plugin:"
    }
}
