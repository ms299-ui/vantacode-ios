/*
 * VantaCode Desktop — 团队群聊页：成员依次发言（流式），主持人收敛后给结论。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.teams.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vantacode.core.theme.Radius
import com.vantacode.core.theme.Spacing
import com.vantacode.core.theme.semanticColors
import com.vantacode.core.ui.AppTextField
import com.vantacode.core.ui.Strings
import com.vantacode.core.ui.dialogTextFieldColors
import com.vantacode.feature.settings.presentation.component.settingsPageBackground
import com.vantacode.feature.agent.domain.team.TeamDefinition
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Award
import compose.icons.feathericons.Send
import compose.icons.feathericons.Square
import compose.icons.feathericons.Trash2
import compose.icons.feathericons.X
import compose.icons.feathericons.AlertTriangle
import compose.icons.feathericons.CheckCircle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** 跨路由传团队名：列表页写入，聊天页读取并清空。桌面端 Bundle 参数 API 不稳，不依赖它。 */
object TeamChatRouteTarget {
    @Volatile var teamName: String? = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamChatScreen(
    teamName: String,
    viewModel: TeamsViewModel,
    onNavigateBack: () -> Unit,
) {    val team = remember(teamName) { viewModel.findTeam(teamName) }
    val messages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val streaming by viewModel.streaming.collectAsStateWithLifecycle()
    val isRunning by viewModel.isRunning.collectAsStateWithLifecycle()
    val warnings by viewModel.warnings.collectAsStateWithLifecycle()
    val plan by viewModel.convergedPlan.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(teamName) { viewModel.openChat(teamName) }
    DisposableEffect(teamName) {
        onDispose { viewModel.leaveChat() }
    }

    var input by remember { mutableStateOf("") }
    var showClearConfirm by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // 新消息 / 流式增量时自动滚到底
    val streamChars = streaming.values.sumOf { it.length }
    LaunchedEffect(messages.size, streamChars) {
        val target = messages.size + streaming.size - 1
        if (target >= 0) {
            listState.animateScrollToItem(target, scrollOffset = 0)
        }
    }

    Scaffold(
        containerColor = settingsPageBackground(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(team?.name ?: teamName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        if (team != null) {
                            Text(
                                text = team.members.joinToString(" · ") { it.name },
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            FeatherIcons.ArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showClearConfirm = true }) {
                        Icon(
                            FeatherIcons.Trash2,
                            contentDescription = Strings.team_chat_clear,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = settingsPageBackground(),
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            // 成员头像条
            if (team != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = Spacing.md, vertical = Spacing.xs),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    team.members.forEach { member ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .padding(horizontal = 2.dp, vertical = 2.dp),
                        ) {
                            TeamAvatar(
                                avatar = member.avatar,
                                name = member.name,
                                size = 24.dp,
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = member.name + if (member.isLead) " · ${Strings.team_member_lead}" else "",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                            )
                        }
                    }
                }
            }

            // 消息列表
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (messages.isEmpty() && streaming.isEmpty()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(Spacing.xl),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = Strings.team_chat_empty,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = Strings.formatted(Strings.team_chat_empty_hint, team?.ownerName ?: ""),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = Spacing.xs),
                        )
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                    ) {
                        items(messages, key = { it.id }) { message ->
                            ChatBubble(
                                speaker = message.speaker,
                                avatar = message.avatar,
                                content = message.content,
                                createdAt = message.createdAt,
                                isOwner = message.role == "owner",
                                isLead = team?.members?.any { it.isLead && it.name == message.speaker } == true,
                                streaming = false,
                            )
                        }
                        // 流式发言气泡（落库后会从 streaming 消失）
                        streaming.forEach { (speaker, partial) ->
                            item(key = "streaming-$speaker") {
                                ChatBubble(
                                    speaker = speaker,
                                    avatar = team?.members?.firstOrNull { it.name == speaker }?.avatar ?: "",
                                    content = partial,
                                    createdAt = null,
                                    isOwner = false,
                                    isLead = team?.members?.any { it.isLead && it.name == speaker } == true,
                                    streaming = true,
                                )
                            }
                        }
                    }
                }
            }

            // 主持人收敛方案（可关闭）
            if (plan != null) {
                InfoBanner(
                    icon = FeatherIcons.CheckCircle,
                    tint = MaterialTheme.colorScheme.primary,
                    text = plan!!,
                    onDismiss = { viewModel.dismissPlan() },
                )
            }
            // warnings（可关闭）
            if (warnings.isNotEmpty()) {
                InfoBanner(
                    icon = FeatherIcons.AlertTriangle,
                    tint = MaterialTheme.colorScheme.error,
                    text = warnings.joinToString("\n"),
                    onDismiss = { viewModel.clearWarnings() },
                )
            }
            if (error != null) {
                InfoBanner(
                    icon = FeatherIcons.AlertTriangle,
                    tint = MaterialTheme.colorScheme.error,
                    text = error!!,
                    onDismiss = { viewModel.dismissError() },
                )
            }

            // 输入区
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md, vertical = Spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = Strings.team_chat_input_hint,
                    modifier = Modifier.weight(1f),
                    colors = dialogTextFieldColors(),
                    singleLine = true,
                    enabled = !isRunning,
                )
                Spacer(Modifier.width(Spacing.sm))
                if (isRunning) {
                    IconButton(
                        onClick = { viewModel.stop() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(Radius.sm)),
                    ) {
                        Icon(
                            FeatherIcons.Square,
                            contentDescription = Strings.team_chat_stop,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            val text = input.trim()
                            if (text.isNotEmpty()) {
                                viewModel.send(text)
                                input = ""
                            }
                        },
                        enabled = input.isNotBlank(),
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            FeatherIcons.Send,
                            contentDescription = Strings.team_chat_send,
                            tint = if (input.isNotBlank()) MaterialTheme.colorScheme.primary
                            else MaterialTheme.semanticColors.subtleText,
                        )
                    }
                }
            }
            if (isRunning) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.md)
                        .padding(bottom = Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(12.dp),
                        strokeWidth = 1.5.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = Strings.team_chat_thinking,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }

    // 清聊天记录确认
    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text(Strings.team_chat_clear) },
            text = { Text(Strings.team_chat_clear_confirm) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearChatHistory()
                        showClearConfirm = false
                    }
                ) {
                    Text(Strings.team_chat_clear, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text(Strings.common_cancel) }
            }
        )
    }
}

