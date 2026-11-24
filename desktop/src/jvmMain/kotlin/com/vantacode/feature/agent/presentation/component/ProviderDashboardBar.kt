package com.vantacode.feature.agent.presentation.component
import com.vantacode.core.platform.DesktopContext

import com.vantacode.core.platform.ClipData
import com.vantacode.core.platform.ClipboardManager
import com.vantacode.core.platform.Intent
import com.vantacode.core.platform.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.vantacode.core.platform.LocalDesktopContext
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantacode.core.theme.Radius
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.R
import com.vantacode.feature.settings.domain.model.AIProviderConfig
import com.vantacode.feature.settings.domain.model.AdaptiveCardAction
import com.vantacode.feature.settings.domain.model.ProviderDashboardState
import com.vantacode.core.ui.ExpandableChevronIcon
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertCircle
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.ChevronUp
import com.vantacode.core.ui.Strings
import com.vantacode.core.platform.PlatformToast

/**
 * 位于聊天输入框上方的自定义面板栏。
 * 基于 Adaptive Cards 声明式规范，支持任意自定义排版与交互。
 */
@Composable
fun ProviderDashboardBar(
    provider: AIProviderConfig,
    state: ProviderDashboardState,
    onRefresh: () -> Unit,
    maxExpandedBodyHeight: Dp,
    modifier: Modifier = Modifier,
    forceCollapse: Boolean = false,
    onRefreshByButton: () -> Unit = {},
    onExpandedChange: (Boolean) -> Unit = {}
) {
    val context = LocalDesktopContext.current
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    // 弹窗/键盘叠加时同帧收起：用派生状态而不是 LaunchedEffect 异步改 isExpanded，
    // 否则弹窗先出现顶开布局、面板后折叠，中间产生空档闪屏。
    val effectiveExpanded = isExpanded && !forceCollapse
    // 上报展开状态给外层，供叠加面板联动折叠
    LaunchedEffect(effectiveExpanded) { onExpandedChange(effectiveExpanded) }

    val cardBgColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)
    val borderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.xs)
            .clip(RoundedCornerShape(Radius.lg))
            .border(1.dp, borderColor, RoundedCornerShape(Radius.lg)),
        shape = RoundedCornerShape(Radius.lg),
        color = cardBgColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md, vertical = 10.dp)
        ) {
            when (state) {
                is ProviderDashboardState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = provider.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(Spacing.xs))
                            Text(
                                text = Strings.dashboard_fetching,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                is ProviderDashboardState.Error -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = provider.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(Radius.xs))
                                .clickable { onRefresh() }
                                .padding(horizontal = Spacing.xs, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = FeatherIcons.AlertCircle,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = Strings.dashboard_fetch_failed_retry,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                is ProviderDashboardState.Success -> {
                    val card = state.result.card
                    val onCardAction: (AdaptiveCardAction) -> Unit = { action ->
                        when (action) {
                            is AdaptiveCardAction.OpenUrl -> {
                                runCatching {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(action.url)).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                    context.startActivity(intent)
                                }.onFailure {
                                    PlatformToast.show(Strings.common_open_link_failed)
                                }
                            }
                            is AdaptiveCardAction.CopyToClipboard -> {
                                val clipboard = context.getSystemService(DesktopContext.CLIPBOARD_SERVICE) as? ClipboardManager
                                val clip = ClipData.newPlainText(action.title, action.value)
                                clipboard?.setPrimaryClip(clip)
                                PlatformToast.show(Strings.formatted(Strings.common_copied_with_title, action.title))
                            }
                        }
                    }

                    // ── 顶部单行常驻栏（点击展开/折叠） ──
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !effectiveExpanded },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = provider.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(end = Spacing.md)
                        )

                        // 收起状态展示单行卡片摘要
                        AnimatedVisibility(
                            visible = !effectiveExpanded,
                            modifier = Modifier.weight(1f),
                            enter = fadeIn(tween(180)),
                            exit = fadeOut(tween(120))
                        ) {
                            AdaptiveCardView(
                                card = card,
                                isExpanded = false,
                                onAction = onCardAction,
                                onRefresh = onRefreshByButton
                            )
                        }

                        if (effectiveExpanded) {
                            Spacer(Modifier.weight(1f))
                        }

                        Spacer(Modifier.width(Spacing.sm))

                        ExpandableChevronIcon(
                            expanded = effectiveExpanded,
                            contentDescription = if (effectiveExpanded) {
                                Strings.common_collapse
                            } else {
                                Strings.common_expand
                            },
                            size = 18.dp,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // ── 展开状态内容（平滑自上而下展开、自下而上收起） ──
                    AnimatedVisibility(
                        visible = effectiveExpanded,
                        enter = fadeIn(tween(180)) + expandVertically(tween(220)),
                        exit = fadeOut(tween(140)) + shrinkVertically(tween(180))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = maxExpandedBodyHeight)
                                .verticalScroll(rememberScrollState())
                                .padding(top = Spacing.sm),
                            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            // 卡片 Body 展开渲染
                            AdaptiveCardView(
                                card = card,
                                isExpanded = true,
                                onAction = onCardAction,
                                onRefresh = onRefreshByButton
                            )
                        }
                    }
                }
                ProviderDashboardState.Idle -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRefresh() }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = provider.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = Strings.dashboard_tap_to_query,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
