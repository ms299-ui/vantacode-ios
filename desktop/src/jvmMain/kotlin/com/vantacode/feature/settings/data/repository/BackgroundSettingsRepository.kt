package com.vantacode.feature.settings.data.repository
import com.vantacode.core.platform.DesktopAssets
import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.util.FileLogger

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vantacode.core.datastore.preferencesDataStore
import com.vantacode.core.datastore.preferencesCorruptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

private val DesktopContext.backgroundDataStore by preferencesDataStore(
    name = "background_prefs",
    corruptionHandler = preferencesCorruptionHandler
)

/** 背景媒体类型：静态图 / GIF 动图 / MP4 视频。 */
enum class BackgroundKind { IMAGE, GIF, MP4 }

/**
 * 全局自定义背景：图片/GIF/MP4 文件拷贝到应用私有目录持久化（不依赖 content URI 授权），
 * 类型、透明度与路径存 DataStore。路径为 null 表示未设置背景。
 *
 * 首次启动且用户从未设置/清除过背景时，解压内置默认视频背景（与 Android 版行为对齐）。
 */
@Singleton
class BackgroundSettingsRepository @Inject constructor(
    private val context: DesktopContext
) {
    companion object {
        const val DEFAULT_ALPHA = 0.15f
        /** 顶层水印模式下透明度上限：超过会盖住文字影响可读性。 */
        const val MAX_ALPHA = 0.2f
        /** 最小透明度：0 表示完全透明（背景不可见）。 */
        const val MIN_ALPHA = 0f
        private const val TAG = "BackgroundSettingsRepository"
        /** 内置默认背景（随安装包分发）。 */
        const val DEFAULT_VIDEO_ASSET = "backgrounds/default_bg.mp4"
        private const val DEFAULT_VIDEO_FILE = "default_bg.mp4"
        private val IMAGE_PATH_KEY = stringPreferencesKey("background_image_path")
        private val IMAGE_KIND_KEY = stringPreferencesKey("background_image_kind")
        private val ALPHA_KEY = floatPreferencesKey("background_alpha")
        private val USER_CLEARED_KEY = booleanPreferencesKey("background_user_cleared")

        /** 按扩展名分类背景媒体类型。 */
        fun kindOf(fileName: String): BackgroundKind {
            val ext = fileName.substringAfterLast('.', "").lowercase()
            return when (ext) {
                "gif" -> BackgroundKind.GIF
                "mp4", "webm", "mov", "m4v", "mkv", "avi" -> BackgroundKind.MP4
                else -> BackgroundKind.IMAGE
            }
        }

        /** 把 UI 百分比（0~100）线性映射为实际透明度（0~MAX_ALPHA）。 */
        fun sliderToAlpha(percent: Float): Float =
            MAX_ALPHA * (percent / 100f).coerceIn(0f, 1f)

        /** 把实际透明度反映射为 UI 百分比（0~100）。 */
        fun alphaToSlider(alpha: Float): Float =
            (alpha / MAX_ALPHA).coerceIn(0f, 1f) * 100f
    }

    /** 当前背景文件绝对路径；null 表示未设置。 */
    val imagePathFlow: Flow<String?> = context.backgroundDataStore.data.map { it[IMAGE_PATH_KEY] }

    /** 背景媒体类型（默认静态图）。 */
    val backgroundKindFlow: Flow<BackgroundKind> = context.backgroundDataStore.data.map { prefs ->
        when (prefs[IMAGE_KIND_KEY]?.lowercase()) {
            "gif" -> BackgroundKind.GIF
            "mp4" -> BackgroundKind.MP4
            else -> BackgroundKind.IMAGE
        }
    }

    /** 背景不透明度（0.05~0.2，顶层水印保证文字可读）。 */
    val alphaFlow: Flow<Float> = context.backgroundDataStore.data.map {
        (it[ALPHA_KEY] ?: DEFAULT_ALPHA).coerceIn(MIN_ALPHA, MAX_ALPHA)
    }

    /**
     * 首次启动且用户未设置过背景时，解压内置默认视频背景。
     * 幂等：已存在解压文件或已设置/清除过背景都直接返回。
     */
    suspend fun ensureDefaultBackground() {
        withContext(Dispatchers.IO) {
            runCatching {
                val prefs = context.backgroundDataStore.data.first()
                if (prefs[IMAGE_PATH_KEY] != null || prefs[USER_CLEARED_KEY] == true) return@runCatching
                val targetDir = File(context.getFilesDir(), "backgrounds")
                targetDir.mkdirs()
                val target = File(targetDir, DEFAULT_VIDEO_FILE)
                if (!target.exists() || target.length() == 0L) {
                    DesktopAssets.open(DEFAULT_VIDEO_ASSET).use { input ->
                        target.outputStream().use { output -> input.copyTo(output) }
                    }
                }
                if (target.length() > 0) {
                    context.backgroundDataStore.edit {
                        it[IMAGE_PATH_KEY] = target.absolutePath
                        it[IMAGE_KIND_KEY] = BackgroundKind.MP4.name
                    }
                }
            }.onFailure {
                FileLogger.w(TAG, "解压默认视频背景失败，跳过默认背景", it)
            }
        }
    }

    /**
     * 选择新背景：把 [source] 拷贝到私有目录 backgrounds/ 后替换旧背景。
     * 先写新文件成功再更新 DataStore，失败时旧背景保持不变。
     * 类型按扩展名分类：gif→GIF，视频扩展名→MP4，其余→IMAGE。
     */
    suspend fun setBackgroundImage(source: File) {
        val kind = kindOf(source.name)
        val extension = source.extension.lowercase().ifBlank {
            when (kind) {
                BackgroundKind.GIF -> "gif"
                BackgroundKind.MP4 -> "mp4"
                BackgroundKind.IMAGE -> "jpg"
            }
        }
        val target = File(context.getFilesDir(), "backgrounds/bg_${System.currentTimeMillis()}.$extension")
        target.parentFile?.mkdirs()
        val copied = withContext(Dispatchers.IO) {
            runCatching {
                source.inputStream().use { input ->
                    target.outputStream().use { output -> input.copyTo(output) }
                }
                target.length() > 0
            }.getOrDefault(false)
        }
        if (!copied) return
        val prefs = context.backgroundDataStore.data.first()
        val oldPath = prefs[IMAGE_PATH_KEY]
        context.backgroundDataStore.edit {
            it[IMAGE_PATH_KEY] = target.absolutePath
            it[IMAGE_KIND_KEY] = kind.name
            it.remove(USER_CLEARED_KEY)
        }
        oldPath?.let { runCatching { File(it).delete() } }
    }

    /** 移除背景：删文件并清路径；记录「用户已清除」，避免默认背景自动回来。 */
    suspend fun clearBackground() {
        val oldPath = context.backgroundDataStore.data.first()[IMAGE_PATH_KEY]
        context.backgroundDataStore.edit {
            it.remove(IMAGE_PATH_KEY)
            it.remove(IMAGE_KIND_KEY)
            it[USER_CLEARED_KEY] = true
        }
        oldPath?.let { runCatching { File(it).delete() } }
    }

    suspend fun setBackgroundAlpha(alpha: Float) {
        context.backgroundDataStore.edit {
            it[ALPHA_KEY] = alpha.coerceIn(MIN_ALPHA, MAX_ALPHA)
        }
    }

}
