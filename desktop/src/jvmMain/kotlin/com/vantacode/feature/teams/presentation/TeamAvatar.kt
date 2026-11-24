/*
 * VantaCode Desktop — 团队成员/队长头像：表情字符或图片文件。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.teams.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.absoluteValue

/** 头像背景色板（按名字 hash 稳定取色）。 */
private val AVATAR_PALETTE = listOf(
    Color(0xFFD32F2F), Color(0xFFC2185B), Color(0xFF7B1FA2), Color(0xFF512DA8),
    Color(0xFF303F9F), Color(0xFF1976D2), Color(0xFF00796B), Color(0xFF388E3C),
    Color(0xFF689F38), Color(0xFFF57F17), Color(0xFFE64A19), Color(0xFF5D4037),
    Color(0xFF455A64), Color(0xFF00838F),
)

private fun colorFor(name: String): Color = AVATAR_PALETTE[name.hashCode().absoluteValue % AVATAR_PALETTE.size]

/** 头像展示：优先当图片文件解码，否则渲染单字符/表情。 */
@Composable
fun TeamAvatar(
    avatar: String,
    name: String,
    size: Dp = 40.dp,
    modifier: Modifier = Modifier,
) {
    val trimmed = avatar.trim()
    if (trimmed.isNotBlank()) {
        val imagePath = trimmed.takeIf { File(it).isFile }
        if (imagePath != null) {
            var bitmap by remember(imagePath) { mutableStateOf<ImageBitmap?>(null) }
            LaunchedEffect(imagePath) {
                bitmap = withContext(Dispatchers.IO) {
                    runCatching {
                        val src = ImageIO.read(File(imagePath)) ?: return@runCatching null
                        val maxEdge = 192
                        val scale = minOf(1f, maxEdge.toFloat() / maxOf(src.width, src.height))
                        val w = (src.width * scale).toInt().coerceAtLeast(1)
                        val h = (src.height * scale).toInt().coerceAtLeast(1)
                        val scaled = if (scale < 1f) {
                            java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB).apply {
                                val g = createGraphics()
                                g.setRenderingHint(
                                    java.awt.RenderingHints.KEY_INTERPOLATION,
                                    java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR
                                )
                                g.drawImage(src, 0, 0, w, h, null)
                                g.dispose()
                            }
                        } else src
                        ByteArrayOutputStream().use { bos ->
                            ImageIO.write(scaled, "png", bos)
                            bos.toByteArray().decodeToImageBitmap()
                        }
                    }.getOrNull()
                }
            }
            val bmp = bitmap
            if (bmp != null) {
                Image(
                    bitmap = bmp,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(size)
                        .clip(CircleShape)
                )
                return
            }
        }
    }

    // 表情字符（如 🐱）：最多取 2 个码位，避免长串撑爆圆圈
    val glyph = trimmed.takeIf { it.isNotBlank() } ?: name.trim().take(1).uppercase()
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(colorFor(name)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = glyph.take(2),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
        )
    }
}
