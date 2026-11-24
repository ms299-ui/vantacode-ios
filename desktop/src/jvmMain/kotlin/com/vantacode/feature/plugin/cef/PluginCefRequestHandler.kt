package com.vantacode.feature.plugin.cef

import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefCallback
import org.cef.handler.CefRequestHandlerAdapter
import org.cef.handler.CefResourceHandlerAdapter
import org.cef.handler.CefResourceRequestHandler
import org.cef.handler.CefResourceRequestHandlerAdapter
import org.cef.misc.BoolRef
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefRequest
import org.cef.network.CefResponse
import java.io.File
import java.net.URI

/**
 * 把插件 HTML 页面与宿主 JS 资源映射到同一个虚拟 origin（复用 Android 域名），
 * 保证 aicode.js / aicode-ui-vue.js 的相对路径引用原样可用。附 CSP 与路径穿越防护。
 */
class PluginCefRequestHandler(
    private val installDir: File,
    private val hostAsset: (String) -> ByteArray?,
    private val themeCss: () -> String
) : CefRequestHandlerAdapter() {

    override fun getResourceRequestHandler(
        browser: CefBrowser?,
        frame: CefFrame?,
        request: CefRequest,
        isNavigation: Boolean,
        isDownload: Boolean,
        requestInitiator: String?,
        disableDefaultHandling: BoolRef
    ): CefResourceRequestHandler? {
        val url = request.url ?: return null
        if (!url.startsWith(ORIGIN)) return null
        disableDefaultHandling.set(true)
        return object : CefResourceRequestHandlerAdapter() {
            override fun getResourceHandler(
                b: CefBrowser?, f: CefFrame?, r: CefRequest
            ): org.cef.handler.CefResourceHandler {
                val path = runCatching { URI(r.url).path }.getOrNull() ?: "/"
                val resource = when {
                    path.startsWith(PLUGIN) -> fileResource(path.removePrefix(PLUGIN))
                    path.startsWith(HOST) -> hostResource(path.removePrefix(HOST))
                    else -> null
                }
                return BytesResourceHandler(resource, isHtml = resource?.mime == "text/html")
            }
        }
    }

    /** 外链交给系统浏览器，不在插件面板里加载。 */
    override fun onBeforeBrowse(
        browser: CefBrowser?, frame: CefFrame?, request: CefRequest,
        isRedirect: Boolean, isTransfer: Boolean
    ): Boolean {
        val url = request.url ?: return false
        if (url.startsWith(ORIGIN) || url.startsWith("about:", true)) return false
        runCatching {
            java.awt.Desktop.getDesktop().browse(URI(url))
        }
        return true
    }

    private fun fileResource(relative: String): Resource? {
        val file = File(installDir, relative).canonicalFile
        val prefix = installDir.canonicalFile.path + File.separator
        if (file.path == installDir.canonicalFile.path || !file.path.startsWith(prefix) || !file.isFile) {
            return null
        }
        val mime = mimeOf(file.name)
        return Resource(file.readBytes(), mime)
    }

    private fun hostResource(relative: String): Resource? {
        if (relative.isEmpty() || relative.split('/').any { it == ".." || it == "." }) return null
        return when (relative) {
            "theme.css" -> Resource(themeCss().toByteArray(Charsets.UTF_8), "text/css")
            // 品牌别名：兼容老插件里引用 muse.js 的写法
            "muse.js" -> hostAsset("aicode.js")?.let { Resource(it, "application/javascript") }
            else -> hostAsset(relative)?.let { Resource(it, mimeOf(relative)) }
        }
    }

    private data class Resource(val bytes: ByteArray, val mime: String)

    /** 旧版 resource handler API，JCEF 会回退到 processRequest / readResponse。 */
    private class BytesResourceHandler(
        private val resource: Resource?,
        private val isHtml: Boolean
    ) : CefResourceHandlerAdapter() {
        private var offset = 0
        private var data = resource?.bytes ?: ByteArray(0)

        override fun processRequest(request: CefRequest?, callback: CefCallback): Boolean {
            if (resource == null) {
                callback.cancel()
                return false
            }
            // HTML 注入桥 shim + CSP meta（桥 shim 见 BRIDGE_SHIM）
            if (isHtml) {
                val text = String(data, Charsets.UTF_8)
                data = injectBridge(text).toByteArray(Charsets.UTF_8)
            }
            callback.Continue()
            return true
        }

        override fun getResponseHeaders(
            response: CefResponse,
            responseLength: IntRef,
            redirectUrl: StringRef
        ) {
            if (resource == null) {
                response.status = 404
                responseLength.set(0)
                return
            }
            response.status = 200
            response.mimeType = resource.mime
            response.setHeaderByName("Cache-Control", "no-store", true)
            if (isHtml) {
                response.setHeaderByName("Content-Security-Policy", HTML_CSP, true)
            }
            responseLength.set(data.size)
        }

        override fun readResponse(
            dataOut: ByteArray,
            bytesToRead: Int,
            bytesRead: IntRef,
            callback: CefCallback
        ): Boolean {
            if (offset >= data.size) {
                bytesRead.set(0)
                return false
            }
            val n = minOf(bytesToRead, data.size - offset)
            System.arraycopy(data, offset, dataOut, 0, n)
            offset += n
            bytesRead.set(n)
            return true
        }

        override fun cancel() {}
    }

    companion object {
        const val ORIGIN = "https://appassets.androidplatform.net"
        const val PLUGIN = "/plugin/"
        const val HOST = "/__aicode/"

        // 与 Android PluginWebViewFactory 一致，保留 Vue/远程图片所需的能力
        private const val HTML_CSP =
            "default-src 'self' $ORIGIN data: blob:; " +
                "script-src 'self' $ORIGIN 'unsafe-inline' 'unsafe-eval'; " +
                "style-src 'self' $ORIGIN 'unsafe-inline' https: data:; " +
                "img-src 'self' $ORIGIN data: blob: https:; " +
                "font-src 'self' $ORIGIN data: https:; " +
                "media-src 'self' $ORIGIN data: blob: https:; " +
                "connect-src 'self' $ORIGIN http: https:; " +
                "frame-src 'none'; object-src 'none'; base-uri 'none'"

        /**
         * 注入把 __aicodeBridge.postMessage 适配到 cefQuery 的 shim。
         * 插件自带的 aicode.js 契约不变：宿主回包走 window.__aicodeResolve(id, json)。
         */
        private fun injectBridge(html: String): String {
            val shim = """<script>
(function () {
  window.__aicodeBridge = {
    postMessage: function (payload) {
      var id = null;
      try { id = JSON.parse(payload).id; } catch (e) {}
      window.__aicodeBridgeQuery({
        request: payload, persistent: false,
        onSuccess: function (reply) {
          if (id != null && window.__aicodeResolve) window.__aicodeResolve(id, reply);
        },
        onFailure: function (code, msg) {
          if (id != null && window.__aicodeResolve) {
            window.__aicodeResolve(id, JSON.stringify({ok:false,error:{code:'BRIDGE_'+code,message:msg}}));
          }
        }
      });
    }
  };
})();
</script>"""
            val idx = html.indexOf("</head>")
            return if (idx >= 0) html.substring(0, idx) + shim + "\n" + html.substring(idx) else shim + "\n" + html
        }

        private fun mimeOf(name: String): String = when (name.substringAfterLast('.').lowercase()) {
            "html", "htm" -> "text/html"
            "js" -> "application/javascript"
            "css" -> "text/css"
            "json" -> "application/json"
            "svg" -> "image/svg+xml"
            "png" -> "image/png"
            "jpg", "jpeg" -> "image/jpeg"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            "md", "txt" -> "text/plain; charset=utf-8"
            else -> "application/octet-stream"
        }
    }
}
