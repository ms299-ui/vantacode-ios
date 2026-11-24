package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vantacode.core.theme.AppThemePreset
import com.vantacode.core.theme.Radius
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.AdaptiveModalBottomSheet
import com.vantacode.core.ui.R
import com.vantacode.core.ui.stringResource
import com.vantacode.feature.settings.data.repository.AppThemeMode
import com.vantacode.core.ui.Strings
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check

/**
 * 外观主题面板：明暗模式 + 配色方案。
 *
 * 选择后不关闭弹窗，便于连续对比几套配色。
 *
 * @param selectedPresetId 当前配色方案 id，null 表示未选过（回退默认）。
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun ThemeSelectionSheet(
    selected: AppThemeMode,
    selectedPresetId: String?,
    onSelected: (AppThemeMode) -> Unit,
    onPresetSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val activePreset = AppThemePreset.findById(selectedPresetId)

    AdaptiveModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.xl)
        ) {
            SectionLabel(Strings.theme_mode_section)

            AppThemeMode.entries.forEach { mode ->
                val isSelected = mode == selected
                Surface(
                    onClick = { onSelected(mode) },
                    color = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(mode.labelRes),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            modifier = Modifier.weight(1f)
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = FeatherIcons.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(Spacing.sm))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(Modifier.height(Spacing.md))

            SectionLabel(Strings.theme_preset_section)

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                AppThemePreset.ALL_PRESETS.forEach { preset ->
                    PresetChip(
                        preset = preset,
                        darkTheme = isDarkTheme,
                        isSelected = preset.id == activePreset.id,
                        onSelect = { onPresetSelected(preset.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(horizontal = Spacing.lg)
            .padding(bottom = Spacing.sm)
    )
}

/** 配色方案色块：外圈是该主题的页面底色，内圈是主色，让浅色/深色主题一眼可辨。 */
@Composable
private fun PresetChip(
    preset: AppThemePreset,
    darkTheme: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val (accentColor, canvasColor) = preset.previewColors(darkTheme)
    val shape = RoundedCornerShape(Radius.pill)

    Surface(
        modifier = Modifier
            .clip(shape)
            .clickable(onClick = onSelect)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
                },
                shape = shape
            ),
        shape = shape,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ) {
        Row(
            modifier = Modifier.padding(start = Spacing.sm, end = 14.dp, top = 7.dp, bottom = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(canvasColor)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .size(11.dp)
                        .clip(CircleShape)
                        .background(accentColor)
                )
            }
            Spacer(Modifier.width(Spacing.sm))
            Text(
                text = stringResource(preset.nameRes),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
