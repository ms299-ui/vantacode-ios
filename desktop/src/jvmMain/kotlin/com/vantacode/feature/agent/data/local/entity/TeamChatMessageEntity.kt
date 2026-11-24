/*
 * VantaCode Desktop — 团队群聊消息（team_chat_messages 表）。
 * 与 Android 版 team_chat_db.team_chat_messages 列一致。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 团队群聊一条消息。[role]：owner（用户）/ member / lead / system。
 * [imagePaths]：逗号分隔的附件路径（桌面端暂未启用图片输入）。
 * [refSessionId]：若该条消息由 agent 会话触发（如 TaskTool），关联会话 id。
 */
@Entity(
    tableName = "team_chat_messages",
    indices = [Index(value = ["teamName"])],
)
data class TeamChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teamName: String,
    val speaker: String,
    val avatar: String = "",
    val role: String = "member",
    val content: String,
    val imagePaths: String = "",
    val refSessionId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
)
