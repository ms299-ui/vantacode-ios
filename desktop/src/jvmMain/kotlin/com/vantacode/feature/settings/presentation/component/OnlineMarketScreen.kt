package com.vantacode.feature.settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vantacode.core.theme.Spacing
import com.vantacode.core.theme.semanticColors
import com.vantacode.core.ui.Strings
import com.vantacode.di.AppGraph
import com.vantacode.feature.settings.data.repository.MarketEntry
import com.vantacode.feature.settings.data.repository.MarketRepository
import com.vantacode.feature.settings.data.repository.SkillInstallError
import com.vantacode.feature.settings.data.repository.SkillInstallResult
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.ChevronUp
import compose.icons.feathericons.Download
import compose.icons.feathericons.RefreshCw
import compose.icons.feathericons.Search
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** 安装结果提示条的状态。 */
private data class MarketInstallNotice(val text: String, val success: Boolean)

/**
 * 技能市场（在线）分区。
 *
 * 交互与 Android 版一致：空关键词浏览注册表 / claude-skills 话题，有关键词时查 GitHub
 * 搜索接口；条目可展开看详情并一键安装到 `~/.aicode/skills/`。
 *
 * 桌面端不再使用 Scaffold/TopAppBar——设置页自带标题栏与返回导航，这里只负责内容区。
 */
