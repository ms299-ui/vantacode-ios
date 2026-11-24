/*
 * VantaCode Desktop — Teams 页面共用的 ViewModel：团队列表 CRUD + 群聊执行。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.teams.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.data.local.dao.TeamChatDao
import com.vantacode.feature.agent.data.local.entity.TeamChatMessageEntity
import com.vantacode.feature.agent.domain.team.TeamChatRunner
import com.vantacode.feature.agent.domain.team.TeamDefinition
import com.vantacode.feature.agent.domain.team.TeamForm
import com.vantacode.feature.agent.domain.team.TeamRepository
import com.vantacode.feature.agent.domain.team.TeamSaveError
import com.vantacode.feature.settings.data.repository.DefaultModelSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/** 当前群聊里的一条展示消息（含流式渲染中的发言由 [TeamsViewModel.streaming] 单独给）。 */
data class TeamChatUiMessage(
    val id: Long,
    val speaker: String,
    val avatar: String,
    val role: String,
    val content: String,
    val createdAt: Long,
)

@OptIn(ExperimentalCoroutinesApi::class)
class TeamsViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    private val teamChatRunner: TeamChatRunner,
    private val teamChatDao: TeamChatDao,
    private val defaultModelSettings: DefaultModelSettingsRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "TeamsViewModel"
        /** 群聊历史保留上限。 */
        const val HISTORY_LIMIT = 300

        private val AUTOCOMPOSE_SYSTEM_PROMPT = """
            你是团队编制专家。根据用户描述的团队，设计 3-6 个成员岗位。
            每行一个成员，严格使用如下格式（不要加序号以外的任何其他内容）：
            角色名 | 一句话职责
            要求：角色名 ≤ 12 个中文字符；职责 ≤ 40 个中文字符，说清楚这个人负责什么、交付什么。
            直接输出成员列表，不要解释、不要加标题、不要 code block 标记。
        """.trimIndent()
    }

    // ── 列表 ───────────────────────────────────────────────
    private val _teams = MutableStateFlow<List<TeamDefinition>>(emptyList())
    val teams: StateFlow<List<TeamDefinition>> = _teams.asStateFlow()

    // ── 群聊 ───────────────────────────────────────────────
    private val _currentTeamName = MutableStateFlow<String?>(null)
    val currentTeamName: StateFlow<String?> = _currentTeamName.asStateFlow()

    val chatMessages: StateFlow<List<TeamChatUiMessage>> =
        _currentTeamName
            .flatMapLatest { name ->
                if (name == null) flowOf(emptyList())
                else teamChatDao.observeMessages(name).map { list -> list.map { it.toUi() } }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** 各成员正在流式输出的内容（speaker → 已输出文本），完成后清空。 */
    private val _streaming = MutableStateFlow<Map<String, String>>(emptyMap())
    val streaming: StateFlow<Map<String, String>> = _streaming.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _warnings = MutableStateFlow<List<String>>(emptyList())
    val warnings: StateFlow<List<String>> = _warnings.asStateFlow()

    private val _plan = MutableStateFlow<String?>(null)
    val convergedPlan: StateFlow<String?> = _plan.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var runJob: Job? = null

    init {
        viewModelScope.launch { refreshTeams() }
    }

    fun refreshTeams() {
        _teams.value = teamRepository.listAll()
    }

    fun findTeam(name: String): TeamDefinition? = teamRepository.find(name)

    /** 保存团队（新建/改名）。 */
    suspend fun saveTeam(form: TeamForm, originalName: String?): TeamSaveError? =
        teamRepository.save(form, originalName).also { refreshTeams() }

    fun deleteTeam(name: String): Boolean =
        teamRepository.delete(name).also { if (it) refreshTeams() }

    /** 进入某个团队的群聊（列表页不订阅消息）。 */
    fun openChat(teamName: String) {
        if (_currentTeamName.value == teamName) return
        _currentTeamName.value = teamName
        _streaming.value = emptyMap()
        _warnings.value = emptyList()
        _plan.value = null
        _error.value = null
    }

    fun leaveChat() {
        _currentTeamName.value = null
    }

    private fun TeamChatMessageEntity.toUi() = TeamChatUiMessage(
        id = id,
        speaker = speaker,
        avatar = avatar,
        role = role,
        content = content,
        createdAt = createdAt,
    )

    /** 发一条群聊消息并触发整轮团队讨论。 */
    fun send(text: String) {
        val teamName = _currentTeamName.value ?: return
        val task = text.trim()
        if (task.isEmpty() || _isRunning.value) return

        runJob = viewModelScope.launch {
            val team = teamRepository.find(teamName)
            if (team == null) {
                _error.value = "团队「$teamName」不存在或定义已被删除"
                return@launch
            }
            _isRunning.value = true
            _error.value = null
            _warnings.value = emptyList()
            _plan.value = null

            val sessionId = "team-chat-${team.name}-${System.currentTimeMillis()}"
            val ownerName = team.ownerName.ifBlank { TeamDefinition.DEFAULT_OWNER_NAME }

            runCatching {
                teamChatDao.insert(
                    TeamChatMessageEntity(
                        teamName = team.name,
                        speaker = ownerName,
                        avatar = team.ownerAvatar,
                        role = "owner",
                        content = task,
                        refSessionId = sessionId,
                    )
                )
            }.onFailure { FileLogger.w(TAG, "写入群聊用户消息失败", it) }

            val fallbackProviderId = defaultModelSettings.getDefaultProviderId().takeIf { it.isNotBlank() }
            val fallbackModel = defaultModelSettings.getDefaultModel().takeIf { it.isNotBlank() }

            // 历史转 Turns 拼进上下文（限制最近 40 条，控制 token）
            val prior = teamChatDao.getMessagesOnce(team.name)
                .takeLast(40)
                .map {
                    TeamChatRunner.Turn(
                        round = 0,
                        speaker = it.speaker,
                        avatar = it.avatar,
                        content = it.content,
                    )
                }

            val result = runCatching {
                teamChatRunner.run(
                    team = team,
                    task = task,
                    rounds = TeamChatRunner.DEFAULT_ROUNDS,
                    fallbackProviderId = fallbackProviderId,
                    fallbackModel = fallbackModel,
                    sessionId = sessionId,
                    priorTurns = prior,
                    onTurnDelta = { turn, delta ->
                        _streaming.value = _streaming.value + (turn.speaker to
                            ((_streaming.value[turn.speaker] ?: "") + delta))
                    },
                    onTurn = { turn ->
                        _streaming.value = _streaming.value - turn.speaker
                        runCatching {
                            teamChatDao.insert(
                                TeamChatMessageEntity(
                                    teamName = team.name,
                                    speaker = turn.speaker,
                                    avatar = turn.avatar,
                                    role = roleOf(team, turn.speaker),
                                    content = turn.content,
                                    refSessionId = sessionId,
                                )
                            )
                            teamChatDao.trim(team.name, HISTORY_LIMIT)
                        }.onFailure { FileLogger.w(TAG, "写入群聊发言失败", it) }
                    },
                )
            }

            _streaming.value = emptyMap()
            result.onSuccess { r ->
                _warnings.value = r.warnings
                _plan.value = r.plan
            }.onFailure { e ->
                FileLogger.w(TAG, "群聊执行失败", e)
                _error.value = e.message ?: e.javaClass.simpleName
            }
            _isRunning.value = false
        }
    }

    fun stop() {
        runJob?.cancel()
        runJob = null
        _streaming.value = emptyMap()
        _isRunning.value = false
    }

    fun clearWarnings() { _warnings.value = emptyList() }
    fun dismissPlan() { _plan.value = null }
    fun dismissError() { _error.value = null }

    /**
     * 「自动组队」：把团队描述（+ 额外要求）交给默认模型，产出岗位列表。
     * 返回成员列表（模型沿用本机默认模型，提示词为各自岗位职责）；失败抛异常。
     */
    suspend fun autocompose(description: String, hint: String): List<com.vantacode.feature.agent.domain.team.TeamMember> {
        val desc = description.trim()
        require(desc.isNotBlank()) { "请先填写团队是干什么的" }
        val fallbackProviderId = defaultModelSettings.getDefaultProviderId().takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("尚未配置默认模型（AI 供应商）")
        val fallbackModel = defaultModelSettings.getDefaultModel().takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("尚未配置默认模型")
        val system = AUTOCOMPOSE_SYSTEM_PROMPT
        val user = buildString {
            append("团队描述：").append(desc).append('\n')
            append("额外要求：").append(hint.trim().ifBlank { "（无）" })
        }
        val raw = teamChatRunner.completeStandalone(system, user)
        val presets = listOf("🐱", "🦊", "🐰", "🦉", "🤖", "🧠", "🚀", "🔥")
        val seen = HashSet<String>()
        val parsed = raw.lineSequence()
            .map { it.trim() }
            .map { Regex("^\\d+[.、)]\\s*").replace(it, "") }     // 去掉序号
            .filter { it.contains('|') }
            .map { line ->
                val name = line.substringBefore('|').trim().take(40)
                val duty = line.substringAfter('|').trim()
                name to duty
            }
            .filter { (name, duty) -> name.isNotBlank() && duty.isNotBlank() && seen.add(name.lowercase()) }
            .take(8)
            .toList()
        if (parsed.isEmpty()) {
            throw IllegalStateException("AI 没有返回可解析的成员（期望「角色名 | 一句话职责」）")
        }
        return parsed.mapIndexed { index, (name, duty) ->
            com.vantacode.feature.agent.domain.team.TeamMember(
                name = name,
                avatar = presets[index % presets.size],
                providerId = fallbackProviderId,
                model = fallbackModel,
                prompt = duty,
                isLead = index == 0,
            )
        }
    }

    fun clearChatHistory() {
        val name = _currentTeamName.value ?: return
        viewModelScope.launch {
            runCatching { teamChatDao.clear(name) }
        }
    }

    private fun roleOf(team: TeamDefinition, speaker: String): String =
        if (team.members.any { it.isLead && it.name == speaker }) "lead" else "member"
}
