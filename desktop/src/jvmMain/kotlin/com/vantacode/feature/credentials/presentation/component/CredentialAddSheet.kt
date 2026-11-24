package com.vantacode.feature.credentials.presentation.component

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
import com.vantacode.feature.settings.presentation.component.SettingsDivider
import com.vantacode.feature.settings.presentation.component.SettingsGroup
import com.vantacode.feature.settings.presentation.component.SettingsRow
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit3
import compose.icons.feathericons.Github
import com.vantacode.core.ui.Strings

/**
 * 「添加凭据」底部弹层：提供推荐的 GitHub OAuth 设备码快捷登录与手动配置两种方式。
 *
 * 版式与 [com.vantacode.feature.settings.presentation.component.McpAddSheet]、
 * [com.vantacode.feature.settings.presentation.component.SkillAddSheet] 保持一致：
 * 标题 + 分组卡片内的可点行（图标 / 标题 / 说明 / 右箭头）。
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun CredentialAddSheet(
    onManual: () -> Unit,
    onQuickLogin: () -> Unit,
    onDismiss: () -> Unit
) {
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
                text = Strings.credential_add_sheet_title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            SettingsGroup {
                SettingsRow(
                    icon = FeatherIcons.Github,
                    title = Strings.credential_add_github_title,
                    subtitle = Strings.credential_add_github_desc,
                    onClick = onQuickLogin
                )
                SettingsDivider()
                SettingsRow(
                    icon = FeatherIcons.Edit3,
                    title = Strings.credential_add_manual_title,
                    subtitle = Strings.credential_add_manual_subtitle,
                    onClick = onManual
                )
            }
        }
    }
}
