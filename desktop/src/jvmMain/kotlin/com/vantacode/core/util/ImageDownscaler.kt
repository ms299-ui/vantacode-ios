/*
 * VantaCode Desktop — image downscaling for model-bound attachments.
 * Android Bitmap/ExifInterface replaced by javax.imageio + manual EXIF orientation parsing.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.io.InputStream
import java.util.Base64
import javax.imageio.ImageIO

/**
 * 发给模型的图片先缩小再编码。
 *
 * 模型侧会把超过长边上限（Anthropic ≈ 1568px）的图缩小后再看，原图多传的字节不带来任何信息，
 * 却会在每一轮历史回放里重复占用请求体。只处理发给模型的副本，工作区里的原文件不动。
 */
object ImageDownscaler {
    const val MAX_EDGE = 1568
    private const val SMALL_BYTES = 300 * 1024
    private const val JPEG_QUALITY = 85

    class Result(val mimeType: String, val bytes: ByteArray)

    /** 长边不超过 [maxEdge] 的等比目标尺寸；已在范围内返回原尺寸。 */
    internal fun fitWithin(width: Int, height: Int, maxEdge: Int = MAX_EDGE): Pair<Int, Int> {
        val longest = maxOf(width, height)
        if (longest <= maxEdge) return width to height
        val scale = maxEdge.toDouble() / longest
        return (width * scale).toInt().coerceAtLeast(1) to (height * scale).toInt().coerceAtLeast(1)
    }

    /** 解码不了、动图、已经够小或重编码后更大时，原样返回。 */
    fun downscale(bytes: ByteArray, mimeType: String): Result {
        val original = Result(mimeType, bytes)
        if (mimeType.equals("image/gif", ignoreCase = true)) return original
        return try {
            val src = ImageIO.read(ByteArrayInputStream(bytes)) ?: return original
            val width = src.width
            val height = src.height
            if (width <= 0 || height <= 0) return original
            if (maxOf(width, height) <= MAX_EDGE && bytes.size <= SMALL_BYTES) return original

            val (targetW, targetH) = fitWithin(width, height)
            val scaled = if (targetW == width && targetH == height) src else scaleImage(src, targetW, targetH)
            val oriented = applyOrientation(scaled, readOrientation(bytes))
            val orientedW = oriented.width
            val orientedH = oriented.height
            val finalImage = if (maxOf(orientedW, orientedH) > MAX_EDGE) {
                val (w, h) = fitWithin(orientedW, orientedH)
                scaleImage(oriented, w, h)
            } else {
                oriented
            }

            val keepAlpha = hasAlpha(finalImage) && !mimeType.equals("image/jpeg", ignoreCase = true)
            val format = if (keepAlpha) "png" else "jpeg"
            val out = ByteArrayOutputStream()
            if (format == "jpeg") {
                // 统一压成不透明白底，避免透明通道在 JPEG 里变黑
                val opaque = BufferedImage(finalImage.width, finalImage.height, BufferedImage.TYPE_INT_RGB)
                val g = opaque.createGraphics()
                g.color = java.awt.Color.WHITE
                g.fillRect(0, 0, opaque.width, opaque.height)
                g.drawImage(finalImage, 0, 0, null)
                g.dispose()
                val jpegParams = ImageIO.getImageWritersByFormatName("jpeg").next()
                val param = jpegParams.defaultWriteParam
                param.compressionMode = javax.imageio.ImageWriteParam.MODE_EXPLICIT
                param.compressionQuality = JPEG_QUALITY / 100f
                ImageIO.createImageOutputStream(out).use { ios ->
                    jpegParams.output = ios
                    jpegParams.write(null, javax.imageio.IIOImage(opaque, null, null), param)
                }
            } else {
                ImageIO.write(finalImage, format, out)
            }
            val encoded = out.toByteArray()
            if (encoded.size >= bytes.size) original
            else Result(if (keepAlpha) "image/png" else "image/jpeg", encoded)
        } catch (_: OutOfMemoryError) {
            original
        } catch (_: Exception) {
            original
        }
    }

