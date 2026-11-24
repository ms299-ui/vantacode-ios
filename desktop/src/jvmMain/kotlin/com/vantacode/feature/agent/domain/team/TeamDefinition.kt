/*
 * VantaCode Desktop — 团队定义（持久化为 `~/.aicode/teams/<name>.md`，name 即文件名）。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.team

/**
 * 一支团队：基本信息 + 成员 + 协作约定（markdown 正文）。
 * 与 Android 版 `.md` 格式完全一致，桌面与手机团队定义可互换。
 */
data class TeamDefinition(
    val name: String,
    val description: String = "",
    val ownerName: String = DEFAULT_OWNER_NAME,
    val ownerAvatar: String = "",
    val members: List<TeamMember> = emptyList(),
    val strategy: String = "",
    val filePath: String? = null,
) {
    companion object {
        const val DEFAULT_OWNER_NAME = "我"
        const val MAX_MEMBERS = 12
        const val MAX_NAME_LENGTH = 40
        const val MAX_DESCRIPTION_LENGTH = 500
    }
}

/** 团队定义表单（设置页编辑用）。 */
data class TeamForm(
    val name: String,
    val description: String = "",
    val ownerName: String = TeamDefinition.DEFAULT_OWNER_NAME,
    val ownerAvatar: String = "",
    val members: List<TeamMember> = emptyList(),
    val strategy: String = "",
)

/** 保存团队的业务错误。 */
enum class TeamSaveError {
    /** 名称为空、过长、含非法字符或与占位符冲突。 */
    INVALID_NAME,

    /** 已存在同名团队（大小写不敏感）。 */
    NAME_CONFLICT,

    /** 成员数超过 [TeamDefinition.MAX_MEMBERS]。 */
    MEMBER_LIMIT,

    /** 过滤后没有任何可发言的成员。 */
    NO_MEMBERS,
}
