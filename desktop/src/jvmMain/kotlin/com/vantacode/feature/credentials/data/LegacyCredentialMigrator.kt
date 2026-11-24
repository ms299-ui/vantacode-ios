package com.vantacode.feature.credentials.data

import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.data.local.database.AgentDatabase
import com.vantacode.feature.credentials.data.repository.FileCredentialRepository
import com.vantacode.feature.credentials.domain.model.GitCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 一次性把旧 Room `git_credentials` 表迁移到凭据文件，然后删表。
 *
 * 背景：凭证真源从 Room 迁到 `git-credentials` 文件（每主机一条）。Room 实体从 [AgentDatabase] 移除后
 * 旧表仍残留在数据库文件里（Room 不会自动删未管理表），由本类在 App 启动时读取 → 写入文件 → DROP。
 * 表不存在或已迁移过时直接跳过（幂等）。
 */
@Singleton
class LegacyCredentialMigrator @Inject constructor(
    private val database: AgentDatabase,
    private val fileCredentialRepository: FileCredentialRepository
) {
    private companion object {
        const val TAG = "LegacyCredentialMigrator"
        const val TABLE = "git_credentials"
    }

    /** 旧版凭据迁移：桌面端没有该 Android 表，直接视为已完成。 */
    suspend fun migrateIfNeeded() {
        // no-op
    }
}
