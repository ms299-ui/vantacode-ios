/*
 * VantaCode Desktop — FFmpeg 子进程视频背景源。
 * 优先用系统 ffmpeg（PATH 或 VANTACODE_FFMPEG / -Dvantacode.ffmpeg）把视频解码成
 * rawvideo RGBA 帧流，按帧率喂给 Compose Canvas；找不到 ffmpeg 时上层回退 jcodec。
 * 60fps 源文件 -stream_loop -1 管道内连续解码，不额外占用磁盘缓存。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.media

import com.vantacode.core.util.FileLogger
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.ImageInfo
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit

/** 找 ffmpeg 可执行文件：环境变量 > -D 属性 > 首启解包的捆绑版 > PATH。 */
object FfmpegLocator {
    private const val TAG = "FfmpegLocator"

    val exe: String? by lazy {
        val candidates = mutableListOf<String>()
        System.getenv("VANTACODE_FFMPEG")?.takeIf { it.isNotBlank() }?.let { candidates += it }
        System.getProperty("vantacode.ffmpeg")?.takeIf { it.isNotBlank() }?.let { candidates += it }
        // 捆绑版：首次使用解包到 tools/ffmpeg（Windows 10+ 自带 tar.exe 可解 7z）
        extractBundled()?.let { candidates += it }
        // 系统 PATH（Gyan 等常见安装位置）
        (System.getenv("PATH") ?: "").split(File.pathSeparator).filter { it.isNotBlank() }
            .forEach { candidates += File(it, "ffmpeg.exe").absolutePath }
        candidates.firstOrNull { File(it).isFile }?.also {
            FileLogger.i(TAG, "使用 ffmpeg: $it")
        }
    }

    /** 解包捆绑的 ffmpeg.7z 到 ~/.vantacode/tools/ffmpeg/，解出来的 ffmpeg.exe 路径；失败返回 null。 */
    private fun extractBundled(): String? {
        val toolsDir = File(com.vantacode.core.platform.PlatformPaths.filesDir, "tools/ffmpeg")
        toolsDir.mkdirs()
        // 已解过就找 ffmpeg.exe（不管子目录名带不带版本号）
        toolsDir.walkTopDown().firstOrNull { it.name.equals("ffmpeg.exe", ignoreCase = true) }?.let {
            return it.absolutePath
        }
        return runCatching {
            val tarExe = File(System.getenv("SystemRoot") ?: "C:\\Windows", "System32/tar.exe")
            if (!tarExe.isFile) {
                FileLogger.w(TAG, "未找到 tar.exe，无法解包捆绑 ffmpeg")
                return null
            }
            // 从 classpath 读出捆绑的 7z（34 MB，解一次就行）
            val assetBytes = com.vantacode.core.platform.DesktopAssets.open("tools/ffmpeg.7z").use { it.readBytes() }
            val tmp = File.createTempFile("ffmpeg", ".7z").apply { deleteOnExit() }
            tmp.outputStream().use { it.write(assetBytes) }
            val process = ProcessBuilder(
                tarExe.absolutePath, "-xf", tmp.absolutePath, "-C", toolsDir.absolutePath
            ).redirectErrorStream(false).start()
            val finished = process.waitFor(90L, java.util.concurrent.TimeUnit.SECONDS)
            tmp.delete()
            if (!finished || process.exitValue() != 0) {
                FileLogger.w(TAG, "解包捆绑 ffmpeg 失败: finished=$finished exit=${runCatching { process.exitValue() }.getOrNull()}")
                return null
            }
            toolsDir.walkTopDown().firstOrNull { it.name.equals("ffmpeg.exe", ignoreCase = true) }?.absolutePath
        }.onFailure { FileLogger.w(TAG, "解包捆绑 ffmpeg 异常", it) }.getOrNull()
    }

    val ffprobe: String? by lazy {
        exe?.let { exePath ->
            val probe = File(File(exePath).parentFile, "ffprobe.exe")
            if (probe.isFile) probe.absolutePath else null
        }
    }
}

/** ffprobe 读到的流信息。 */
data class VideoProbe(val width: Int, val height: Int, val fps: Double)

