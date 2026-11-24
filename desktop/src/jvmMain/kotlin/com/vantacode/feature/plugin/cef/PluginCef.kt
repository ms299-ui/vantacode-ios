package com.vantacode.feature.plugin.cef

import com.vantacode.core.util.FileLogger
import me.friwi.jcefmaven.CefAppBuilder
import me.friwi.jcefmaven.CefBuildInfo
import org.cef.CefApp
import org.cef.CefClient
import org.cef.browser.CefBrowser
import org.cef.handler.CefLoadHandlerAdapter
import java.io.File

/**
 * JCEF 单例管理：首次打开插件页面时才初始化（懒加载），避免拖慢主启动链路。
 * CefApp 全局只建一次，App 退出时统一 dispose；每个页面独立 CefClient/CefBrowser。
 */
object PluginCef {
    private const val TAG = "PluginCef"

    @Volatile
    private var cefApp: CefApp? = null
    private val lock = Any()

    /** 是否可用；初始化失败时保持 false，UI 走降级。 */
    @Volatile
    var available: Boolean = false
        private set

    /** 初始化失败原因，供 UI 展示。 */
    @Volatile
    var failure: String? = null
        private set

    val installDir: File
        get() = File(System.getProperty("user.home"), ".vantacode/jcef")

    /** 线程安全地获取 CefApp，失败抛异常由调用方捕获降级。 */
    fun app(): CefApp {
        cefApp?.let { return it }
        return synchronized(lock) {
            cefApp?.let { return@synchronized it }
            val built = try {
                val builder = CefAppBuilder()
                builder.setInstallDir(installDir)
                builder.cefSettings.windowless_rendering_enabled = true // OSR，SwingPanel 必需
                builder.cefSettings.cache_path = File(installDir, "cache").absolutePath
                // CEF 原生库解包进度写日志，不阻塞
                builder.setProgressHandler { progress, message ->
                    FileLogger.d(TAG, "JCEF 安装 $progress%: $message")
                }
                builder.build()
            } catch (e: Throwable) {
                failure = e.message ?: e::class.simpleName
                FileLogger.e(TAG, "JCEF 初始化失败", e)
                throw e
            }
            cefApp = built
            available = true
            built
        }
    }

    /** 新建一个 CefClient，挂上请求拦截与桥接 handler。 */
    fun newClient(
        requestHandler: org.cef.handler.CefRequestHandler,
        bridgeHandler: org.cef.handler.CefMessageRouterHandler
    ): CefClient {
        val client = app().createClient()
        client.addRequestHandler(requestHandler)
        val router = org.cef.browser.CefMessageRouter.create(
            org.cef.browser.CefMessageRouter.CefMessageRouterConfig("__aicodeBridgeQuery", "__aicodeBridgeCancel")
        )
        router.addHandler(bridgeHandler, true)
        client.addMessageRouter(router)
        return client
    }

    /** 关闭单个页面：只关 browser/client，不 dispose 全局 CefApp。 */
    fun closePage(client: CefClient, browser: CefBrowser) {
        runCatching {
            browser.close(true)
            client.dispose()
        }.onFailure { FileLogger.w(TAG, "关闭 JCEF 页面失败", it) }
    }

    /** App 退出时调用。 */
    fun shutdown() {
        runCatching { cefApp?.dispose() }
        cefApp = null
        available = false
    }
}
