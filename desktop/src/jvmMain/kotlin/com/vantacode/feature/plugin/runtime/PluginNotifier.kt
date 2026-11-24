package com.vantacode.feature.plugin.runtime

import com.vantacode.core.platform.TrayNotifier
import com.vantacode.feature.plugin.domain.PluginHostPaths
import java.awt.SystemTray
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.put

/**
 * 插件发系统通知的宿主实现。
 *
 * 每个插件使用自己的 notification tag（`plugin:<pluginId>[:key]`），不会覆盖宿主或其他插件的通知；
 * 系统通知关闭、渠道被关闭或缺少权限时返回明确错误，不伪造成功。
 *
 * 桌面端用 [TrayNotifier]（java.awt.SystemTray）替代 Android NotificationCompat：
 * 参数校验与 tag 命名空间语义保持不变；无系统托盘时返回明确错误码 NOTIFICATION_DISABLED。
 */
@Singleton
class PluginNotifier @Inject constructor() {

    fun notify(pluginId: String, params: JsonObject): JsonElement {
        val title = PluginHostPaths.requireString(params, "title").trim()
        val body = PluginHostPaths.requireString(params, "body").trim()
        if (title.isEmpty()) throw PluginProcessException("PARAM_INVALID", "empty title")
        if (title.length > MAX_TITLE_CHARS) {
            throw PluginProcessException("PARAM_INVALID", "title too long")
        }
        if (body.length > MAX_BODY_CHARS) {
            throw PluginProcessException("PARAM_INVALID", "body too long")
        }
        val key = (params["key"] as? JsonPrimitive)?.contentOrNull?.trim()?.takeIf { it.isNotEmpty() }
        if (key != null && (key.length > MAX_KEY_CHARS || key.any { it.isISOControl() })) {
            throw PluginProcessException("PARAM_INVALID", "invalid key")
        }

        // Android 的通知权限/渠道开关在桌面端不存在；托盘可用性即对应的宿主开关。
        if (!SystemTray.isSupported()) {
            throw PluginProcessException("NOTIFICATION_DISABLED", "系统托盘通知不可用")
        }

        val tag = "plugin:$pluginId" + (key?.let { ":$it" } ?: "")
        runCatching { TrayNotifier.notify("$tag: $title", body) }
            .getOrElse { error ->
                throw PluginProcessException(
                    "NOTIFICATION_FAILED",
                    "发送通知失败: ${error.message ?: error.javaClass.simpleName}"
                )
            }
        return buildJsonObject { put("ok", true) }
    }

    companion object {
        const val CHANNEL_ID = "plugin_events"
        private const val NOTIFICATION_ID = 1
        private const val MAX_TITLE_CHARS = 100
        private const val MAX_BODY_CHARS = 1_000
        private const val MAX_KEY_CHARS = 64
    }
}
