package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.AppSwitch
import com.vantacode.feature.agent.domain.subagent.InjectPart
import com.vantacode.feature.agent.presentation.component.MarkdownContent
import com.vantacode.feature.agent.presentation.component.MarkdownRenderCache
import com.vantacode.feature.settings.presentation.SubAgentUiEntry
import com.vantacode.core.ui.Strings
import com.vantacode.core.ui.DesktopConfiguration

/**
 * 子代理详情页：分组卡片——「是否启用」开关行、「摘要」描述卡、「配置」（模型/工具集/注入项/定义文件）键值卡、
 * 「agent 提示词」正文卡（Markdown 渲染）。要改配置走顶栏的编辑按钮。
 */
@Composable
internal fun SubAgentDetailSection(
    entry: SubAgentUiEntry,
    onToggle: (Boolean) -> Unit,
    cache: MarkdownRenderCache? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.lg)
            .padding(bottom = Spacing.xl),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        SettingsGroup {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = Strings.subagent_enable,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = Strings.subagent_enable_hint,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                AppSwitch(
                    checked = !entry.disabled,
                    onCheckedChange = onToggle
                )
            }
        }

        SettingsGroupHeader(text = Strings.skills_summary)
        SettingsGroup {
            Text(
                text = entry.description.ifBlank { Strings.mcp_no_description },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = 12.dp)
            )
        }

        SettingsGroup {
            InfoRow(
                label = Strings.subagent_model,
                value = modelText(entry)
            )
            SettingsDivider()
            InfoRow(
                label = Strings.subagent_editor_mode,
                value = modeText(entry)
            )
            SettingsDivider()
            InfoRow(
                label = Strings.subagent_tools,
                value = toolsText(entry)
            )
            SettingsDivider()
            InfoRow(
                label = Strings.subagent_inject,
                value = injectText(entry.inject)
            )
            entry.filePath?.let { path ->
                SettingsDivider()
                InfoRow(
                    label = Strings.subagent_source_file,
                    value = path
                )
            }
        }

        SettingsGroupHeader(text = Strings.subagent_prompt)
        SettingsGroup {
            MarkdownContent(
                text = entry.prompt.ifBlank { Strings.mcp_no_description },
                color = MaterialTheme.colorScheme.onSurface,
                cache = cache,
                lazyScroll = true,
                modifier = Modifier
                    .fillMaxWidth()
                    // 正文卡限高：长文本在卡内懒加载滚动（外层整页仍可继续滚），避免超大 md 全量渲染卡顿。
                    .heightIn(max = DesktopConfiguration.screenHeightDp.dp * 0.62f)
                    .padding(horizontal = Spacing.lg, vertical = 12.dp)
            )
        }
    }
}

/** 键值行：左标签右值，值过长时换行。 */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.lg, vertical = 12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(Spacing.md))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun modelText(entry: SubAgentUiEntry): String {
    val parts = listOfNotNull(entry.providerId, entry.model, entry.reasoningEffort)
    return if (parts.isEmpty()) Strings.subagent_inherit_parent else parts.joinToString(" / ")
}

@Composable
private fun modeText(entry: SubAgentUiEntry): String =
    entry.mode?.name ?: Strings.subagent_inherit_parent

@Composable
private fun toolsText(entry: SubAgentUiEntry): String {
    val parts = buildList {
        if (entry.allowedTools.isNotEmpty()) {
            add(Strings.formatted(Strings.subagent_tools_allow, entry.allowedTools.joinToString(", ")))
        }
        if (entry.disallowedTools.isNotEmpty()) {
            add(Strings.formatted(Strings.subagent_tools_deny, entry.disallowedTools.joinToString(", ")))
        }
    }
    return if (parts.isEmpty()) Strings.subagent_all_tools else parts.joinToString("\n")
}

@Composable
private fun injectText(inject: Set<InjectPart>): String =
    if (inject.isEmpty()) {
        Strings.subagent_inject_none
    } else {
        // 直接展示 frontmatter 里的写法，便于用户照抄到手写的定义文件里。
        InjectPart.entries.filter { it in inject }.joinToString(", ") { it.token }
    }
