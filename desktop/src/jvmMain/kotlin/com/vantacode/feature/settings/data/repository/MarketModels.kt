package com.vantacode.feature.settings.data.repository

import kotlinx.serialization.Serializable

/** 技能市场条目（与在线注册表 / GitHub 搜索结果共用）。 */
@Serializable
data class MarketEntry(
    val name: String,
    val displayName: String = "",
    val description: String = "",
    val author: String = "",
    val stars: Int = 0,
    val packageName: String = "",
    val tags: List<String> = emptyList(),
    val downloadUrl: String = "",
    val category: String = "skill",
    val version: String = "1.0.0"
)

/** 在线注册表；兼容 skills / plugins 两种字段名。 */
@Serializable
data class MarketRegistry(
    val version: Int = 1,
    val updatedAt: String = "",
    val skills: List<MarketEntry> = emptyList(),
    val plugins: List<MarketEntry> = emptyList()
) {
    val entries: List<MarketEntry> get() = skills.ifEmpty { plugins }
}

/** 安装失败的原因分类；UI 据此选择本地化文案。 */
enum class SkillInstallError {
    DOWNLOAD_FAILED,
    NO_SKILL_FOUND,
    INSTALL_FAILED
}

/** 从 ZIP 安装技能的结果。 */
sealed interface SkillInstallResult {
    data class Success(val skillNames: List<String>) : SkillInstallResult
    data class Failure(val error: SkillInstallError, val detail: String? = null) : SkillInstallResult
}
