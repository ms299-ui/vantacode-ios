/*
 * VantaCode Desktop — 团队编辑器（AdaptiveModalBottomSheet）：名称 / 简介 / 协作方式 / 队长 / 成员 / 自动组队。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.teams.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vantacode.core.ui.AdaptiveModalBottomSheet
import com.vantacode.core.ui.AppTextField
import com.vantacode.core.ui.Strings
import com.vantacode.core.ui.dialogTextFieldColors
import com.vantacode.core.theme.Spacing
import com.vantacode.core.theme.semanticColors
import com.vantacode.di.AppGraph
import com.vantacode.feature.agent.domain.subagent.AgentDefinition
import com.vantacode.feature.agent.domain.team.TeamDefinition
import com.vantacode.feature.agent.domain.team.TeamMember
import com.vantacode.feature.agent.domain.team.TeamForm
import com.vantacode.feature.agent.domain.team.TeamSaveError
import com.vantacode.feature.settings.domain.model.AIProviderConfig
import compose.icons.FeatherIcons
import compose.icons.feathericons.Award
import compose.icons.feathericons.Plus
import compose.icons.feathericons.UserMinus
import compose.icons.feathericons.Zap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val AVATAR_PRESETS = listOf("🐱", "🦊", "🐰", "🦉", "🤖", "🧠", "🚀", "🔥", "🔧", "🌟", "🐙", "🐉")

/** 团队编辑器（创建/编辑）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamEditorSheet(
    existing: TeamDefinition?,
    onDismiss: () -> Unit,
    onSave: suspend (TeamForm, String?) -> TeamSaveError?,
    onAutocompose: suspend (String, String) -> Result<List<TeamMember>>,
) {
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    var name by remember { mutableStateOf(existing?.name ?: "") }
    var description by remember { mutableStateOf(existing?.description ?: "") }
    var ownerName by remember { mutableStateOf(existing?.ownerName ?: TeamDefinition.DEFAULT_OWNER_NAME) }
    var ownerAvatar by remember { mutableStateOf(existing?.ownerAvatar ?: "") }
    var strategy by remember { mutableStateOf(existing?.strategy ?: "") }
    val members = remember {
        mutableStateListOf<TeamMember>().apply { if (existing != null) addAll(existing.members) }
    }
    var extraHint by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }
    var autocomposing by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf<TeamSaveError?>(null) }
    var autocomposeError by remember { mutableStateOf<String?>(null) }
    var memberWarning by remember { mutableStateOf<String?>(null) }

    fun toggleLead(index: Int) {
        members.indices.forEach { i ->
            members[i] = members[i].copy(isLead = i == index && !members[i].isLead)
        }
    }

    fun addMember(member: TeamMember) {
        if (members.size >= TeamDefinition.MAX_MEMBERS) {
            memberWarning = Strings.formatted(Strings.team_error_member_limit, TeamDefinition.MAX_MEMBERS)
            return
        }
        members += member
        memberWarning = null
    }

    AdaptiveModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.lg)
                .padding(bottom = Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            Text(
                text = if (existing == null) Strings.team_editor_new else Strings.team_editor_edit,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            // ── 基本信息 ─────────────────────────
            Text(
                text = Strings.team_editor_basic,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            AppTextField(
                value = name,
                onValueChange = { name = it; saveError = null },
                singleLine = true,
                placeholder = Strings.team_name_hint,
                colors = dialogTextFieldColors(),
            )
            AppTextField(
                value = description,
                onValueChange = { description = it },
                singleLine = false,
                minLines = 2,
                placeholder = Strings.team_description_hint,
                colors = dialogTextFieldColors(),
            )
            AppTextField(
                value = strategy,
                onValueChange = { strategy = it },
                singleLine = false,
                minLines = 2,
                placeholder = Strings.team_strategy_hint,
                colors = dialogTextFieldColors(),
            )

            // ── 队长 ─────────────────────────────
            Text(
                text = Strings.team_editor_owner,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TeamAvatar(
                    avatar = ownerAvatar,
                    name = ownerName.ifBlank { TeamDefinition.DEFAULT_OWNER_NAME },
                    size = 48.dp,
                    modifier = Modifier.clickable {
                        val idx = AVATAR_PRESETS.indexOf(ownerAvatar)
                        ownerAvatar = AVATAR_PRESETS[(idx + 1).floorMod(AVATAR_PRESETS.size)]
                    },
                )
                Spacer(Modifier.width(Spacing.md))
                AppTextField(
                    value = ownerName,
                    onValueChange = { ownerName = it },
                    singleLine = true,
                    placeholder = Strings.team_editor_owner_name,
                    modifier = Modifier.weight(1f),
                    colors = dialogTextFieldColors(),
                )
            }
            Text(
                text = Strings.team_editor_pick_avatar,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.semanticColors.subtleText,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            ) {
                AVATAR_PRESETS.forEach { preset ->
                    val selected = ownerAvatar == preset
                    Surface(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.semanticColors.capsuleSurface,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .clickable { ownerAvatar = preset },
                    ) {
                        Text(
                            text = preset,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(6.dp),
                        )
                    }
                }
            }

            // ── 成员 ─────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = Strings.formatted(Strings.teams_member_count, members.size),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                )
                AddMemberMenu(
                    onAdd = { member -> addMember(member) },
                    excludeAgents = members.map { it.agent }.filter { it.isNotBlank() }.toSet(),
                    excludeProviderModelPairs = members.map { it.providerId to it.model }.filter { it.second.isNotBlank() }.toSet(),
                )
            }
            Text(
                text = Strings.team_editor_members_hint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.semanticColors.subtleText,
            )

            members.forEachIndexed { index, member ->
                MemberCard(
                    member = member,
                    onNameChange = { members[index] = members[index].copy(name = it) },
                    onPromptChange = { members[index] = members[index].copy(prompt = it) },
                    onCycleAvatar = {
                        val idx = AVATAR_PRESETS.indexOf(member.avatar)
                        members[index] = member.copy(avatar = AVATAR_PRESETS[(idx + 1).floorMod(AVATAR_PRESETS.size)])
                    },
                    onToggleLead = { toggleLead(index) },
                    onRemove = { members.removeAt(index) },
                )
            }
            if (members.isEmpty()) {
                Text(
                    text = Strings.team_editor_no_members,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Spacing.md),
                )
            }

            // ── 自动组队 ─────────────────────────
            Text(
                text = Strings.team_editor_autocompose,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            AppTextField(
                value = extraHint,
                onValueChange = { extraHint = it },
                singleLine = false,
                minLines = 1,
                placeholder = Strings.team_editor_autocompose_placeholder,
                colors = dialogTextFieldColors(),
            )
            OutlinedButton(
                onClick = {
                    autocomposing = true
                    autocomposeError = null
                    scope.launch {
                        val result = runCatching { onAutocompose(description, extraHint).getOrThrow() }
                        autocomposing = false
                        result.onSuccess { composed ->
                            members.clear()
                            members.addAll(composed)
                        }.onFailure {
                            autocomposeError = it.message ?: it.javaClass.simpleName
                        }
                    }
                },
                enabled = description.isNotBlank() && !autocomposing && !saving,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (autocomposing) {
                    Text(Strings.team_editor_autocomposing)
                } else {
                    Icon(
                        FeatherIcons.Zap,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(Spacing.sm))
                    Text(Strings.team_editor_autocompose_action)
                }
            }
            if (autocomposeError != null) {
                Text(
                    text = autocomposeError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            // ── 错误提示 ─────────────────────────
            if (saveError != null) {
                Text(
                    text = when (saveError) {
                        TeamSaveError.INVALID_NAME -> Strings.team_error_invalid_name
                        TeamSaveError.NAME_CONFLICT -> Strings.team_error_name_conflict
                        TeamSaveError.MEMBER_LIMIT -> Strings.formatted(Strings.team_error_member_limit, TeamDefinition.MAX_MEMBERS)
                        TeamSaveError.NO_MEMBERS -> Strings.team_error_no_members
                        null -> ""
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (memberWarning != null) {
                Text(
                    text = memberWarning!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            // ── 保存 ─────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                TextButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text(Strings.common_cancel)
                }
                androidx.compose.material3.Button(
                    onClick = {
                        saveError = null
                        saving = true
                        scope.launch {
                            val form = TeamForm(
                                name = name.trim(),
                                description = description.trim(),
                                ownerName = ownerName.trim().ifBlank { TeamDefinition.DEFAULT_OWNER_NAME },
                                ownerAvatar = ownerAvatar.trim(),
                                members = members.map { it.copy(name = it.name.trim(), prompt = it.prompt.trim()) },
                                strategy = strategy.trim(),
                            )
                            val err = onSave(form, existing?.name)
                            saving = false
                            if (err == null) onDismiss() else saveError = err
                        }
                    },
                    enabled = !saving && !autocomposing,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(if (saving) "…" else Strings.common_save)
                }
            }
        }
    }
}

private fun Int.floorMod(modulus: Int): Int = ((this % modulus) + modulus) % modulus

/** 「+ 从子代理/从模型添加」下拉菜单。 */
@Composable
private fun AddMemberMenu(
    onAdd: (TeamMember) -> Unit,
    excludeAgents: Set<String>,
    excludeProviderModelPairs: Set<Pair<String, String>>,
) {
    var expanded by remember { mutableStateOf(false) }
    val agents by produceState<List<AgentDefinition>>(initialValue = emptyList()) {
        withContext(Dispatchers.IO) {
            value = runCatching { AppGraph.agentDefinitionRepository.listEnabled().map { it.definition } }
                .getOrDefault(emptyList())
        }
    }
    val providers by AppGraph.aiProviderRepository.getAllProviders()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Icon(FeatherIcons.Plus, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(Spacing.xs))
            Text(Strings.team_member_add)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 320.dp, min = 96.dp),
        ) {
            Text(
                text = Strings.team_editor_add_agent,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
            )
            val availableAgents = agents.filter { it.name !in excludeAgents }
            if (availableAgents.isEmpty()) {
                Text(
                    text = Strings.team_editor_no_agents,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.semanticColors.subtleText,
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
                )
            } else {
                availableAgents.forEach { agent ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(agent.name, style = MaterialTheme.typography.bodyMedium)
                                if (agent.description.isNotBlank()) {
                                    Text(
                                        agent.description,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.semanticColors.subtleText,
                                        maxLines = 1,
                                    )
                                }
                            }
                        },
                        onClick = {
                            onAdd(
                                TeamMember(
                                    name = agent.name,
                                    agent = agent.name,
                                    avatar = AVATAR_PRESETS[agent.name.length % AVATAR_PRESETS.size],
                                )
                            )
                            expanded = false
                        }
                    )
                }
            }
            HorizontalDivider()
            Text(
                text = Strings.team_editor_add_model,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
            )
            val modelRows = providers.flatMap { p: AIProviderConfig ->
                p.models.map { m -> Triple(p.id, p.name, m) }
                    .filter { (id, _, m) -> (id to m) !in excludeProviderModelPairs }
            }
            if (modelRows.isEmpty()) {
                Text(
                    text = Strings.team_editor_no_models,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.semanticColors.subtleText,
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
                )
            } else {
                modelRows.take(80).forEach { (providerId, providerName, model) ->
                    DropdownMenuItem(
                        text = { Text("$providerName · $model") },
                        onClick = {
                            onAdd(
                                TeamMember(
                                    name = model.take(24),
                                    model = model,
                                    providerId = providerId,
                                    avatar = AVATAR_PRESETS[model.length % AVATAR_PRESETS.size],
                                )
                            )
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/** 单个成员卡：头像（点击换预设）+ 名称 + 引导词 + 队长徽标 + 移除。 */
@Composable
private fun MemberCard(
    member: TeamMember,
    onNameChange: (String) -> Unit,
    onPromptChange: (String) -> Unit,
    onCycleAvatar: () -> Unit,
    onToggleLead: () -> Unit,
    onRemove: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TeamAvatar(
                avatar = member.avatar,
                name = member.name.ifBlank { "?" },
                size = 44.dp,
                modifier = Modifier.clickable(onClick = onCycleAvatar),
            )
            Spacer(Modifier.width(Spacing.md))
            AppTextField(
                value = member.name,
                onValueChange = onNameChange,
                singleLine = true,
                placeholder = Strings.team_editor_rename_member,
                modifier = Modifier.weight(1f),
                colors = dialogTextFieldColors(),
            )
            IconButton(onClick = onToggleLead) {
                Icon(
                    FeatherIcons.Award,
                    contentDescription = Strings.team_member_lead,
                    tint = if (member.isLead) MaterialTheme.colorScheme.primary
                    else MaterialTheme.semanticColors.subtleText,
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    FeatherIcons.UserMinus,
                    contentDescription = Strings.team_editor_remove_member,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(56.dp))
            Text(
                text = member.subtitle + if (member.isLead) " · ${Strings.team_member_lead}" else "",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.semanticColors.subtleText,
            )
        }
        AppTextField(
            value = member.prompt,
            onValueChange = onPromptChange,
            singleLine = false,
            minLines = 1,
            placeholder = Strings.team_member_prompt_hint,
            colors = dialogTextFieldColors(),
        )
    }
}
