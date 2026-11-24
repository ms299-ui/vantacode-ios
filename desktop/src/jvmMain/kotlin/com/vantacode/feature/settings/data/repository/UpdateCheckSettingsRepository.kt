package com.vantacode.feature.settings.data.repository

import com.vantacode.core.platform.DesktopContext
import com.vantacode.feature.settings.data.remote.UpdateChannel
import com.vantacode.feature.settings.data.remote.UpdateCheckResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 更新检查的设置（自动检查开关 + 更新通道）与入口。
 * 桌面端不在应用内安装，checkForUpdate 只拉取信息，由界面展示并打开 Release 页。
 */
@Singleton
class UpdateCheckSettingsRepository @Inject constructor(
    private val context: DesktopContext
) {
    private val prefs = context.getSharedPreferences("update_prefs", DesktopContext.MODE_PRIVATE)

    private val _autoCheckEnabled = MutableStateFlow(prefs.getBoolean(KEY_AUTO_CHECK, true))
    val autoCheckEnabled: StateFlow<Boolean> = _autoCheckEnabled.asStateFlow()

    private val _channel = MutableStateFlow(
        runCatching {
            UpdateChannel.valueOf(prefs.getString(KEY_CHANNEL, null) ?: UpdateChannel.STABLE.name)
        }.getOrDefault(UpdateChannel.STABLE)
    )
    val channel: StateFlow<UpdateChannel> = _channel.asStateFlow()

    fun setAutoCheckEnabled(enabled: Boolean) {
        _autoCheckEnabled.value = enabled
        prefs.edit { putBoolean(KEY_AUTO_CHECK, enabled) }
    }

    fun setChannel(channel: UpdateChannel) {
        _channel.value = channel
        prefs.edit { putString(KEY_CHANNEL, channel.name) }
    }

    suspend fun checkForUpdate(currentVersion: String): UpdateCheckResult =
        com.vantacode.feature.settings.data.remote.checkForUpdate(currentVersion, channel.value)

    private companion object {
        const val KEY_AUTO_CHECK = "auto_check_enabled"
        const val KEY_CHANNEL = "update_channel"
    }
}
