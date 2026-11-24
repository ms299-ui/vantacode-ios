/*
 * VantaCode Desktop — background media frame decoders (GIF / MP4).
 * 纯 JVM 实现：GIF 走 javax.imageio 读全部帧再按 disposal 规则合成；
 * MP4 走 jcodec（MIT）逐帧顺序解码。两者都 downscale 到 maxEdge 控制内存。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.media

import com.vantacode.core.util.FileLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.ImageReader
import javax.imageio.metadata.IIOMetadata
import javax.imageio.stream.ImageInputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.io.StringWriter

/** 解码后的 GIF：每帧一张 downscale 后的 ARGB 图 + 延时（毫秒，已做下限钳制）。 */
class DecodedGif(
    val frames: List<BufferedImage>,
    val delaysMs: IntArray,
    val width: Int,
    val height: Int,
)

object GifFrameDecoder {
    private const val TAG = "GifFrameDecoder"
    /** 超过这个帧数就按比例抽帧，避免一条 500 帧的长 GIF 吃光内存。 */
    private const val MAX_FRAMES = 150
    /** 单帧解码延时的下限（GIF 标准里 0/10ms 常被浏览器钳成 ~20ms）。 */
    private const val MIN_DELAY_MS = 20

    suspend fun decode(file: File, maxEdge: Int): DecodedGif? = withContext(Dispatchers.IO) {
        runCatching { decodeInternal(file, maxEdge) }
            .onFailure { FileLogger.w(TAG, "GIF 解码失败: ${file.absolutePath}", it) }
            .getOrNull()
    }

