package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import com.vantacode.core.platform.DesktopAssets
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.Strings
import com.vantacode.feature.plugin.cef.PluginCef
import com.vantacode.feature.plugin.cef.PluginCefBridgeHandler
import com.vantacode.feature.plugin.cef.PluginCefRequestHandler
import com.vantacode.feature.plugin.domain.PluginPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.cef.CefApp
import org.cef.CefClient
import org.cef.browser.CefBrowser
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * 插件 HTML 页面宿主（JCEF / OSR + SwingPanel）。
 * 首次打开时懒初始化 CEF 并显示进度；失败时降级为文字提示，不阻塞主启动链路。
 */
@Composable
internal fun PluginPageHostDesktop(
    page: PluginPage,
    onBack: () -> Unit
) {
    val lang = if (Strings.current == "en") "en" else "zh-CN"

    // 懒初始化：CEF 首次解包 ~163MiB 可能耗时数秒，放到 IO 线程并显示进度
    val session by produceState<Session?>(initialValue = null, key1 = page.cacheKey) {
        value = withContext(Dispatchers.IO) {
            runCatching { createSession(page, onBack) }.getOrNull()
        }
    }

    when {
        session == null && PluginCef.failure == null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(
                    text = "正在初始化插件浏览器…",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = Spacing.md)
                )
            }
        }

        session == null -> Column(
            modifier = Modifier.fillMaxSize().padding(Spacing.lg),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Spacing.md)
        ) {
            TextButton(onClick = onBack) { Text(Strings.common_back) }
            Text(page.title.resolve(lang), style = MaterialTheme.typography.titleLarge)
            Text(Strings.plugin_page_unsupported, style = MaterialTheme.typography.bodyMedium)
            PluginCef.failure?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }

        else -> {
            val s = session!!
            DisposableEffect(s) {
                onDispose { s.close() }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                TextButton(onClick = onBack) { Text(Strings.common_back) }
                SwingPanel(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    factory = { GuardPanel(s.browser) }
                )
            }
        }
    }
}

private class Session(
    val app: CefApp,
    val client: CefClient,
    val browser: CefBrowser,
    val bridge: PluginCefBridgeHandler
) {
    fun close() {
        bridge.markClosed()
        PluginCef.closePage(client, browser)
    }
}

private suspend fun createSession(page: PluginPage, onClose: () -> Unit): Session {
    // 用户打开配置页即表示要使用该插件；若处于停用状态先启用，避免 ui.invoke 被 requireActive 拒绝。
    val pluginManager = com.vantacode.di.AppGraph.pluginManager
    if (pluginManager.plugins.value.firstOrNull { it.id == page.pluginId }?.isActive != true) {
        runCatching { pluginManager.setEnabled(page.pluginId, true) }
    }
    val app = PluginCef.app()
    val requestHandler = PluginCefRequestHandler(
        installDir = page.installDir,
        hostAsset = { name ->
            runCatching {
                DesktopAssets.open("plugin-ui/$name").use { it.readBytes() }
            }.getOrNull()
        },
        themeCss = { ":root { color-scheme: light dark; }" }
    )
    val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.SupervisorJob() + Dispatchers.IO)
    val bridge = PluginCefBridgeHandler(
        page = page,
        scope = scope,
        pluginManager = com.vantacode.di.AppGraph.pluginManager,
        runtimeManager = com.vantacode.di.AppGraph.pluginRuntimeManager,
        authorized = { true },
        onClose = onClose,
        onToast = { msg -> com.vantacode.core.platform.PlatformToast.show(msg) }
    )
    val client = PluginCef.newClient(requestHandler, bridge)
    val url = "${PluginCefRequestHandler.ORIGIN}${PluginCefRequestHandler.PLUGIN}${page.pageFile}"
    val browser = client.createBrowser(url, true, false) // OSR
    return Session(app, client, browser, bridge)
}

/**
 * 抑制关窗时的 invalidate/repaint 冒泡——Compose 先 dispose SkiaLayer，AWT removeNotify
 * 再触发 JCEF removeCanvas → invalidate，冒泡到已销毁的 interop 层会 NPE。
 */
private class GuardPanel(browser: CefBrowser) : JPanel(BorderLayout()) {
    private var removing = false

    init {
        add(browser.uiComponent, BorderLayout.CENTER)
        isFocusable = true
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                browser.setFocus(true)
                requestFocusInWindow()
            }
        })
    }

    override fun removeNotify() {
        removing = true
        super.removeNotify()
    }

    override fun invalidate() {
        if (removing) return
        super.invalidate()
    }

    override fun repaint() {
        if (removing) return
        super.repaint()
    }
}
