package com.vantacode.feature.plugin.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.vantacode.core.datastore.preferencesDataStore
import com.vantacode.core.datastore.preferencesCorruptionHandler
import com.vantacode.core.platform.DesktopContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val DesktopContext.pluginDevDataStore by preferencesDataStore(
    name = "plugin_dev_settings",
    corruptionHandler = preferencesCorruptionHandler
)

/** 开发者侧载开关；默认关闭，普通本地 zip 导入不受影响。 */
@Singleton
class PluginDevSettingsRepository @Inject constructor(
    private val context: DesktopContext
) {
    val enabled: Flow<Boolean> = context.pluginDevDataStore.data.map { it[KEY] ?: false }

    suspend fun setEnabled(enabled: Boolean) {
        context.pluginDevDataStore.edit { it[KEY] = enabled }
    }

    private companion object {
        val KEY = booleanPreferencesKey("developer_sideload")
    }
}
