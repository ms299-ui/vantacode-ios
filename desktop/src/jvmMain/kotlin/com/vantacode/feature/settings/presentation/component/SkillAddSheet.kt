package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.AdaptiveModalBottomSheet
import com.vantacode.core.ui.SegmentedTabs
import com.vantacode.feature.agent.domain.skill.SkillScope
import compose.icons.FeatherIcons
import compose.icons.feathericons.Archive
import compose.icons.feathericons.Edit3
import compose.icons.feathericons.FileText
import com.vantacode.core.ui.Strings

/**
 * 「添加技能」底部弹层：顶部选作用域（全局 / 当前项目），下方三种添加方式——
 * 手动新建（进编辑表单）、从文件导入（.md）、从压缩包导入（.zip，可含多个技能）。
 *
 * 版式与 [McpAddSheet] 保持一致：标题 + 小字标签 + 分段控件选作用域 + 分组卡片列添加方式。
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SkillAddSheet(
    scope: SkillScope,
    onScopeChange: (SkillScope) -> Unit,
    onManual: () -> Unit,
    onPickFile: () -> Unit,
    onPickZip: () -> Unit,
    onDismiss: () -> Unit
) {
    val scopes = listOf(SkillScope.GLOBAL, SkillScope.PROJECT)

    AdaptiveModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg)
                .padding(bottom = Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Text(
                text = Strings.skills_add_sheet_title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                Text(
                    text = Strings.skills_editor_scope,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SegmentedTabs(
                    selected = scopes.indexOf(scope).coerceAtLeast(0),
                    labels = listOf(
                        Strings.skills_scope_global,
                        Strings.skills_scope_project
                    ),
                    onSelect = { onScopeChange(scopes[it]) }
                )
            }

            SettingsGroup {
                SettingsRow(
                    icon = FeatherIcons.Edit3,
                    title = Strings.skills_add_manual,
                    subtitle = Strings.skills_add_manual_desc,
                    onClick = onManual
                )
                SettingsDivider()
                SettingsRow(
                    icon = FeatherIcons.FileText,
                    title = Strings.skills_add_from_file,
                    subtitle = Strings.skills_add_from_file_desc,
                    onClick = onPickFile
                )
                SettingsDivider()
                SettingsRow(
                    icon = FeatherIcons.Archive,
                    title = Strings.skills_add_from_zip,
                    subtitle = Strings.skills_add_from_zip_desc,
                    onClick = onPickZip
                )
            }
        }
    }
}