    private fun decodeInternal(file: File, maxEdge: Int): DecodedGif? {
        val reader = ImageIO.getImageReadersBySuffix("gif").asSequence().firstOrNull() ?: return null
        ImageIO.createImageInputStream(file).use { iis: ImageInputStream ->
            reader.input = iis
            val frameCount = try { reader.getNumImages(true) } catch (_: Exception) { 0 }
            if (frameCount <= 0) return null

            // 逻辑屏幕尺寸（某些 GIF 帧比画布小），从 stream metadata 读，失败回退到首帧尺寸
            var logicalW = 0
            var logicalH = 0
            runCatching {
                val meta = reader.streamMetadata
                val (w, h) = logicalScreenSize(meta)
                logicalW = w; logicalH = h
            }
            if (logicalW <= 0 || logicalH <= 0) {
                logicalW = reader.getWidth(0)
                logicalH = reader.getHeight(0)
            }

            // downscale 比例：等比缩到 maxEdge 以内
            val scale = minOf(1f, maxEdge.toFloat() / maxOf(logicalW, logicalH))
            val targetW = (logicalW * scale).toInt().coerceAtLeast(1)
            val targetH = (logicalH * scale).toInt().coerceAtLeast(1)

            val canvas = BufferedImage(logicalW, logicalH, BufferedImage.TYPE_INT_ARGB)
            val graphics = canvas.createGraphics()
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)

            data class FrameMeta(val delayMs: Int, val disposal: Int, val left: Int, val top: Int, val width: Int, val height: Int)

            // 抽帧步长：超过 MAX_FRAMES 时跳帧
            val step = if (frameCount <= MAX_FRAMES) 1 else kotlin.math.ceil(frameCount.toDouble() / MAX_FRAMES).toInt()
            val outFrames = ArrayList<BufferedImage>(frameCount / step + 1)
            val outDelays = ArrayList<Int>(frameCount / step + 1)

            var prevDisposal = 0
            var prevRect: IntArray? = null
            var savedCanvas: BufferedImage? = null
            var mergedDelay = 0
            var mergedCount = 0

            var index = 0
            while (index < frameCount) {
                val disposal = frameDisposal(reader, index)
                val (left, top) = framePosition(reader, index)
                val (fw, fh) = frameSize(reader, index)
                val delay = frameDelayMs(reader, index)

                // 上一帧的 disposal 在现在才生效
                when (prevDisposal) {
                    2 -> prevRect?.let { graphics.clearRect(it[0], it[1], it[2], it[3]) } // restoreToBackgroundColor
                    3 -> savedCanvas?.let { snap -> graphics.clearRect(0, 0, logicalW, logicalH); graphics.drawImage(snap, 0, 0, null) } // restoreToPrevious
                }
                if (disposal == 3) {
                    savedCanvas = BufferedImage(logicalW, logicalH, BufferedImage.TYPE_INT_ARGB).also { it.createGraphics().drawImage(canvas, 0, 0, null) }
                }

                val frame = reader.read(index) ?: break
                graphics.drawImage(frame, left, top, null)

                if (index % step == 0) {
                    val scaled = scaleTo(canvas, targetW, targetH)
                    if (mergedCount > 0) {
                        // 抽帧合并：延时累加给保留的帧
                        outDelays[outDelays.size - 1] = outDelays[outDelays.size - 1] + mergedDelay
                        mergedDelay = 0
                        mergedCount = 0
                    }
                    outFrames.add(scaled)
                    outDelays.add(delay)
                } else {
                    mergedDelay += delay
                    mergedCount += 1
                }

                prevDisposal = disposal
                prevRect = intArrayOf(left, top, fw.coerceAtLeast(1), fh.coerceAtLeast(1))
                index += 1
            }
            graphics.dispose()
            if (outFrames.isEmpty()) return null
            // 尾部被抽掉的延时补给最后一帧
            if (mergedDelay > 0) outDelays[outDelays.size - 1] = outDelays[outDelays.size - 1] + mergedDelay
            return DecodedGif(outFrames, outDelays.toIntArray(), targetW, targetH)
        }
    }

    private fun scaleTo(src: BufferedImage, w: Int, h: Int): BufferedImage {
        if (src.width == w && src.height == h) {
            val copy = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
            copy.createGraphics().apply { drawImage(src, 0, 0, null); dispose() }
            return copy
        }
        val dst = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        dst.createGraphics().apply {
            setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
            drawImage(src, 0, 0, w, h, null)
            dispose()
        }
        return dst
    }

    /** GIF stream metadata → 逻辑屏幕尺寸；任何一步失败都返回 (0,0)。 */
    private fun logicalScreenSize(meta: IIOMetadata?): Pair<Int, Int> {
        val xml = metadataToXml(meta ?: return 0 to 0) ?: return 0 to 0
        val match = Regex("<LogicalScreenDescriptor[^>]*width=\"(\\d+)\"[^>]*height=\"(\\d+)\"").find(xml)
            ?: Regex("<LogicalScreenDescriptor[^>]*height=\"(\\d+)\"[^>]*width=\"(\\d+)\"").find(xml)
            ?: return 0 to 0
        return if (match.groupValues[0].indexOf("width=") < match.groupValues[0].indexOf("height=")) {
            match.groupValues[1].toInt() to match.groupValues[2].toInt()
        } else {
            match.groupValues[2].toInt() to match.groupValues[1].toInt()
        }
    }

    private fun frameDelayMs(reader: ImageReader, index: Int): Int {
        val xml = metadataToXml(runCatching { reader.getImageMetadata(index) }.getOrNull() ?: return 100) ?: return 100
        val delay = Regex("<GraphicControlExtension[^>]*delayTime=\"(\\d+)\"").find(xml)?.groupValues?.get(1)?.toIntOrNull() ?: 10
        return (delay * 10).coerceAtLeast(MIN_DELAY_MS)
    }

    /** disposal: 0=none 1=doNotDispose 2=restoreToBackgroundColor 3=restoreToPrevious */
    private fun frameDisposal(reader: ImageReader, index: Int): Int {
        val xml = metadataToXml(runCatching { reader.getImageMetadata(index) }.getOrNull() ?: return 0) ?: return 0
        return Regex("<GraphicControlExtension[^>]*disposalMethod=\"([^\"]+)\"").find(xml)?.groupValues?.get(1)?.let { token ->
            when (token.lowercase()) {
                "restoretobackgroundcolor", "restoretobackground" -> 2
                "restoretoprevious" -> 3
                else -> 0
            }
        } ?: 0
    }

    private fun framePosition(reader: ImageReader, index: Int): Pair<Int, Int> {
        val xml = metadataToXml(runCatching { reader.getImageMetadata(index) }.getOrNull() ?: return 0 to 0) ?: return 0 to 0
        val left = Regex("<ImageDescriptor[^>]*imageLeftPosition=\"(\\d+)\"").find(xml)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        val top = Regex("<ImageDescriptor[^>]*imageTopPosition=\"(\\d+)\"").find(xml)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        return left to top
    }

    private fun frameSize(reader: ImageReader, index: Int): Pair<Int, Int> {
        val w = try { reader.getWidth(index) } catch (_: Exception) { 1 }
        val h = try { reader.getHeight(index) } catch (_: Exception) { 1 }
        return w to h
    }

    private fun metadataToXml(meta: IIOMetadata?): String? = runCatching {
        val format = meta?.nativeMetadataFormatName ?: return null
        val node = meta.getAsTree(format)
        val tf = TransformerFactory.newInstance().newTransformer()
        val out = StringWriter()
        tf.transform(DOMSource(node), StreamResult(out))
        out.toString()
    }.getOrNull()
}

