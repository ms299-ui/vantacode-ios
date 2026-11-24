package com.vantacode.feature.plugin.runtime

import com.vantacode.feature.plugin.domain.PluginHostException
import javax.inject.Inject
import javax.inject.Singleton

/** 插件调用 Shizuku 的失败原因；调用方转成页面桥/进程错误码。 */
class PluginShizukuException(val code: String, message: String) : Exception(message)

data class PluginShizukuResult(val output: String, val exitCode: Int, val truncated: Boolean)

/**
 * 插件侧的 Shizuku 执行入口。
 *
 * 桌面端没有 Shizuku（Android 专属的 adb/root 授权服务），也没有对应的 UserService 后端，
 * 因此这里只保留类与方法签名作为兼容桩：任何调用都以 SHIZUKU_UNSUPPORTED 失败，
 * 由宿主把它转成 JSON-RPC 错误码返回给插件。
 */
@Singleton
class PluginShizukuRunner @Inject constructor() {

    suspend fun exec(pluginId: String, command: String, timeoutSeconds: Long): PluginShizukuResult {
        throw PluginHostException(
            "SHIZUKU_UNSUPPORTED",
            "桌面端不支持 Shizuku，插件的 shizuku 相关能力不可用"
        )
    }
}