@Composable
internal fun MarketSection(
    repository: MarketRepository = AppGraph.marketRepository,
    modifier: Modifier = Modifier,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var entries by remember { mutableStateOf<List<MarketEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var sortByStars by rememberSaveable { mutableStateOf(true) }
    var reloadKey by remember { mutableIntStateOf(0) }
    var page by remember { mutableIntStateOf(1) }
    var hasMore by remember { mutableStateOf(false) }
    var loadingMore by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf<MarketInstallNotice?>(null) }
    var installedNames by remember { mutableStateOf<Set<String>>(emptySet()) }
    val scope = rememberCoroutineScope()

    // 关键词变了就重新查在线接口；空关键词走注册表/话题浏览。输入停顿 450ms 再发请求，省配额。
    LaunchedEffect(searchQuery, reloadKey) {
        if (searchQuery.isNotBlank()) delay(450)
        isLoading = true
        page = 1
        entries = if (searchQuery.isBlank()) {
            repository.loadSkillRegistry()
        } else {
            repository.searchSkillsOnline(searchQuery, page = 1)
        }
        hasMore = entries.isNotEmpty()
        installedNames = repository.listInstalledSkills().toSet()
        isLoading = false
    }

    val filtered = remember(searchQuery, entries, sortByStars) {
        val matched = repository.searchSkills(entries, searchQuery)
        if (sortByStars) matched.sortedByDescending { it.stars } else matched.sortedBy { it.name }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Spacing.lg, end = Spacing.sm, top = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ModelSearchField(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = Strings.market_search_hint,
                modifier = Modifier.weight(1f),
                trailing = if (searchQuery.isNotBlank()) {
                    {
                        TextButton(onClick = { searchQuery = "" }) { Text(Strings.common_clear) }
                    }
                } else null
            )
            IconButton(onClick = { reloadKey++ }) {
                Icon(
                    FeatherIcons.RefreshCw,
                    contentDescription = Strings.market_refresh,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg, vertical = Spacing.xs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.formatted(Strings.market_total_skills, entries.size) +
                    (if (searchQuery.isNotBlank()) {
                        Strings.formatted(Strings.market_matched, filtered.size)
                    } else ""),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { sortByStars = !sortByStars }) {
                Text(
                    text = if (sortByStars) Strings.market_sort_by_stars else Strings.market_sort_by_name,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        notice?.let { current ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.xs),
                shape = RoundedCornerShape(10.dp),
                color = if (current.success) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.errorContainer
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = current.text,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (current.success) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        },
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { notice = null }) { Text(Strings.common_got_it) }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = Spacing.xl),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(26.dp), strokeWidth = 2.5.dp)
            }
            return@Column
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = Spacing.lg,
                end = Spacing.lg,
                top = Spacing.xs,
                bottom = Spacing.xl
            ),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            items(filtered, key = { it.name + "|" + it.downloadUrl }) { entry ->
                MarketEntryCard(
                    entry = entry,
                    installed = entry.name.substringAfterLast('/') in installedNames ||
                        entry.name in installedNames,
                    onInstall = {
                        if (entry.downloadUrl.isBlank()) {
                            notice = MarketInstallNotice(Strings.market_install_no_url, false)
                        } else {
                            scope.launch {
                                notice = MarketInstallNotice(
                                    Strings.formatted(
                                        Strings.market_installing,
                                        entry.displayName.ifBlank { entry.name }
                                    ),
                                    true
                                )
                                when (val result = repository.installFromUrl(entry.downloadUrl)) {
                                    is SkillInstallResult.Success -> {
                                        installedNames = repository.listInstalledSkills().toSet()
                                        notice = MarketInstallNotice(
                                            Strings.formatted(
                                                Strings.market_install_success,
                                                result.skillNames.joinToString()
                                            ),
                                            true
                                        )
                                    }
                                    is SkillInstallResult.Failure -> {
                                        notice = MarketInstallNotice(installFailureText(result), false)
                                    }
                                }
                            }
                        }
                    }
                )
            }

            if (filtered.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = Spacing.xl),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = Strings.market_empty,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (hasMore && searchQuery.isNotBlank()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(Spacing.md),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            enabled = !loadingMore,
                            onClick = {
                                scope.launch {
                                    loadingMore = true
                                    val next = page + 1
                                    val more = repository.searchSkillsOnline(searchQuery, page = next)
                                    if (more.isNotEmpty()) {
                                        page = next
                                        entries = entries + more
                                    }
                                    hasMore = more.isNotEmpty()
                                    loadingMore = false
                                }
                            }
                        ) {
                            Text(
                                if (loadingMore) Strings.market_loading_more else Strings.market_load_more
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun installFailureText(result: SkillInstallResult.Failure): String = when (result.error) {
    SkillInstallError.DOWNLOAD_FAILED -> Strings.formatted(
        Strings.market_install_failed_download,
        result.detail ?: Strings.about_network_error
    )
    SkillInstallError.NO_SKILL_FOUND -> Strings.market_install_failed_no_skill
    SkillInstallError.INSTALL_FAILED -> Strings.formatted(
        Strings.market_install_failed_install,
        result.detail.orEmpty()
    )
}

@Composable
private fun MarketEntryCard(
    entry: MarketEntry,
    installed: Boolean,
    onInstall: () -> Unit,
) {
    var expanded by remember(entry.name) { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.semanticColors.cardSurface)
    ) {
        Column(Modifier.padding(Spacing.md)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = entry.displayName.ifBlank { entry.name }.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(Spacing.md))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = entry.displayName.ifBlank { entry.name },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = if (expanded) 3 else 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (installed) {
                            Spacer(Modifier.width(Spacing.xs))
                            Text(
                                text = Strings.market_installed,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    if (entry.description.isNotBlank()) {
                        Text(
                            text = entry.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = if (expanded) Int.MAX_VALUE else 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    val meta = buildList {
                        if (entry.author.isNotBlank()) add(entry.author)
                        if (entry.stars > 0) add("★ ${entry.stars}")
                        if (entry.version.isNotBlank()) add("v${entry.version}")
                        if (entry.category.isNotBlank()) add(entry.category)
                    }.joinToString(" · ")
                    if (meta.isNotBlank()) {
                        Text(
                            text = meta,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.width(Spacing.sm))
                FilledTonalButton(onClick = onInstall) {
                    Icon(
                        FeatherIcons.Download,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(Spacing.xs))
                    Text(Strings.market_install, style = MaterialTheme.typography.labelMedium)
                }
            }

            Row(
                modifier = Modifier.padding(top = Spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.market_expand_detail,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) FeatherIcons.ChevronUp else FeatherIcons.ChevronDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(14.dp)
                )
            }

            if (expanded) {
                HorizontalDivider(Modifier.padding(vertical = Spacing.sm))
                MarketDetailRow(Strings.skill_detail_name, entry.name)
                if (entry.displayName.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_display_name, entry.displayName)
                }
                if (entry.author.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_author, entry.author)
                }
                if (entry.stars > 0) {
                    MarketDetailRow(Strings.skill_detail_stars, entry.stars.toString())
                }
                if (entry.version.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_version, entry.version)
                }
                if (entry.category.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_category, entry.category)
                }
                if (entry.packageName.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_package, entry.packageName)
                }
                if (entry.tags.isNotEmpty()) {
                    MarketDetailRow(Strings.skill_detail_tags, entry.tags.joinToString("、"))
                }
                if (entry.downloadUrl.isNotBlank()) {
                    MarketDetailRow(Strings.skill_detail_download, entry.downloadUrl)
                }
                Text(
                    text = Strings.skill_install_note,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = Spacing.sm)
                )
            }
        }
    }
}

@Composable
private fun MarketDetailRow(label: String, value: String) {
    if (value.isBlank()) return
    Row(Modifier.fillMaxWidth().heightIn(min = 20.dp).padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(72.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
    }
}
