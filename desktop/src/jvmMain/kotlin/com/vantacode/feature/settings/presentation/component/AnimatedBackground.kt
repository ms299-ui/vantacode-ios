/*
 * VantaCode Desktop — GIF / MP4 背景绘制组件。
 * GIF 按解码出的帧表循环播放（delay 逐帧推进）；MP4 优先整表预解码（原生帧率零开销循环），
 * 预解码失败再回退流式解码。两类背景都按 ContentScale.Crop 等比例裁剪铺满，透明度由外部控制。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.settings.presentation.component

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.foundation.Canvas
import com.vantacode.core.media.DecodedGif
import com.vantacode.core.media.GifFrameDecoder
import com.vantacode.core.media.Mp4FrameDecoder
import com.vantacode.core.media.FfmpegLocator
import com.vantacode.core.media.FfmpegVideoSource
import com.vantacode.core.util.FileLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.ImageInfo
import java.awt.image.BufferedImage
import java.io.File
import kotlin.math.max
import kotlin.math.roundToInt

private const val TAG = "AnimatedBackground"
private const val GIF_MAX_EDGE = 640
private const val VIDEO_MAX_EDGE = 640
private const val VIDEO_PRELOAD_MAX_EDGE = 480
private const val MAX_PRELOAD_FRAMES = 180

/** BufferedImage(TYPE_INT_ARGB) → Compose ImageBitmap（BGRA_8888 直接拷贝像素）。 */
private fun BufferedImage.toComposeImageBitmap(): ImageBitmap {
    val w = width
    val h = height
    val argb = getRGB(0, 0, w, h, null, 0, w)
    val bytes = ByteArray(w * h * 4)
    var i = 0
    var o = 0
    while (i < argb.size) {
        val v = argb[i++]
        bytes[o++] = v.toByte() // B
        bytes[o++] = (v shr 8).toByte() // G
        bytes[o++] = (v shr 16).toByte() // R
        bytes[o++] = (v shr 24).toByte() // A
    }
    val info = ImageInfo(w, h, ColorType.BGRA_8888, ColorAlphaType.UNPREMUL)
    val bitmap = Bitmap()
    bitmap.installPixels(info, bytes, w * 4)
    return bitmap.asComposeImageBitmap()
}

/** Crop 效果：等比放大到铺满画布，居中裁剪。 */
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawImageCrop(image: ImageBitmap) {
    val cw = size.width
    val ch = size.height
    val iw = image.width.toFloat()
    val ih = image.height.toFloat()
    if (cw <= 0f || ch <= 0f || iw <= 0f || ih <= 0f) return
    val scale = max(cw / iw, ch / ih)
    val dw = (iw * scale).roundToInt()
    val dh = (ih * scale).roundToInt()
    drawImage(
        image = image,
        dstOffset = IntOffset(((cw - dw) / 2f).roundToInt(), ((ch - dh) / 2f).roundToInt()),
        dstSize = IntSize(dw, dh)
    )
}

/** GIF 背景：解码整表帧后按各帧 delay 循环。解码失败则什么都不画（与未设置背景一致）。 */
@Composable
fun GifBackground(
    path: String,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    val decoded by produceState<DecodedGif?>(initialValue = null, path) {
        value = GifFrameDecoder.decode(File(path), GIF_MAX_EDGE)
    }
    val gif = decoded ?: return
    var frameIndex by remember(gif) { mutableIntStateOf(0) }
    LaunchedEffect(gif) {
        while (isActive) {
            val delayMs = gif.delaysMs[frameIndex % gif.delaysMs.size].toLong()
            delay(delayMs)
            frameIndex = (frameIndex + 1) % gif.frames.size
        }
    }
    val frame = gif.frames[frameIndex % gif.frames.size]
    val bitmap = remember(gif, frameIndex) { frame.toComposeImageBitmap() }
    Canvas(modifier = modifier.alpha(alpha)) {
        drawImageCrop(bitmap)
    }
}

/** 预解码状态：Loading（解码中，先不渲染起）→ Ready（循环零开销）→ Failed（回退流式）。 */
private sealed interface VideoPreloadState {
    data object Loading : VideoPreloadState
    data class Ready(val decoded: DecodedGif) : VideoPreloadState
    data object Failed : VideoPreloadState
}

