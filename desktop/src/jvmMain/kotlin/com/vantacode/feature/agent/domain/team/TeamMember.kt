/*
 * VantaCode Desktop — 团队成员模型（与 .md 定义文件、TeamChatRunner 共用）。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.team

/**
 * 团队成员：一个可被队内其他成员 @ 的角色。
 * [agent] 非空时该成员作为子代理执行（模型/工具沿用子代理定义）；否则按 [providerId] + [model] 直接呼叫某个模型。
 */
data class TeamMember(
    val name: String,
    val avatar: String = "",
    val agent: String = "",
    val providerId: String = "",
    val model: String = "",
    val prompt: String = "",
    val isLead: Boolean = false,
) {
    val usesAgent: Boolean get() = agent.isNotBlank()

    /** 列表行展示副标题。 */
    val subtitle: String
        get() = when {
            usesAgent -> "子代理 · $agent"
            model.isNotBlank() -> "模型 · $model"
            else -> "未配置模型"
        }
}
