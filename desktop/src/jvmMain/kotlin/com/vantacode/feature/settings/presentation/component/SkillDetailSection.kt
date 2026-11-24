package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import com.vantacode.core.ui.AppSwitch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.unit.dp
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Spacing
import com.vantacode.feature.agent.presentation.component.MarkdownContent
import com.vantacode.feature.agent.presentation.component.MarkdownRenderCache
import com.vantacode.feature.settings.presentation.SkillUiEntry
import com.vantacode.core.ui.Strings
import com.vantacode.core.ui.DesktopConfiguration

/**
 * 技能详情页：分组卡片——「是否启用」开关行、「摘要」描述卡、「正文」指令卡（Markdown 渲染）。
 * 卡片左上小标题与设置主页分组一致。
 */
@Composable
internal fun SkillDetailSection(
    entry: SkillUiEntry,
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
        // 卡片 1：是否启用（开关行）
        SettingsGroup {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.skills_enable,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                AppSwitch(
                    checked = !entry.disabled,
                    onCheckedChange = onToggle
                )
            }
        }

        // 卡片 2：摘要
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

        // 卡片 3：正文（Markdown 渲染）
        SettingsGroupHeader(text = Strings.skills_instructions)
        SettingsGroup {
            MarkdownContent(
                text = entry.instructions.ifBlank { Strings.mcp_no_description },
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
