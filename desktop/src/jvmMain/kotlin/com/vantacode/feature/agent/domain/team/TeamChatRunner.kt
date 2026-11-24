/*
 * VantaCode Desktop — 团队群聊执行器：给定团队与任务，让成员依次发言、
 * 由「会议主持」判断是否收敛，输出讨论结果并给出可执行方案。
 *
 * 与 Android 版 TeamChatRunner 的行为一致：
 * - 解析每位成员（子代理按定义执行；否则按 provider+model 直接呼叫）；
 * - 每轮按成员顺序发言，逐字流式回调（onTurnDelta），整轮结束回调 onTurn 落库；
 * - 每轮结束后用 fallback provider/model 判断是否收敛，收敛即提前结束；
 * - 全程记录 LlmCallRecord（kind="team_chat"）。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.team

import com.vantacode.core.platform.DesktopAssets
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.data.local.dao.LlmCallRecordDao
import com.vantacode.feature.agent.data.local.entity.LlmCallRecordEntity
import com.vantacode.feature.agent.data.remote.anthropic.AnthropicApi
import com.vantacode.feature.agent.data.remote.gemini.GeminiApi
import com.vantacode.feature.agent.data.remote.openai.OpenAIApi
import com.vantacode.feature.agent.domain.model.AgentMessage
import com.vantacode.feature.agent.domain.provider.AIProvider
import com.vantacode.feature.agent.domain.provider.AIResponse
import com.vantacode.feature.agent.domain.provider.AIStreamChunk
import com.vantacode.feature.agent.domain.provider.AllKeysFailedException
import com.vantacode.feature.agent.domain.provider.AnthropicAdapter
import com.vantacode.feature.agent.domain.provider.fixedTemperature
import com.vantacode.feature.agent.domain.provider.GeminiAdapter
import com.vantacode.feature.agent.domain.provider.isKeySwitchFailure
import com.vantacode.feature.agent.domain.provider.KeySwitchOutcome
import com.vantacode.feature.agent.domain.provider.OpenAIAdapter
import com.vantacode.feature.agent.domain.provider.toReasoningCapability
import com.vantacode.feature.agent.domain.subagent.AgentDefinitionRepository
import com.vantacode.feature.settings.data.remote.ModelMetadataService
import com.vantacode.feature.settings.data.repository.DefaultModelSettingsRepository
import com.vantacode.feature.settings.data.repository.GeneralSettingsRepository
import com.vantacode.feature.settings.data.repository.ProviderKeyRotator
import com.vantacode.feature.settings.domain.model.AIProviderConfig
import com.vantacode.feature.settings.domain.model.ProviderType
import com.vantacode.feature.settings.domain.repository.AIProviderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamChatRunner @Inject constructor(
    private val aiProviderRepository: AIProviderRepository,
    private val anthropicApi: AnthropicApi,
    private val openAIApi: OpenAIApi,
    private val geminiApi: GeminiApi,
    private val keyRotator: ProviderKeyRotator,
    private val modelMetadataService: ModelMetadataService,
    private val generalSettingsRepository: GeneralSettingsRepository,
    private val defaultModelSettingsRepository: DefaultModelSettingsRepository,
    private val agentDefinitionRepository: AgentDefinitionRepository,
    private val llmCallRecordDao: LlmCallRecordDao,
) {
    companion object {
        const val DEFAULT_ROUNDS = 2
        const val MAX_ROUNDS = 4
        private const val TAG = "TeamChatRunner"
    }

    data class Speaker(
        val displayName: String,
        val prompt: String,
        val avatar: String,
        val providerId: String?,
        val model: String?,
    )

    data class Turn(
        val round: Int,
        val speaker: String,
        val avatar: String,
        val content: String,
    )

    data class RunResult(
        val turns: List<Turn>,
        val warnings: List<String>,
        val converged: Boolean,
        /** 主持人收敛时给出的方案摘要（null = 未收敛）。 */
        val plan: String?,
    )

    /**
     * 让 [team] 针对 [task] 开一场群聊。
     *
     * @param fallbackProviderId 成员未指定 provider/model 时的回退 provider（通常传当前默认 provider）；judge 也用它。
     * @param fallbackModel 同上，回退模型。
     * @param priorTurns 已有讨论（接着上次的上下文继续）；round 序号从 prior 最大值 + 1 继续。
     * @param onTurnDelta 流式增量回调（speaker, delta）。
     * @param onTurn 每条发言落库前回调（speaker, 完整内容）；返回 Unit。
     */
    suspend fun run(
        team: TeamDefinition,
        task: String,
        rounds: Int = DEFAULT_ROUNDS,
        fallbackProviderId: String?,
        fallbackModel: String?,
        sessionId: String,
        priorTurns: List<Turn> = emptyList(),
        onTurnDelta: suspend (Turn, delta: String) -> Unit = { _, _ -> },
        onTurn: suspend (Turn) -> Unit = { },
    ): RunResult {
        val warnings = mutableListOf<String>()
        val speakers = team.members.mapNotNull { resolveSpeaker(it, warnings) }
        if (speakers.isEmpty()) {
            val first = warnings.firstOrNull()?.let { "：$it" } ?: ""
            throw IllegalStateException("团队「${team.name}」没有可发言的成员$first")
        }

        val effectiveRounds = rounds.coerceIn(1, MAX_ROUNDS)
        val turns = priorTurns.toMutableList()
        var startRound = (priorTurns.maxOfOrNull { it.round } ?: 0) + 1

        var plan: String? = null
        for (i in 0 until effectiveRounds) {
            val round = startRound + i
            for (speaker in speakers) {
                val turn = Turn(round, speaker.displayName, speaker.avatar, "")
                val content = runCatching {
                    speak(
                        team = team,
                        speaker = speaker,
                        task = task,
                        turns = turns,
                        round = round,
                        fallbackProviderId = fallbackProviderId,
                        fallbackModel = fallbackModel,
                        sessionId = sessionId,
                        onDelta = { delta -> onTurnDelta(turn.copy(content = ""), delta) },
                    )
                }.getOrElse { e ->
                    FileLogger.w(TAG, "成员发言失败: ${speaker.displayName}", e)
                    warnings += "${speaker.displayName} 发言失败：${e.message ?: e.javaClass.simpleName}"
                    "（发言失败）"
                }
                val finalTurn = turn.copy(content = content.trim())
                turns += finalTurn
                onTurn(finalTurn)
            }
            plan = runCatching { judgeConverged(team, turns, fallbackProviderId, fallbackModel, sessionId) }
                .onFailure { FileLogger.w(TAG, "主持人判断收敛失败", it) }
                .getOrNull()
            if (plan != null) break
        }

        return RunResult(
            turns = turns,
            warnings = warnings,
            converged = plan != null,
            plan = plan,
        )
    }

    /** 供「自动组队」等一次性场景的独立补全（非流式）；沿用默认 provider/model。 */
    suspend fun completeStandalone(systemPrompt: String, user: String): String =
        buildProvider(null, null, "team-autocompose").complete(
            systemPrompt = systemPrompt,
            messages = listOf(AgentMessage.UserMessage(content = user)),
        ).content

    // ── 内部实现 ────────────────────────────────────────────────

    private fun resolveSpeaker(member: TeamMember, warnings: MutableList<String>): Speaker? {
        if (member.usesAgent) {
            val definition = agentDefinitionRepository.findIncludingDisabled(member.agent)
            if (definition == null) {
                warnings += "${member.name} 引用的子代理「${member.agent}」不存在，已跳过"
                return null
            }
            return Speaker(
                displayName = member.name,
                prompt = definition.prompt,
                avatar = member.avatar,
                providerId = definition.providerId,
                model = definition.model,
            )
        }
        if (member.model.isBlank()) {
            warnings += "${member.name} 没有指定模型，已跳过"
            return null
        }
        val prompt = member.prompt.trim().ifBlank {
            "你是「${member.name}」，就讨论的问题给出你自己的判断。"
        }
        return Speaker(
            displayName = member.name,
            prompt = prompt,
            avatar = member.avatar,
            providerId = member.providerId.takeIf { it.isNotBlank() },
            model = member.model,
        )
    }

    private suspend fun speak(
        team: TeamDefinition,
        speaker: Speaker,
        task: String,
        turns: List<Turn>,
        round: Int,
        fallbackProviderId: String?,
        fallbackModel: String?,
        sessionId: String,
        onDelta: suspend (String) -> Unit,
    ): String {
        val provider = buildProvider(speaker.providerId ?: fallbackProviderId, speaker.model ?: fallbackModel, sessionId)
        val systemPrompt = buildSpeakerPrompt(team, speaker.displayName, speaker.prompt)
        val userMessage = buildRoundMessage(team, task, turns, round, speaker.displayName)
        val startedAt = System.currentTimeMillis()
        val buffer = StringBuilder()
        var response: AIResponse? = null
        val messages = listOf(AgentMessage.UserMessage(content = userMessage))
        val streamResult = runCatching {
            provider.completeStream(
                systemPrompt = systemPrompt,
                messages = messages,
            ).collect { chunk ->
                when (chunk) {
                    is AIStreamChunk.TextDelta -> {
                        buffer.append(chunk.text)
                        onDelta(chunk.text)
                    }
                    is AIStreamChunk.ReasoningDelta -> Unit
                    is AIStreamChunk.Final -> response = chunk.response
                    is AIStreamChunk.Retrying -> Unit
                    is AIStreamChunk.KeySwitched -> Unit
                    is AIStreamChunk.ToolCallDeclared -> Unit
                }
            }
        }
        if (streamResult.isFailure) {
            FileLogger.w(TAG, "流式发言失败: ${speaker.displayName}，尝试非流式兜底", streamResult.exceptionOrNull())
            val fallback = provider.complete(systemPrompt = systemPrompt, messages = messages)
            response = fallback
            buffer.setLength(0)
            buffer.append(fallback.content)
            onDelta(fallback.content)
        }
        val finalResponse = response
        if (buffer.isBlank() && finalResponse != null) buffer.append(finalResponse.content)
        recordCall(sessionId, provider, finalResponse, System.currentTimeMillis() - startedAt)
        return buffer.toString()
    }

    /** 会议主持：判断讨论是否收敛到可执行方案；收敛返回方案摘要（3-6 行），否则 null。 */
    private suspend fun judgeConverged(
        team: TeamDefinition,
        turns: List<Turn>,
        fallbackProviderId: String?,
        fallbackModel: String?,
        sessionId: String,
    ): String? {
        val provider = buildProvider(fallbackProviderId, fallbackModel, sessionId)
        val transcript = turns.joinToString("\n") { "${it.speaker}：${it.content}" }
        val prompt = buildString {
            append("「${team.name}」的讨论记录：\n")
            append(transcript)
            append("\n\n判断标准：是否已经明确到「谁负责做什么、交付什么」，且没有未决的关键分歧。\n")
            append("如果已收敛：第一行输出 CONVERGED，随后用 3-6 行写出最终方案（谁负责什么、验收标准）。\n")
            append("如果没收敛：只输出一行，以 CONTINUE 开头，跟一句还差什么。")
        }
        val response = provider.complete(
            systemPrompt = "你是会议主持。只判断讨论是否已经收敛到可以立刻开工的具体方案，不做别的。",
            messages = listOf(AgentMessage.UserMessage(content = prompt)),
        )
        val text = response.content.trim()
        if (!text.startsWith("CONVERGED", ignoreCase = true)) return null
        val planBody = text.lineSequence().drop(1).joinToString("\n").trim()
        return planBody.ifBlank { "（主持人未给出细节，按上面这轮讨论的结论执行）" }
    }

    private fun readIdentity(): String? = runCatching {
        DesktopAssets.open("prompts/00-identity.md").bufferedReader().use { it.readText().trim() }
            .takeIf { it.isNotBlank() }
    }.getOrNull()

    private fun buildSpeakerPrompt(team: TeamDefinition, speakerName: String, ownPrompt: String): String =
        buildString {
            readIdentity()?.let { append(it).append("\n\n") }
            append("你是「${team.name}」团队的成员「$speakerName」，正与其他成员围在同一张桌上讨论。\n")
            if (team.description.isNotBlank()) append("团队职责：${team.description.trim()}\n")
            append("\n你的职责：\n${ownPrompt.trim()}\n")
            if (team.strategy.isNotBlank()) append("\n团队协作约定：\n${team.strategy.trim()}\n")
            append(
                "\n群聊规则：\n" +
                    "- 你看得到本场讨论里所有人的发言；轮到你时直接说你要说的，不要复述已达成一致的内容。\n" +
                    "- 可以反对、补充、指出别人漏掉的前提；有分歧就摆出依据，不要客气，也不要和稀泥。\n" +
                    "- 已经能下结论时给出明确结论，别再重复讨论过程。\n" +
                    "- 只发言，不动手：本轮不需要、也不能调用工具或执行命令。\n" +
                    "- 长度控制在 200 字以内；需要贴代码或清单时按必要长度写。\n"
            )
        }

    private fun buildRoundMessage(
        team: TeamDefinition,
        task: String,
        turns: List<Turn>,
        round: Int,
        speakerName: String,
    ): String = buildString {
        append("【讨论主题】\n${task.trim()}\n\n")
        if (turns.isEmpty()) {
            append("【群聊记录】暂无，你是第一个发言的人。\n")
        } else {
            append("【群聊记录】\n")
            var lastRound = -1
            for (turn in turns) {
                if (turn.round > 0 && turn.round != lastRound) {
                    append("— 第 ${turn.round} 轮 —\n")
                    lastRound = turn.round
                }
                append("${turn.speaker}：${turn.content}\n\n")
            }
        }
        append("\n现在轮到第 $round 轮的「$speakerName」发言。")
    }

    /** 由 providerId/model 构造独立 [AIProvider]；找不到或不可用时抛 [IllegalStateException]。 */
    private suspend fun buildProvider(providerIdRaw: String?, model: String?, sessionId: String): AIProvider {
        val config = resolveConfig(providerIdRaw, model)
            ?: throw IllegalStateException("未配置可用的 AI 供应商/模型（providerId=$providerIdRaw, model=$model）")
        return createStandaloneProvider(config, sessionId)
    }

    private suspend fun resolveConfig(providerIdRaw: String?, model: String?): AIProviderConfig? {
        if (!providerIdRaw.isNullOrBlank()) {
            val config = aiProviderRepository.getProviderById(providerIdRaw) ?: return null
            if (!config.isEnabled || !config.hasUsableApiKey) return null
            val selected = model?.takeIf { it.isNotBlank() && it in config.models }
            return if (selected != null) config.copy(selectedModel = selected) else config
        }
        if (!model.isNullOrBlank()) {
            // 没指定 provider 时，在已启用且模型列表含该模型的 provider 里找一个
            return aiProviderRepository.getAllProviders().first()
                .firstOrNull { it.isEnabled && it.hasUsableApiKey && model in it.models }
                ?.copy(selectedModel = model)
        }
        // 完全没指定：沿用全局默认模型
        val defaultProviderId = defaultModelSettingsRepository.getDefaultProviderId()
        val defaultModel = defaultModelSettingsRepository.getDefaultModel()
        if (defaultProviderId.isBlank() || defaultModel.isBlank()) return null
        val config = aiProviderRepository.getProviderById(defaultProviderId) ?: return null
        if (!config.isEnabled || !config.hasUsableApiKey) return null
        if (defaultModel !in config.models) return null
        return config.copy(selectedModel = defaultModel)
    }

    private suspend fun createStandaloneProvider(config: AIProviderConfig, sessionId: String): AIProvider {
        val provider: AIProvider = when (config.type) {
            ProviderType.ANTHROPIC -> AnthropicAdapter(anthropicApi).also {
                it.cacheBreakpointsEnabled = config.anthropicCacheBreakpoints
            }
            ProviderType.GEMINI -> GeminiAdapter(geminiApi)
            else -> OpenAIAdapter(openAIApi).also {
                it.chatCacheKeyEnabled = config.openaiChatCacheKey
            }
        }
        val activeKeys = config.effectiveApiKeys
        provider.apiKey = keyRotator.activeKey(config, sessionId)
            ?: throw IllegalStateException("「${config.name}」的 Key 均在冷却中，请稍后重试")
        provider.keySwitcher = if (activeKeys.size > 1) {
            { error, failedKey, triedKeys ->
                if (!error.isKeySwitchFailure(config.effectiveKeySwitchStatusCodes)) {
                    null
                } else {
                    val switched = keyRotator.reportFailure(config.id, sessionId, failedKey, triedKeys)
                        ?: throw AllKeysFailedException(
                            "「${config.name}」的 ${activeKeys.size} 个 Key 均失败：${error.message ?: error.javaClass.simpleName}",
                            error,
                        )
                    KeySwitchOutcome(switched.newKey, switched.newIndex, switched.total)
                }
            }
        } else {
            null
        }
        provider.baseUrl = config.baseUrl
        provider.model = config.effectiveModel
        provider.useFullUrl = config.useFullUrl
        provider.useResponseApi = config.useResponseApi
        provider.providerId = config.id
        provider.logSessionId = sessionId
        provider.customHeaders = config.customHeaders
        val metadata = modelMetadataService.resolve(config.id, config.type, config.effectiveModel)
        provider.maxOutputTokens = metadata.outputTokens
        provider.reasoningCapability = metadata.toReasoningCapability()
        provider.temperature = if (metadata.supportsCustomTemperature) fixedTemperature(config.effectiveModel) else null
        provider.firstByteTimeoutMs = generalSettingsRepository.firstByteTimeoutMs()
        provider.streamIdleTimeoutMs = generalSettingsRepository.streamIdleTimeoutMs()
        provider.maxNetworkRetries = generalSettingsRepository.maxNetworkRetries()
        return provider
    }

    private suspend fun recordCall(sessionId: String, provider: AIProvider, response: AIResponse?, durationMs: Long) {
        runCatching {
            llmCallRecordDao.insert(
                LlmCallRecordEntity(
                    sessionId = sessionId,
                    providerId = provider.providerId,
                    model = provider.model,
                    kind = "team_chat",
                    inputTokens = response?.inputTokens ?: 0,
                    outputTokens = response?.outputTokens ?: 0,
                    cachedInputTokens = response?.cachedInputTokens ?: 0,
                    cacheCreationTokens = response?.cacheCreationTokens ?: 0,
                    durationMillis = durationMs.toInt(),
                    status = if (response == null) "error" else "success",
                    errorMessage = null,
                    stopReason = response?.stopReason,
                    createdAt = System.currentTimeMillis(),
                )
            )
        }.onFailure { FileLogger.w(TAG, "记录 team_chat 调用失败", it) }
    }
}