    private fun scaleImage(src: BufferedImage, w: Int, h: Int): BufferedImage {
        val dst = BufferedImage(w, h, if (src.colorModel.hasAlpha()) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
        val g = dst.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.drawImage(src, 0, 0, w, h, null)
        g.dispose()
        return dst
    }

    private fun hasAlpha(img: BufferedImage): Boolean = img.colorModel.hasAlpha()

    /**
     * 从 JPEG/PNG 的 EXIF 里读取朝向（1-8）。找不到或解析失败按正常返回。
     * 手写解析，避免引入额外依赖：扫 APP1 段里的 Exif\0\0 TIFF 头。
     */
    private fun readOrientation(bytes: ByteArray): Int {
        return try {
            var i = 2 // 跳过 SOI 0xFFD8
            while (i + 4 < bytes.size) {
                val marker = (bytes[i].toInt() and 0xFF shl 8) or (bytes[i + 1].toInt() and 0xFF)
                if (marker == 0xFFE1) { // APP1
                    val segmentLength = ((bytes[i + 2].toInt() and 0xFF) shl 8) or (bytes[i + 3].toInt() and 0xFF)
                    if (segmentLength >= 8 && bytes.getString(i + 4, 6).startsWith("Exif")) {
                        return parseTiffOrientation(bytes, i + 4 + 6)
                    }
                    i += 2 + segmentLength
                    continue
                }
                if ((marker and 0xFF00) != 0xFF00) break // 不是 marker，放弃
                if (marker == 0xFFD9 || marker == 0xFFDA) break // EOI / SOS
                val len = ((bytes[i + 2].toInt() and 0xFF) shl 8) or (bytes[i + 3].toInt() and 0xFF)
                if (len < 2) break
                i += 2 + len
            }
            1
        } catch (_: Exception) {
            1
        }
    }

    private fun ByteArray.getString(offset: Int, length: Int): String =
        String(this, offset, length.coerceAtMost(size - offset), Charsets.US_ASCII)

    private fun parseTiffOrientation(bytes: ByteArray, tiffStart: Int): Int {
        val littleEndian = bytes[tiffStart] == 0x49.toByte() // 'I'
        fun u16(off: Int): Int {
            val a = bytes[off].toInt() and 0xFF
            val b = bytes[off + 1].toInt() and 0xFF
            return if (littleEndian) (b shl 8) or a else (a shl 8) or b
        }
        fun u32(off: Int): Long {
            val a = bytes[off].toLong() and 0xFF
            val b = bytes[off + 1].toLong() and 0xFF
            val c = bytes[off + 2].toLong() and 0xFF
            val d = bytes[off + 3].toLong() and 0xFF
            return if (littleEndian) (d shl 24) or (c shl 16) or (b shl 8) or a else (a shl 24) or (b shl 16) or (c shl 8) or d
        }
        val ifd0 = tiffStart + u32(tiffStart + 4).toInt()
        val entries = u16(ifd0)
        for (n in 0 until entries) {
            val entry = ifd0 + 2 + n * 12
            if (u16(entry) == 0x0112) {
                return u16(entry + 8)
            }
        }
        return 1
    }

    private fun applyOrientation(bitmap: BufferedImage, orientation: Int): BufferedImage {
        val transform: AffineTransform = when (orientation) {
            3 -> AffineTransform.getRotateInstance(Math.PI, bitmap.width / 2.0, bitmap.height / 2.0)
            6 -> AffineTransform.getRotateInstance(Math.PI / 2, bitmap.width / 2.0, bitmap.height / 2.0)
            8 -> AffineTransform.getRotateInstance(-Math.PI / 2, bitmap.width / 2.0, bitmap.height / 2.0)
            2 -> AffineTransform(-1.0, 0.0, 0.0, 1.0, bitmap.width.toDouble(), 0.0)
            4 -> AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, bitmap.height.toDouble())
            5 -> AffineTransform(0.0, 1.0, 1.0, 0.0, 0.0, 0.0)
            7 -> AffineTransform(0.0, -1.0, -1.0, 0.0, bitmap.width.toDouble(), bitmap.height.toDouble())
            else -> return bitmap
        }
        // 旋转 90/270 时宽高互换
        val rotate90 = orientation == 6 || orientation == 8
        val outW = if (rotate90) bitmap.height else bitmap.width
        val outH = if (rotate90) bitmap.width else bitmap.height
        val dst = BufferedImage(outW, outH, bitmap.type)
        val g = dst.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        when (orientation) {
            3 -> {
                g.translate(bitmap.width.toDouble(), bitmap.height.toDouble())
                g.rotate(Math.PI)
                g.drawImage(bitmap, 0, 0, null)
            }
            6 -> {
                g.translate(bitmap.height.toDouble(), 0.0)
                g.rotate(Math.PI / 2)
                g.drawImage(bitmap, 0, 0, null)
            }
            8 -> {
                g.translate(0.0, bitmap.width.toDouble())
                g.rotate(-Math.PI / 2)
                g.drawImage(bitmap, 0, 0, null)
            }
            2 -> {
                g.translate(bitmap.width.toDouble(), 0.0)
                g.scale(-1.0, 1.0)
                g.drawImage(bitmap, 0, 0, null)
            }
            4 -> {
                g.translate(0.0, bitmap.height.toDouble())
                g.scale(1.0, -1.0)
                g.drawImage(bitmap, 0, 0, null)
            }
            5 -> {
                g.rotate(Math.PI / 4)
                g.shear(-1.0, 0.0)
                g.drawImage(bitmap, 0, 0, null)
            }
            7 -> {
                g.translate(bitmap.width.toDouble(), bitmap.height.toDouble())
                g.rotate(3 * Math.PI / 4)
                g.shear(-1.0, 0.0)
                g.drawImage(bitmap, 0, 0, null)
            }
            else -> g.drawImage(bitmap, 0, 0, null)
        }
        g.dispose()
        return dst
    }

    /** 缩小后的 base64 与实际 mime。 */
    fun downscaleToBase64(bytes: ByteArray, mimeType: String): Pair<String, String> {
        val result = downscale(bytes, mimeType)
        return result.mimeType to Base64.getEncoder().encodeToString(result.bytes)
    }
}