@Composable
private fun ChatBubble(
    speaker: String,
    avatar: String,
    content: String,
    createdAt: Long?,
    isOwner: Boolean,
    isLead: Boolean,
    streaming: Boolean,
) {
    val bubbleColor = if (isOwner) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.semanticColors.cardSurface
    val contentColor = if (isOwner) MaterialTheme.colorScheme.onPrimaryContainer
    else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOwner) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        if (!isOwner) {
            TeamAvatar(avatar = avatar, name = speaker, size = 32.dp)
            Spacer(Modifier.width(Spacing.sm))
        }
        Surface(
            color = bubbleColor,
            shape = RoundedCornerShape(Radius.lg),
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .clip(RoundedCornerShape(Radius.lg)),
        ) {
            Column(modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.sm)) {
                if (!isOwner) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = speaker,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        if (isLead) {
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                FeatherIcons.Award,
                                contentDescription = Strings.team_member_lead,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(12.dp),
                            )
                        }
                        if (createdAt != null) {
                            Spacer(Modifier.width(Spacing.sm))
                            Text(
                                text = TIME_FORMAT.format(Date(createdAt)),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.semanticColors.subtleText,
                            )
                        }
                        if (streaming) {
                            Spacer(Modifier.width(Spacing.sm))
                            Text(
                                text = "…",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                )
            }
        }
        if (isOwner) {
            Spacer(Modifier.width(Spacing.sm))
            TeamAvatar(avatar = "", name = speaker, size = 32.dp)
        }
    }
}

private val TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.getDefault())

/** 顶部信息条（收敛方案 / 警告 / 错误），右上角可关闭。 */
@Composable
private fun InfoBanner(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    text: String,
    onDismiss: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.xs)
            .clip(RoundedCornerShape(Radius.md))
            .background(MaterialTheme.semanticColors.capsuleSurface)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .heightIn(max = 120.dp),
        )
        IconButton(onClick = onDismiss) {
            Icon(
                FeatherIcons.X,
                contentDescription = "Dismiss",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