/** 跑 ffprobe 拿宽高与帧率；失败/缺 stream/比值异常返回 null。 */
fun probeVideo(file: File): VideoProbe? = runCatching {
    val probe = FfmpegLocator.ffprobe ?: return@runCatching null
    val process = ProcessBuilder(
        probe, "-v", "error", "-select_streams", "v:0",
        "-show_entries", "stream=width,height,r_frame_rate",
        "-of", "csv=p=0", file.absolutePath
    ).redirectErrorStream(false).start()
    val text = process.inputStream.bufferedReader().use { it.readText() }
    if (!process.waitFor(3, TimeUnit.SECONDS)) {
        process.destroyForcibly()
        return@runCatching null
    }
    if (process.exitValue() != 0) return@runCatching null
    val parts = text.trim().split(",").map { it.trim() }
    if (parts.size < 3) return@runCatching null
    val w = parts[0].toIntOrNull() ?: return@runCatching null
    val h = parts[1].toIntOrNull() ?: return@runCatching null
    // fps 形如 "60/1"、"24000/1001"，偶数情况下也可能是 "24"
    val fpsRaw = parts[2]
    val fps = fpsRaw.split("/").let { split ->
        if (split.size == 2) {
            val num = split[0].toDoubleOrNull() ?: return@runCatching null
            val den = split[1].toDoubleOrNull() ?: return@runCatching null
            if (den == 0.0) 0.0 else num / den
        } else fpsRaw.toDoubleOrNull() ?: 0.0
    }
    if (w <= 0 || h <= 0) null else VideoProbe(w, h, fps)
}.getOrNull()

/** FFmpeg 常驻子进程：-stream_loop -1 反复解码，按帧率喂 Compose。 */
class FfmpegVideoSource(
    private val file: File,
    private val maxWidth: Int = 640,
) : AutoCloseable {
    companion object {
        private const val TAG = "FfmpegVideoSource"
    }

    val frameIntervalMs: Long
    val width: Int
    val height: Int

    private var process: Process? = null
    private var input: InputStream? = null

    init {
        val probe = probeVideo(file)
            ?: throw IllegalStateException("ffprobe 无法解析视频: ${file.absolutePath}")
        val scale = minOf(1.0, maxWidth.toDouble() / probe.width)
        width = (probe.width * scale).toInt().let { it - it % 2 }.coerceAtLeast(2)
        height = (probe.height * scale).toInt().let { it - it % 2 }.coerceAtLeast(2)
        val targetFps = probe.fps.coerceIn(15.0, 30.0)
        frameIntervalMs = (1000.0 / targetFps).toLong().coerceIn(16L, 200L)
    }

    fun start() {
        val exe = FfmpegLocator.exe
            ?: throw IllegalStateException("ffmpeg 未找到（PATH 或 VANTACODE_FFMPEG）")
        val command = listOf(
            exe,
            "-hide_banner", "-loglevel", "error",
            "-stream_loop", "-1",   // 无限循环（管道内循环）
            "-an", "-sn", "-dn",
            "-i", file.absolutePath,
            "-vf", "scale=$width:$height",
            "-r", (1000.0 / frameIntervalMs).toInt().toString(),
            "-pix_fmt", "rgba",
            "-f", "rawvideo",
            "pipe:1",
        )
        val p = ProcessBuilder(command)
            .redirectErrorStream(false)
            .start()
        process = p
        input = p.inputStream
        FileLogger.i(TAG, "ffmpeg 启动: size=${width}x$height fps=${frameIntervalMs}ms file=${file.absolutePath}")
    }

    /** 阻塞读下一帧；进程退出/流结束返回 null。成功返回 Compose ImageBitmap。 */
    fun readFrame(): ImageBitmap? {
        val ins = input ?: return null
        val bytes = ByteArray(width * height * 4)
        var offset = 0
        while (offset < bytes.size) {
            val read = ins.read(bytes, offset, bytes.size - offset)
            if (read < 0) return null
            offset += read
        }
        return rawFrameToBitmap(bytes)
    }

    private fun rawFrameToBitmap(bytes: ByteArray): ImageBitmap {
        val bgra = ByteArray(bytes.size)
        var i = 0
        while (i < bytes.size) {
            bgra[i] = bytes[i + 2]     // B
            bgra[i + 1] = bytes[i + 1] // G
            bgra[i + 2] = bytes[i]     // R
            bgra[i + 3] = bytes[i + 3] // A
            i += 4
        }
        val info = ImageInfo(width, height, ColorType.BGRA_8888, ColorAlphaType.UNPREMUL)
        val bmp = Bitmap()
        bmp.installPixels(info, bgra, width * 4)
        return bmp.asComposeImageBitmap()
    }

    override fun close() {
        input?.close()
        process?.let { p ->
            p.destroy()
            if (!p.waitFor(2, TimeUnit.SECONDS)) p.destroyForcibly()
        }
        input = null
        process = null
    }
}