/** MP4 背景：优先整表预解码（121 帧左右的小视频循环零开销），失败回退流式解码。 */
@Composable
fun VideoBackground(
    path: String,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    // 系统有 ffmpeg 时优先用它解码（原生性能，60fps 余量）；没有再走 jcodec 预解码/流式兜底。
    val hasFfmpeg = remember { FfmpegLocator.exe != null }
    if (hasFfmpeg) {
        FfmpegVideoBackground(path = path, alpha = alpha, modifier = modifier)
        return
    }

    val state by produceState<VideoPreloadState>(initialValue = VideoPreloadState.Loading, path) {
        value = withContext(Dispatchers.IO) {
            val result = runCatching {
                Mp4FrameDecoder(File(path)).preloadFrames(VIDEO_PRELOAD_MAX_EDGE, MAX_PRELOAD_FRAMES)
            }.onSuccess { FileLogger.i(TAG, "MP4 预解码完成: path=$path frames=${it?.frames?.size ?: 0}") }
                .onFailure { FileLogger.w(TAG, "MP4 预解码失败: $path", it) }
                .getOrNull()
            if (result != null) VideoPreloadState.Ready(result) else VideoPreloadState.Failed
        }
    }

    when (val s = state) {
        is VideoPreloadState.Loading -> {
            // 解码期间先空着，避免和流式解码抢 CPU 互相拖慢
        }
        is VideoPreloadState.Ready -> {
            val decoded = s.decoded
            var frameIndex by remember(decoded) { mutableIntStateOf(0) }
            LaunchedEffect(decoded) {
                while (isActive) {
                    delay(decoded.delaysMs[frameIndex % decoded.delaysMs.size].toLong())
                    frameIndex = (frameIndex + 1) % decoded.frames.size
                }
            }
            val frame = decoded.frames[frameIndex % decoded.frames.size]
            val bitmap = remember(decoded, frameIndex) { frame.toComposeImageBitmap() }
            Canvas(modifier = modifier.alpha(alpha)) {
                drawImageCrop(bitmap)
            }
        }
        is VideoPreloadState.Failed -> StreamingVideoBackground(path = path, alpha = alpha, modifier = modifier)
    }
}

/** FFmpeg 子进程播放（PATH 上有 ffmpeg 时优先）：rawvideo 帧直接取管道塞进 Compose。 */
@Composable
private fun FfmpegVideoBackground(
    path: String,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    var frame by remember(path) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(path) {
        withContext(Dispatchers.IO) {
            val source = try {
                FfmpegVideoSource(File(path))
            } catch (e: Exception) {
                FileLogger.w(TAG, "ffmpeg 打开 MP4 失败: $path", e)
                return@withContext
            }
            try {
                source.start()
                while (isActive) {
                    val bitmap = source.readFrame() ?: break
                    frame = bitmap
                    delay(source.frameIntervalMs)
                }
            } finally {
                source.close()
            }
        }
    }
    val bitmap = frame
    if (bitmap != null) {
        Canvas(modifier = modifier.alpha(alpha)) {
            drawImageCrop(bitmap)
        }
    }
}

/** MP4 流式解码（预解码失败时的兜底）：jcodec 顺序解码逐帧上屏，到结尾自动回卷。 */
@Composable
private fun StreamingVideoBackground(
    path: String,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    var frame by remember(path) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(path) {
        withContext(Dispatchers.IO) {
            val decoder = try {
                Mp4FrameDecoder(File(path))
            } catch (e: Exception) {
                FileLogger.w(TAG, "打开 MP4 失败: $path", e)
                return@withContext
            }
            try {
                while (isActive) {
                    val startedNs = System.nanoTime()
                    val next = decoder.nextFrame()
                    if (next == null) {
                        delay(120)
                        continue
                    }
                    val scaled = decoder.resizeToFit(next, VIDEO_MAX_EDGE)
                    frame = scaled.toComposeImageBitmap()
                    val elapsedMs = (System.nanoTime() - startedNs) / 1_000_000
                    delay((decoder.frameIntervalMs - elapsedMs).coerceAtLeast(0L))
                }
            } finally {
                decoder.close()
            }
        }
    }
    val bitmap = frame
    if (bitmap != null) {
        Canvas(modifier = modifier.alpha(alpha)) {
            drawImageCrop(bitmap)
        }
    }
}
