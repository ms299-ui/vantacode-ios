package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.Strings
import com.vantacode.feature.settings.data.remote.UpdateCheckResult

private const val RELEASES_URL = "https://github.com/Hye-steam/VantaCode-Desktop/releases"

/**
 * 更新检查对话框。四种状态：检查中 / 已是最新 / 发现新版本 / 检查失败。
 * 桌面端不在应用内安装，发现新版本时展示变更日志并引导到 Release 页下载。
 */
@Composable
internal fun UpdateCheckDialog(
    checking: Boolean,
    result: UpdateCheckResult?,
    currentVersion: String,
    onDismiss: () -> Unit,
    onOpenRelease: (String) -> Unit
) {
    when {
        checking -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(Strings.about_check_update) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Text(
                        text = Strings.about_checking_update,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            confirmButton = {}
        )

        result is UpdateCheckResult.UpToDate -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(Strings.about_up_to_date) },
            text = {
                Text(
                    text = Strings.formatted(Strings.about_up_to_date_detail, currentVersion),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = { TextButton(onClick = onDismiss) { Text(Strings.about_ok) } }
        )

        result is UpdateCheckResult.NewVersion -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(Strings.about_new_version_found) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.xs)) {
                    Text(
                        text = result.info.latestTag,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    ChangelogText(result.info.changelog)
                }
            },
            confirmButton = {
                TextButton(onClick = { onOpenRelease(RELEASES_URL) }) {
                    Text(Strings.about_download)
                }
            },
            dismissButton = { TextButton(onClick = onDismiss) { Text(Strings.about_later) } }
        )

        result is UpdateCheckResult.Error -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(Strings.about_check_failed) },
            text = {
                Text(
                    text = result.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = { TextButton(onClick = onDismiss) { Text(Strings.about_ok) } }
        )

        else -> Unit
    }
}

/** 更新日志正文：限高可滚动。 */
@Composable
private fun ChangelogText(changelog: String) {
    Text(
        text = changelog,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 320.dp)
            .verticalScroll(rememberScrollState())
            .padding(end = Spacing.xs),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