/** MP4 顺序逐帧解码器（jcodec）。用完必须 [close]。线程不安全，单线程使用。 */
class Mp4FrameDecoder(private val file: File) : AutoCloseable {
    private var grab: AWTFrameGrab? = null
    private var channel: org.jcodec.common.io.SeekableByteChannel? = null
    val frameIntervalMs: Long
    private var consecutiveFailures = 0

    init {
        val ch = NIOUtils.readableChannel(file)
        channel = ch
        val g = AWTFrameGrab.createAWTFrameGrab(ch)
        grab = g
        val meta = g.videoTrack?.meta
        val totalFrames = meta?.totalFrames ?: 0
        val totalDurationSec = meta?.totalDuration ?: 0.0
        frameIntervalMs = if (totalFrames > 0 && totalDurationSec > 0.0) {
            (totalDurationSec * 1000.0 / totalFrames).toLong().coerceIn(20L, 1000L)
        } else {
            33L
        }
    }

    /** 解下一帧；到结尾（帧为 null）或异常时自动回卷到第一帧。全部失败返回 null。 */
    fun nextFrame(): BufferedImage? = runCatching {
        val g = grab ?: return@runCatching null
        if (consecutiveFailures >= 5) return@runCatching null
        try {
            g.frame?.also { consecutiveFailures = 0 } ?: rollbackToStart(g)
        } catch (_: Exception) {
            rollbackToStart(g)
        }
    }.onFailure { FileLogger.w(TAG, "MP4 解帧失败", it) }.getOrNull()

    /** 解一帧（不回卷），到轨道结尾返回 null。用于预解码整个循环。 */
    fun frameOrNull(): BufferedImage? = runCatching { grab?.frame }.getOrNull()

    /**
     * 预解码整表帧到内存（GIF 播放器直接循环）。
     * 帧数超过 [maxFrames] 时返回 null，调用方回退到流式解码。
     */
    fun preloadFrames(maxEdge: Int, maxFrames: Int): DecodedGif? {
        val frames = ArrayList<BufferedImage>(maxFrames)
        while (frames.size < maxFrames) {
            val next = frameOrNull() ?: break
            frames += resizeToFit(next, maxEdge)
        }
        close()
        if (frames.size < 2) return null
        val delays = IntArray(frames.size) { frameIntervalMs.coerceIn(20L, 1000L).toInt() }
        return DecodedGif(frames, delays, frames.first().width, frames.first().height)
    }

    private fun rollbackToStart(g: AWTFrameGrab): BufferedImage? =
        runCatching {
            g.seekToFramePrecise(0)
            val frame = g.frame
            consecutiveFailures = if (frame == null) consecutiveFailures + 1 else 0
            frame
        }.getOrElse {
            consecutiveFailures += 1
            null
        }

    fun resizeToFit(src: BufferedImage, maxEdge: Int): BufferedImage {
        val maxSrc = maxOf(src.width, src.height)
        if (maxSrc <= maxEdge) return src
        val scale = maxEdge.toFloat() / maxSrc
        val w = (src.width * scale).toInt().coerceAtLeast(1)
        val h = (src.height * scale).toInt().coerceAtLeast(1)
        val dst = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        dst.createGraphics().apply {
            setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
            drawImage(src, 0, 0, w, h, null)
            dispose()
        }
        return dst
    }

    override fun close() {
        runCatching { channel?.close() }
        channel = null
        grab = null
    }

    private companion object {
        const val TAG = "Mp4FrameDecoder"
    }
}
