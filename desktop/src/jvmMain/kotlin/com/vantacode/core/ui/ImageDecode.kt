/*

 * VantaCode Desktop — global bitmap sampling decode.
 * Android BitmapFactory replaced by javax.imageio + manual downscale.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.ui

import org.jetbrains.compose.resources.decodeToImageBitmap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vantacode.core.util.FileLogger
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO

private const val TAG = "ImageDecode"

/** 缩略图档位：聊天附件预览卡只有 76dp，采样到 180 已有余量。 */
const val THUMBNAIL_MAX_EDGE = 180

/** Markdown 正文内嵌图片档位：显示高度上限 320dp。 */
const val MARKDOWN_MAX_EDGE = 1024

/** 全屏查看器的总像素上限。 */
private const val VIEWER_MAX_PIXELS = 4_000_000L

/** 解码 OOM 后的降采样重试次数。 */
private const val OOM_RETRIES = 3

/** inSampleSize 上限，防兜底循环在畸形尺寸下失控。 */
private const val MAX_SAMPLE = 32

/**
 * 以 **最大边** 约束采样。
 * 判定用 `||` 而非 `&&`：`&&` 实际约束的是短边，2000×300 这类长截图会整个逃过采样，
 * 按全尺寸解码——AI 产出的长网页截图、终端长输出截图正好是这个形状。
 */
fun calculateInSampleSize(width: Int, height: Int, maxEdge: Int): Int {
    if (width <= 0 || height <= 0 || maxEdge <= 0) return 1
    var sample = 1
    while (sample < MAX_SAMPLE && (width / (sample * 2) >= maxEdge || height / (sample * 2) >= maxEdge)) {
        sample *= 2
    }
    return sample
}

/**
 * 在最大边约束之上再按总像素兜底：ARGB_8888 下 `maxPixels × 4` 字节就是解码峰值内存，
 * 极端长宽比的图（最大边没超限但总像素巨大）靠这一层挡住。
 */
fun calculateInSampleSize(width: Int, height: Int, maxEdge: Int, maxPixels: Long): Int {
    var sample = calculateInSampleSize(width, height, maxEdge)
    if (width <= 0 || height <= 0 || maxPixels <= 0) return sample
    while (sample < MAX_SAMPLE && (width / sample).toLong() * (height / sample) > maxPixels) {
        sample *= 2
    }
    return sample
}

/** 采样解码本地文件。文件不存在、不是图片或解码失败均返回 null。 */
fun decodeSampledBitmap(path: String, maxEdge: Int, maxPixels: Long = Long.MAX_VALUE): ImageBitmap? {
    val file = File(path)
    if (!file.isFile) return null
    val bounds = readBounds(file) ?: return null
    return decodeWithOomRetry(path, bounds, maxEdge, maxPixels) { img -> scale(img, maxEdge, maxPixels) }
}

/** 采样解码内存中的图片字节。 */
fun decodeSampledBitmap(bytes: ByteArray, maxEdge: Int, maxPixels: Long = Long.MAX_VALUE): ImageBitmap? {
    val what = "byte[${bytes.size}]"
    val bounds = readBoundsBytes(bytes) ?: return null
    return decodeWithOomRetry(what, bounds, maxEdge, maxPixels) { img -> scale(img, maxEdge, maxPixels) }
}

private fun readBounds(file: File): Pair<Int, Int>? = runCatching {
    val img = ImageIO.read(file) ?: return null
    img.width to img.height
}.getOrNull()

private fun readBoundsBytes(bytes: ByteArray): Pair<Int, Int>? = runCatching {
    val img = ImageIO.read(ByteArrayInputStream(bytes)) ?: return null
    img.width to img.height
}.getOrNull()

/** 按采样档位缩放到目标尺寸。 */
private fun scale(src: BufferedImage, maxEdge: Int, maxPixels: Long): ImageBitmap {
    var sample = calculateInSampleSize(src.width, src.height, maxEdge, maxPixels)
    var img = src
    while (sample > 1) {
        img = downscale(img, sample)
        sample /= 2
        if (calculateInSampleSize(img.width, img.height, maxEdge, maxPixels) <= 1) break
    }
    return img.awtToComposeBitmap()
}

