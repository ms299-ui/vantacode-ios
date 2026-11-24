package com.vantacode.feature.settings.data.repository
import com.vantacode.core.platform.DesktopContext

import com.vantacode.core.datastore.preferencesDataStore
import com.vantacode.core.datastore.preferencesCorruptionHandler
import javax.inject.Inject
import javax.inject.Singleton

private val DesktopContext.compactionModelDataStore by preferencesDataStore(
    name = "compaction_model_prefs",
    corruptionHandler = preferencesCorruptionHandler
)

/**
 * 持久化「压缩专用模型」选择（providerId + model 两字符串）。
 *
 * 上下文压缩默认跟随当前聊天模型；当用户在此指定一个专用模型后，
 * 压缩轮会临时切换到该专用模型发送摘要请求，发完恢复聊天模型。
 * providerId 为空（未配置）即视为「跟随当前聊天模型」。
 */
@Singleton
class CompactionModelSettingsRepository @Inject constructor(
    context: DesktopContext
) : ModelSelectionSettingsRepository(
    context.compactionModelDataStore, "compaction_provider_id", "compaction_model"
) {

    /** 写入压缩专用模型（设空字符串即等同 [clear]）。 */
    suspend fun setCompactionModel(providerId: String, model: String) = setSelection(providerId, model)

    /** 清空配置——回退到「跟随当前聊天模型」。 */
    suspend fun clear() = clearSelection()

    /** 读取一次当前压缩专用 providerId（冷读用）。 */
    suspend fun getCompactionProviderId(): String = readProviderId()

    /** 读取一次当前压缩专用 model（冷读用）。 */
    suspend fun getCompactionModel(): String = readModel()
}
