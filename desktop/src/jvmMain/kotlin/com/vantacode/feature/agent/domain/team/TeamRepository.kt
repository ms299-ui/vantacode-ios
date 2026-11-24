/*
 * VantaCode Desktop — 团队定义仓库：`~/.aicode/teams/<name>.md` 为唯一事实来源；
 * 改名时群聊记录随团队迁移（TeamChatDao.renameTeam）。
 * 与 Android 版 TeamRepository 行为一致。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.team

import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.data.local.dao.TeamChatDao
import com.vantacode.feature.workspace.domain.FileAccessProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamRepository @Inject constructor(
    private val fileAccess: FileAccessProvider,
    private val teamChatDao: TeamChatDao,
) {
    companion object {
        private const val TAG = "TeamRepository"
        private const val TEAMS_ROOT = "~/.aicode/teams"
        private const val FS_SEP = "/"
        private val ILLEGAL_NAME_CHARS = setOf('/', '\\', ':', '*', '?', '"', '<', '>', '|')

        /** 团队名校验：非空、≤40 字符、不含路径分隔符/通配符/控制字符、不是 . 或 .. */
        fun isValidName(name: String): Boolean {
            val trimmed = name.trim()
            if (trimmed.isBlank() || trimmed.length > TeamDefinition.MAX_NAME_LENGTH) return false
            if (trimmed == "." || trimmed == "..") return false
            return trimmed.none { it in ILLEGAL_NAME_CHARS || it.isISOControl() }
        }

        private fun teamFilePath(name: String): String =
            TEAMS_ROOT.trimEnd('/') + FS_SEP + name.trim() + ".md"
    }

    /** 每次保存/删除都会自增，供 UI 观察列表变化。 */
    private val _teamsVersion = MutableStateFlow(0L)
    val teamsVersion: StateFlow<Long> = _teamsVersion.asStateFlow()

    fun listAll(): List<TeamDefinition> = runCatching {
        if (!fileAccess.isDirectory(TEAMS_ROOT)) return@runCatching emptyList()
        fileAccess.listFiles(TEAMS_ROOT)
            .filter { !it.isDirectory && it.name.endsWith(".md", ignoreCase = true) }
            .mapNotNull { entry ->
                runCatching { TeamDefinitionParser.parse(fileAccess, TEAMS_ROOT.trimEnd('/') + FS_SEP + entry.name) }
                    .onFailure { FileLogger.w(TAG, "解析团队定义失败，已跳过: ${entry.name}", it) }
                    .getOrNull()
            }
            .sortedBy { it.name.lowercase() }
    }.getOrElse {
        FileLogger.w(TAG, "扫描团队定义目录失败: $TEAMS_ROOT", it)
        emptyList()
    }

    fun find(name: String): TeamDefinition? =
        listAll().firstOrNull { it.name.equals(name, ignoreCase = true) }

    fun delete(name: String): Boolean {
        val team = find(name) ?: return false
        val path = team.filePath ?: return false
        if (!fileAccess.isFile(path)) return false
        return runCatching {
            fileAccess.delete(path)
            true
        }.onFailure {
            FileLogger.w(TAG, "删除团队定义失败: $path", it)
        }.getOrDefault(false).also { if (it) bumpVersion() }
    }

    /**
     * 保存表单为团队定义（新建或按 [originalName] 改名更新）。
     * 成功返回 null；失败返回对应业务错误。
     */
    suspend fun save(form: TeamForm, originalName: String?): TeamSaveError? {
        val trimmedName = form.name.trim()
        if (!isValidName(trimmedName)) return TeamSaveError.INVALID_NAME

        val members = form.members
            .map { it.copy(name = it.name.trim(), agent = it.agent.trim()) }
            .filter { it.name.isNotBlank() && (it.usesAgent || it.model.isNotBlank()) }
        // 按名称大小写不敏感去重
        val deduped = members.distinctBy { it.name.lowercase() }
        if (deduped.isEmpty()) return TeamSaveError.NO_MEMBERS
        if (deduped.size > TeamDefinition.MAX_MEMBERS) return TeamSaveError.MEMBER_LIMIT

        val renaming = originalName != null && !originalName.equals(trimmedName, ignoreCase = true)
        if (!renaming) {
            // 新建时防重名；改名时允许保留原名
            val conflict = listAll().any { it.name.equals(trimmedName, ignoreCase = true) }
            val isKeepingOriginal = originalName != null && originalName.equals(trimmedName, ignoreCase = true)
            if (conflict && !isKeepingOriginal) return TeamSaveError.NAME_CONFLICT
        }

        val oldFilePath = originalName?.let { old -> find(old)?.filePath }
        val targetPath = teamFilePath(trimmedName)
        val serialized = TeamDefinitionParser.serialize(
            name = trimmedName,
            description = form.description.take(TeamDefinition.MAX_DESCRIPTION_LENGTH),
            ownerName = form.ownerName.trim().ifBlank { TeamDefinition.DEFAULT_OWNER_NAME },
            ownerAvatar = form.ownerAvatar.trim(),
            members = deduped,
            strategy = form.strategy,
        )

        return runCatching {
            runCatching { fileAccess.mkdirs(TEAMS_ROOT) }
            fileAccess.writeFile(targetPath, serialized, overwrite = true)
            if (renaming && oldFilePath != null && !oldFilePath.equals(targetPath, ignoreCase = true)) {
                runCatching { fileAccess.delete(oldFilePath) }.onFailure {
                    FileLogger.w(TAG, "改名后删除旧团队定义失败: $oldFilePath", it)
                }
            }
            if (renaming && originalName != null) {
                runCatching { teamChatDao.renameTeam(originalName, trimmedName) }.onFailure {
                    FileLogger.w(TAG, "迁移群聊记录到新团队名失败: $originalName -> $trimmedName", it)
                }
            }
            bumpVersion()
            null
        }.onFailure {
            FileLogger.e(TAG, "保存团队定义失败: $trimmedName", it)
        }.getOrElse { TeamSaveError.INVALID_NAME }
    }

    private fun bumpVersion() {
        _teamsVersion.value += 1
    }
}
