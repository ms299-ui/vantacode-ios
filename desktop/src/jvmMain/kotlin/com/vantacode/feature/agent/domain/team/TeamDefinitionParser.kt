/*
 * VantaCode Desktop — 团队定义 `.md` 解析/序列化。
 * frontmatter 用 snakeyaml 解析，成员按 `members:` 列表解析；
 * 与 Android 版 TeamDefinitionParser 的格式完全一致（手机/桌面团队定义可互换）。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.team

import com.vantacode.core.util.FileLogger
import com.vantacode.feature.workspace.domain.FileAccessProvider
import org.yaml.snakeyaml.Yaml

/** 团队定义文件读写。 */
object TeamDefinitionParser {
    private const val TAG = "TeamDefinitionParser"

    /** 读取单个团队定义文件；失败返回 null 并留痕。 */
    fun parse(provider: FileAccessProvider, filePath: String): TeamDefinition? {
        val text = runCatching { provider.readFile(filePath) }.getOrElse {
            FileLogger.w(TAG, "读取团队定义失败: $filePath", it)
            return null
        }
        val (frontmatter, body) = splitAndParseFrontmatter(text) ?: return null

        val fileName = filePath.substringAfterLast('/')
        val name = frontmatter["name"]?.toString()?.trim()?.takeIf { it.isNotBlank() }
            ?: fileName.substringBeforeLast('.')
        val description = (frontmatter["description"]?.toString() ?: "").take(TeamDefinition.MAX_DESCRIPTION_LENGTH)
        val members = parseMembers(frontmatter["members"])
        if (members.isEmpty()) {
            FileLogger.w(TAG, "团队定义没有可用成员，已跳过: $filePath")
            return null
        }
        val ownerName = frontmatter["ownerName"]?.toString()?.trim()?.takeIf { it.isNotBlank() }
            ?: TeamDefinition.DEFAULT_OWNER_NAME
        val ownerAvatar = frontmatter["ownerAvatar"]?.toString()?.trim() ?: ""
        return TeamDefinition(
            name = name,
            description = description,
            ownerName = ownerName,
            ownerAvatar = ownerAvatar,
            members = members,
            strategy = body.trim(),
            filePath = filePath,
        )
    }

    /** 同时接受 YAML 列表与逗号分隔字符串两种写法。 */
    internal fun parseMembers(raw: Any?): List<TeamMember> {
        val list = when (raw) {
            is List<*> -> raw
            is String -> raw.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            else -> emptyList()
        }
        val parsed = list.mapNotNull { item ->
            when (item) {
                is Map<*, *> -> {
                    val map = item
                    fun str(key: String): String = map[key]?.toString()?.trim() ?: ""
                    val rawName = str("name")
                    val agent = str("agent")
                    val model = str("model")
                    if (rawName.isBlank() && agent.isBlank() && model.isBlank()) return@mapNotNull null
                    val name = rawName.ifBlank { agent.ifBlank { model } }
                    TeamMember(
                        name = name,
                        avatar = str("avatar"),
                        agent = agent,
                        providerId = str("provider"),
                        model = model,
                        prompt = str("prompt"),
                        isLead = str("lead").equals("true", ignoreCase = true),
                    )
                }
                else -> item?.toString()?.trim()?.takeIf { it.isNotEmpty() }
                    ?.let { TeamMember(name = it, agent = it) }
            }
        }
        // 按名称去重（大小写不敏感），最多 MAX_MEMBERS 个
        val seen = HashSet<String>()
        return parsed.filter { seen.add(it.name.lowercase()) }.take(TeamDefinition.MAX_MEMBERS)
    }

    /** 把表单写回定义文件文本（frontmatter + 正文）。 */
    fun serialize(
        name: String,
        description: String,
        ownerName: String,
        ownerAvatar: String,
        members: List<TeamMember>,
        strategy: String,
    ): String = buildString {
        appendLine("---")
        appendLine("name: ${quote(name)}")
        appendLine("description: ${quote(description)}")
        appendLine("ownerName: ${quote(ownerName)}")
        if (ownerAvatar.isNotBlank()) appendLine("ownerAvatar: ${quote(ownerAvatar)}")
        appendLine("members:")
        for (member in members) {
            appendLine("  - name: ${quote(member.name)}")
            if (member.avatar.isNotBlank()) appendLine("    avatar: ${quote(member.avatar)}")
            if (member.agent.isNotBlank()) appendLine("    agent: ${quote(member.agent)}")
            if (member.providerId.isNotBlank()) appendLine("    provider: ${quote(member.providerId)}")
            if (member.model.isNotBlank()) appendLine("    model: ${quote(member.model)}")
            if (member.isLead) appendLine("    lead: true")
            if (member.prompt.isNotBlank()) {
                appendLine("    prompt: |")
                for (line in member.prompt.trim().lines()) {
                    appendLine("      $line")
                }
            }
        }
        appendLine("---")
        appendLine(strategy.trim())
    }

    /** frontmatter 与正文分离；frontmatter 顶层不是键值表返回 null。 */
    private fun splitAndParseFrontmatter(text: String): Pair<Map<String, Any>, String>? {
        val normalized = text.replace("\r\n", "\n")
        if (!normalized.startsWith("---\n")) return emptyMap<String, Any>() to normalized
        val end = normalized.indexOf("\n---", startIndex = 3)
        if (end < 0) return emptyMap<String, Any>() to normalized
        val block = normalized.substring(4, end)
        val rest = normalized.substring(end + 4).removePrefix("\n")
        val loaded = try {
            Yaml().load<Any>(block)
        } catch (e: Exception) {
            FileLogger.w(TAG, "解析团队定义 YAML 失败", e)
            null
        }
        val map = loaded as? Map<*, *>
        if (loaded != null && map == null) {
            FileLogger.w(TAG, "团队定义 frontmatter 顶层不是键值表，已跳过: ${loaded.javaClass.simpleName}")
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return (map ?: emptyMap<String, Any>()) as Map<String, Any> to rest
    }

    /** frontmatter 字符串值：双引号包裹，转义反斜杠与引号，换行压成空格保证单行。 */
    private fun quote(value: String): String {
        val escaped = value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace(Regex("\\s*\\n\\s*"), " ")
            .trim()
        return "\"$escaped\""
    }
}
