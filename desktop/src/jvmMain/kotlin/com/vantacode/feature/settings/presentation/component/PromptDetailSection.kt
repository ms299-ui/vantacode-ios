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
import androidx.compose.ui.Modifier
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Spacing
import com.vantacode.feature.agent.domain.prompt.PromptFragment
import com.vantacode.feature.agent.domain.prompt.PromptFragmentSource
import com.vantacode.feature.agent.presentation.component.MarkdownContent
import com.vantacode.core.ui.Strings
import com.vantacode.core.ui.DesktopConfiguration

/**
 * 提示词片段详情页：只读预览——编号/名称/来源、摘要、正文（Markdown）。
 *
 * 要改内容走顶栏的编辑按钮，进入 [PromptEditorScreen]。
 */
@Composable
internal fun PromptDetailSection(fragment: PromptFragment) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.lg)
            .padding(bottom = Spacing.xl),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        SettingsGroup {
            InfoRow(
                label = Strings.prompts_field_number,
                value = "%02d".format(fragment.number)
            )
            SettingsDivider()
            InfoRow(
                label = Strings.prompts_field_title,
                value = fragment.title
            )
            SettingsDivider()
            InfoRow(
                label = Strings.subagent_editor_scope,
                value = stringResource(
                    when (fragment.source) {
                        PromptFragmentSource.PROJECT -> R.string.prompts_source_project
                        PromptFragmentSource.GLOBAL -> R.string.prompts_source_global
                        PromptFragmentSource.LOCAL -> R.string.prompts_source_local
                        PromptFragmentSource.BUILTIN -> R.string.prompts_source_builtin
                    }
                )
            )
        }

        if (fragment.description.isNotBlank()) {
            SettingsGroupHeader(text = Strings.skills_summary)
            SettingsGroup {
                Text(
                    text = fragment.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.lg, vertical = 12.dp)
                )
            }
        }

        SettingsGroupHeader(text = Strings.prompts_field_content)
        SettingsGroup {
            MarkdownContent(
                text = fragment.body,
                color = MaterialTheme.colorScheme.onSurface,
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
