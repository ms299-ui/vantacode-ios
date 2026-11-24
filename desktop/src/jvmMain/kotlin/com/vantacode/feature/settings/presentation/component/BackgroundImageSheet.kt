package com.vantacode.feature.settings.presentation.component

import java.awt.FileDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import com.vantacode.core.ui.AdaptiveModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import androidx.compose.ui.layout.ContentScale
import com.vantacode.core.platform.LocalDesktopContext
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Spacing
import com.vantacode.feature.settings.data.repository.BackgroundSettingsRepository
import com.vantacode.core.media.Mp4FrameDecoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.vantacode.core.ui.Strings

/**
 * 按目标尺寸解码背景图（等比例缩放到 maxWidth×maxHeight 内），失败返回 null。
 * 全局背景层与设置弹窗缩略图共用。
 */
internal fun decodeBackgroundBitmap(path: String, maxWidth: Int, maxHeight: Int): ImageBitmap? =
    runCatching {
        val src = ImageIO.read(File(path)) ?: return@runCatching null
        val w = src.width
        val h = src.height
        val scale = minOf(1f, minOf(maxWidth.toFloat() / w, maxHeight.toFloat() / h))
        val targetW = (w * scale).toInt().coerceAtLeast(1)
        val targetH = (h * scale).toInt().coerceAtLeast(1)
        val scaled = if (scale < 1f) {
            java.awt.image.BufferedImage(targetW, targetH, java.awt.image.BufferedImage.TYPE_INT_ARGB).apply {
                val g = createGraphics()
                g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR)
                g.drawImage(src, 0, 0, targetW, targetH, null)
                g.dispose()
            }
        } else src
        val bos = ByteArrayOutputStream()
        ImageIO.write(scaled, "png", bos)
        bos.toByteArray().decodeToImageBitmap()
    }.getOrNull()

/** MP4 预览首帧：黑底占位图，避免菜单弹窗里跑解码循环。 */
private fun decodeVideoFirstFrame(path: String, maxEdge: Int): ImageBitmap? =
    runCatching {
        val decoder = Mp4FrameDecoder(File(path))
        decoder.use {
            val frame = it.nextFrame() ?: return@runCatching null
            val scaled = it.resizeToFit(frame, maxEdge)
            val bos = java.io.ByteArrayOutputStream()
            javax.imageio.ImageIO.write(scaled.let { img ->
                if (img.type == java.awt.image.BufferedImage.TYPE_INT_ARGB) img else java.awt.image.BufferedImage(img.width, img.height, java.awt.image.BufferedImage.TYPE_INT_ARGB).apply {
                    createGraphics().apply { drawImage(img, 0, 0, null); dispose() }
                }
            }, "png", bos)
            bos.toByteArray().decodeToImageBitmap()
        }
    }.getOrNull()

/**
 * 自定义背景图设置弹窗：缩略图预览 + 透明度滑块 + 选图/移除。
 *
 * @param imagePath 当前背景图路径，null 表示未设置。
 * @param backgroundKind 背景媒体类型（IMAGE/GIF/MP4），决定预览方式。
 * @param alpha 当前透明度（0.05~1.0）。
 * @param onPickImage 用户选定图片后回调。
 * @param onAlphaChange 透明度变化回调（写 DataStore，全局背景随之变化；落盘节流在 ViewModel 侧）。
 * @param onRemove 移除背景回调。
 * @param onDismiss 关闭弹窗。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BackgroundImageSheet(
    imagePath: String?,
    backgroundKind: com.vantacode.feature.settings.data.repository.BackgroundKind,
    alpha: Float,
    onPickImage: (File) -> Unit,
    onAlphaChange: (Float) -> Unit,
    onRemove: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    fun pickImage() {
        // java.awt FileDialog must run on the AWT thread; Compose callbacks already run on the AWT EDT
        val dialog = java.awt.FileDialog(null as java.awt.Frame?, Strings.settings_background_image, java.awt.FileDialog.LOAD)
        dialog.file = "*.png;*.jpg;*.jpeg;*.webp;*.bmp;*.gif;*.mp4;*.webm;*.mov"
        dialog.isVisible = true
        val dir = dialog.directory
        val file = dialog.file
        if (dir != null && file != null && dir.isNotBlank() && file.isNotBlank()) {
            onPickImage(File(dir, file))
        }
    }

    // 缩略图预览：按屏幕宽度采样，避免大图全量解码；GIF 取首帧，MP4 取解码首帧
    val previewBitmap by produceState<ImageBitmap?>(initialValue = null, imagePath, backgroundKind) {
        value = imagePath?.let { path ->
            withContext(Dispatchers.IO) {
                when (backgroundKind) {
                    com.vantacode.feature.settings.data.repository.BackgroundKind.MP4 -> decodeVideoFirstFrame(path, 1024)
                    else -> decodeBackgroundBitmap(path, 1024, 1024)
                }
            }
        }
    }

    // 滑块位置由本地状态驱动：直接绑外部 alpha 会让每次拖动都走「写 DataStore → 回读 → 整个设置页重组」
    // 的往返，滑块因此跟不上手指。弹窗每次打开都重建，初值取当前设置即可。
    var sliderPercent by remember { mutableFloatStateOf(BackgroundSettingsRepository.alphaToSlider(alpha)) }

    AdaptiveModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg)
                .padding(bottom = Spacing.xl)
        ) {
            Text(
                text = Strings.settings_background_image,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Spacing.md)
            )

            if (previewBitmap != null) {
                Image(
                    bitmap = previewBitmap!!,
                    contentDescription = Strings.settings_background_image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(Spacing.lg))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.settings_background_image_alpha,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${sliderPercent.toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Slider(
                value = sliderPercent,
                onValueChange = { percent ->
                    sliderPercent = percent
                    onAlphaChange(BackgroundSettingsRepository.sliderToAlpha(percent))
                },
                valueRange = 0f..100f
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                Button(
                    onClick = {
                        pickImage()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Strings.settings_background_image_pick)
                }
                OutlinedButton(
                    onClick = {
                        onRemove()
                        onDismiss()
                    },
                    enabled = imagePath != null,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Strings.settings_background_image_remove)
                }
            }
        }
    }
}