/**
 * BufferedImage → Compose 的 ImageBitmap。
 *
 * 桌面端没有 Android 的 Bitmap/asImageBitmap，走 skia：先 PNG 编码再解码成 skia Image。
 * 比逐像素拷贝 installPixels 稳（skia-java 的像素安装 API 各版本签名不同），
 * 代价是一次 PNG 编解码——查看器一次只解一张图，可忽略。
 */
private fun BufferedImage.awtToComposeBitmap(): ImageBitmap {
    val buffer = java.io.ByteArrayOutputStream()
    ImageIO.write(this, "png", buffer)
    return buffer.toByteArray().decodeToImageBitmap()
}

private fun downscale(src: BufferedImage, factor: Int): BufferedImage {
    val w = (src.width / factor).coerceAtLeast(1)
    val h = (src.height / factor).coerceAtLeast(1)
    val dst = BufferedImage(w, h, if (src.colorModel.hasAlpha()) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
    val g = dst.createGraphics()
    g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g.drawImage(src, 0, 0, w, h, null)
    g.dispose()
    return dst
}

/**
 * 按采样档位解码，OOM 时降一档重试。
 * [OutOfMemoryError] 是 `Error` 不是 `Exception`，`runCatching {}` 的 `catch (e: Exception)` 抓不到——
 * 不显式接住的话，一张超大图就能把 App 崩掉。
 */
private fun decodeWithOomRetry(
    what: String,
    bounds: Pair<Int, Int>,
    maxEdge: Int,
    maxPixels: Long,
    decode: (BufferedImage) -> ImageBitmap?
): ImageBitmap? {
    repeat(OOM_RETRIES) {
        try {
            val img = ImageIO.read(File(what)) ?: return null
            return decode(img)
        } catch (e: OutOfMemoryError) {
            FileLogger.w(TAG, "解码 OOM，降档重试: $what")
        } catch (e: Exception) {
            FileLogger.w(TAG, "解码失败: $what", e)
            return null
        }
    }
    FileLogger.w(TAG, "解码连续 OOM，放弃: $what")
    return null
}

/** 解码档位：最大边（像素）+ 总像素上限。 */
data class ImageDecodeSpec(val maxEdge: Int, val maxPixels: Long)

/**
 * 缩略图内存缓存：聊天列表里带图消息滑出视口就会被回收，滑回来时每次都要重新读盘解码原图。
 * 按路径索引：附件落盘时已保证文件名唯一，不会被原地替换；也因此 [peek] 在组合阶段调用时不做任何文件 IO。
 * 缩略图最大边 180px，条数上限即内存上限。
 */
object ThumbnailCache {
    private const val MAX_ENTRIES = 96

    private val cache = object : LinkedHashMap<String, ImageBitmap>(32, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, ImageBitmap>?): Boolean =
            size > MAX_ENTRIES
    }

    /** 命中则同步返回（可作 produceState 初值，避免闪占位图）。 */
    fun peek(path: String): ImageBitmap? = synchronized(cache) { cache[path] }

    /** 取缓存，未命中则解码并写入；解码失败不缓存。应在后台线程调用。 */
    fun load(path: String): ImageBitmap? {
        peek(path)?.let { return it }
        val decoded = decodeSampledBitmap(path, THUMBNAIL_MAX_EDGE) ?: return null
        synchronized(cache) { cache[path] = decoded }
        return decoded
    }
}

/**
 * 全屏查看器的解码档位，跟当前窗口走而不写死。
 * 查看器允许放大看细节，故取窗口长边的 1.5 倍，夹在 1024..2048。
 */
@Composable
fun rememberViewerDecodeSpec(): ImageDecodeSpec {
    val density = LocalDensity.current
    val widthDp = DesktopConfiguration.screenWidthDp
    val heightDp = DesktopConfiguration.screenHeightDp
    return remember(widthDp, heightDp, density.density) {
        val longEdgeDp = maxOf(widthDp, heightDp)
        val longEdgePx = with(density) { longEdgeDp.dp.toPx() }.toInt()
        ImageDecodeSpec(
            maxEdge = (longEdgePx * 3 / 2).coerceIn(1024, 2048),
            maxPixels = VIEWER_MAX_PIXELS
        )
    }
}
