package com.vantacode.feature.backup.presentation

import java.io.File

import com.vantacode.core.platform.DesktopFilePicker

import com.vantacode.core.platform.DesktopContext

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vantacode.core.platform.LocalDesktopContext
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vantacode.core.ui.R
import com.vantacode.core.theme.Radius
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.AppSwitch
import com.vantacode.core.ui.AppTextField
import com.vantacode.core.ui.FloatingTabBar
import com.vantacode.core.ui.FloatingTabItem
import com.vantacode.core.ui.SwipeToDeleteRow
import com.vantacode.core.ui.dialogTextFieldColors
import com.vantacode.feature.backup.domain.BackupOptions
import com.vantacode.feature.backup.domain.RestoreStats
import com.vantacode.feature.backup.domain.WorkspaceBackupMeta
import com.vantacode.feature.backup.domain.remote.BackupTarget
import com.vantacode.feature.backup.domain.remote.BackupTargetType
import com.vantacode.feature.settings.presentation.component.SettingsDivider
import com.vantacode.feature.settings.presentation.component.SettingsEmptyState
import com.vantacode.feature.settings.presentation.component.SettingsGroup
import com.vantacode.feature.settings.presentation.component.SettingsGroupHeader
import com.vantacode.feature.settings.presentation.component.SettingsRow
import com.vantacode.feature.settings.presentation.component.settingsPageBackground
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertCircle
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Download
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Server
import compose.icons.feathericons.Upload
import compose.icons.feathericons.UploadCloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.vantacode.core.ui.Strings
import com.vantacode.core.platform.PlatformToast

/**
 * 备份与还原整页：底部悬浮 tab 分「本地备份 / 网络备份」两页。
 *
 * 本地页 = 导出数据范围配置 + 导出/导入；网络页 = 备份目标列表（卡片）+ 上传/恢复，新增入口在右上角。
 * 与「连接与同步」「Git」同款：Scaffold + TopAppBar + HorizontalPager + FloatingTabBar。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BackupScreen(
    viewModel: BackupViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalDesktopContext.current
    val scope = rememberCoroutineScope()

    val exportOptions by viewModel.exportOptions.collectAsStateWithLifecycle()
    val targets by viewModel.targets.collectAsStateWithLifecycle()
    val networkState by viewModel.networkState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    val localScrollState = rememberScrollState()
    val networkScrollState = rememberScrollState()
    val tabsScrolling by remember {
        derivedStateOf { localScrollState.isScrollInProgress || networkScrollState.isScrollInProgress }
    }

    var password by remember { mutableStateOf("") }
    var pendingAction by remember { mutableStateOf<PendingAction?>(null) }
    var pendingImportUri by remember { mutableStateOf<File?>(null) }
    var pendingExportPassword by remember { mutableStateOf("") }
    var pendingExportOptions by remember { mutableStateOf(BackupOptions()) }

    var editingTarget by remember { mutableStateOf<BackupTarget?>(null) }
    var showTargetSheet by remember { mutableStateOf(false) }
    var targetToDelete by remember { mutableStateOf<BackupTarget?>(null) }
    var uploadTarget by remember { mutableStateOf<BackupTarget?>(null) }
    var uploadPassword by remember { mutableStateOf("") }
    var restoreTarget by remember { mutableStateOf<BackupTarget?>(null) }
    var restoreName by remember { mutableStateOf<String?>(null) }
    var restorePassword by remember { mutableStateOf("") }
    var networkWorkingTextRes by remember { mutableStateOf(R.string.backup_net_loading) }

    // 导出：先选保存位置，再写文件（桌面端没有 SAF，直接 FileOutputStream）
    val startExport: () -> Unit = {
        val pw = pendingExportPassword
        val opts = pendingExportOptions
        val target = DesktopFilePicker.pickSaveFile(
            title = Strings.backup_export_btn,
            defaultName = "aicode-backup-${System.currentTimeMillis()}.tar.gz"
        )
        if (target == null) {
            viewModel.reset()
        } else {
            scope.launch {
                val os = withContext(Dispatchers.IO) { runCatching { target.outputStream() }.getOrNull() }
                if (os != null) {
                    viewModel.export(pw, opts, os)
                } else {
                    PlatformToast.show(Strings.formatted(Strings.backup_write_failed, target.absolutePath), true)
                    viewModel.reset()
                }
            }
        }
    }

    val startImport: () -> Unit = {
        val picked = DesktopFilePicker.pickFile(title = Strings.backup_import_btn)
        if (picked == null) {
            pendingAction = null
        } else {
            pendingImportUri = picked
        }
    }

    Scaffold(
        containerColor = settingsPageBackground(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = settingsPageBackground(),
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = { Text(Strings.settings_backup) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(FeatherIcons.ArrowLeft, contentDescription = Strings.common_back)
                    }
                },
                actions = {
                    if (pagerState.currentPage == 1) {
                        IconButton(onClick = {
                            editingTarget = null
                            showTargetSheet = true
                        }) {
                            Icon(FeatherIcons.Plus, contentDescription = Strings.backup_net_add_target)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page == 0) {
                    LocalBackupTab(
                        scrollState = localScrollState,
                        exportOptions = exportOptions,
                        onUpdateOptions = viewModel::updateExportOptions,
                        working = state is BackupState.Working,
                        onExport = { pendingAction = PendingAction.ExportPassword },
                        onImport = {
                            pendingAction = PendingAction.Import
                            startImport()
                        }
                    )
                } else {
                    NetworkBackupTab(
                        scrollState = networkScrollState,
                        targets = targets,
                        working = state is BackupState.Working || networkState is NetworkBackupState.Working,
                        onEdit = {
                            editingTarget = it
                            showTargetSheet = true
                        },
                        onDelete = { targetToDelete = it },
                        onUpload = {
                            uploadTarget = it
                            uploadPassword = ""
                        },
                        onRestore = {
                            networkWorkingTextRes = R.string.backup_net_loading
                            viewModel.loadRemoteBackups(it)
                        }
                    )
                }
            }

            FloatingTabBar(
                pagerState = pagerState,
                items = listOf(
                    FloatingTabItem(FeatherIcons.Download, Strings.backup_tab_local),
                    FloatingTabItem(FeatherIcons.UploadCloud, Strings.backup_net_section)
                ),
                maskColor = settingsPageBackground(),
                isScrolling = tabsScrolling,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    // 导出：输口令（可留空）
    if (pendingAction == PendingAction.ExportPassword) {
        PasswordDialog(
            title = Strings.backup_set_password,
            subtitle = Strings.backup_password_hint,
            confirmText = Strings.backup_export_btn,
            password = password,
            onPasswordChange = { password = it },
            onConfirm = {
                pendingExportPassword = password
                pendingExportOptions = exportOptions
                password = ""
                pendingAction = null
                startExport()
            },
            onDismiss = {
                password = ""
                pendingAction = null
            }
        )
    }

    // 导入口令弹窗（SAF 选完文件后弹出）
    if (pendingAction == PendingAction.Import && pendingImportUri != null) {
        PasswordDialog(
            title = Strings.backup_password_input,
            subtitle = Strings.backup_password_optional_hint,
            confirmText = Strings.backup_import_btn,
            password = password,
            onPasswordChange = { password = it },
            onConfirm = {
                val pw = password
                val uri = pendingImportUri
                password = ""
                pendingAction = null
                pendingImportUri = null
                if (uri != null) viewModel.import(uri, pw)
            },
            onDismiss = {
                password = ""
                pendingAction = null
                pendingImportUri = null
            }
        )
    }

    // 目标编辑底部弹窗
    if (showTargetSheet) {
        BackupTargetSheet(
            initial = editingTarget,
            onDismiss = { showTargetSheet = false },
            onSave = { viewModel.saveTarget(it); showTargetSheet = false },
            onTest = { target, callback -> viewModel.testTarget(target, callback) }
        )
    }

    // 目标删除确认
    targetToDelete?.let { target ->
        AlertDialog(
            onDismissRequest = { targetToDelete = null },
            title = { Text(Strings.common_delete) },
            text = { Text(Strings.formatted(Strings.backup_net_delete_target_confirm, target.name)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTarget(target.id)
                    targetToDelete = null
                }) {
                    Text(Strings.common_delete, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { targetToDelete = null }) { Text(Strings.common_cancel) } }
        )
    }

    // 上传口令（云端上传必须加密：备份默认含解密后的 API Key / SSH 凭据，明文放到远端风险不可控）
    uploadTarget?.let { target ->
        PasswordDialog(
            title = Strings.backup_set_password,
            subtitle = Strings.backup_net_upload_password_hint,
            confirmText = Strings.backup_net_upload,
            password = uploadPassword,
            onPasswordChange = { uploadPassword = it },
            onConfirm = {
                val t = uploadTarget
                val pw = uploadPassword
                uploadTarget = null
                uploadPassword = ""
                if (t != null && pw.isNotEmpty()) {
                    networkWorkingTextRes = R.string.backup_net_uploading
                    viewModel.uploadToTarget(t, pw)
                }
            },
            onDismiss = { uploadTarget = null; uploadPassword = "" },
            confirmEnabled = uploadPassword.isNotEmpty()
        )
    }

    // 云端恢复口令
    if (restoreTarget != null && restoreName != null) {
        PasswordDialog(
            title = Strings.backup_password_input,
            subtitle = Strings.backup_password_optional_hint,
            confirmText = Strings.backup_import_btn,
            password = restorePassword,
            onPasswordChange = { restorePassword = it },
            onConfirm = {
                val t = restoreTarget
                val n = restoreName
                val pw = restorePassword
                restoreTarget = null
                restoreName = null
                restorePassword = ""
                if (t != null && n != null) viewModel.restoreFromRemote(t, n, pw)
            },
            onDismiss = { restoreTarget = null; restoreName = null; restorePassword = "" }
        )
    }

    // 上传完成提示
    LaunchedEffect(networkState) {
        val done = networkState as? NetworkBackupState.UploadDone
        if (done != null) {
            PlatformToast.show(Strings.formatted(Strings.backup_net_upload_done, done.targetName),
                false
            )
            viewModel.dismissNetworkState()
        }
    }

    if (networkState is NetworkBackupState.Working) {
        NetworkProgressDialog(networkWorkingTextRes)
    }

    val remoteList = networkState as? NetworkBackupState.RemoteList
    if (remoteList != null) {
        RemoteBackupDialog(
            targetName = remoteList.targetName,
            items = remoteList.items,
            onRestore = { item ->
                val target = targets.firstOrNull { it.id == remoteList.targetId }
                if (target != null) {
                    restoreTarget = target
                    restoreName = item.name
                    restorePassword = ""
                } else {
                    viewModel.dismissNetworkState()
                }
            },
            onDelete = { item ->
                val target = targets.firstOrNull { it.id == remoteList.targetId }
                if (target != null) {
                    networkWorkingTextRes = R.string.backup_net_loading
                    viewModel.deleteRemoteBackup(target, item.name)
                } else {
                    viewModel.dismissNetworkState()
                }
            },
            onDismiss = { viewModel.dismissNetworkState() }
        )
    }

    val netError = networkState as? NetworkBackupState.Error
    if (netError != null) {
        ResultDialog(
            title = Strings.backup_operation_failed,
            message = netError.message,
            onDismiss = { viewModel.dismissNetworkState() }
        )
    }

    // 导出完成 → 提示并复位
    LaunchedEffect(state) {
        if (state is BackupState.ExportDone) {
            PlatformToast.show(Strings.backup_exported)
            viewModel.reset()
        }
    }

    if (state is BackupState.Working) {
        ProgressDialog()
    }

    when (val current = state) {
        is BackupState.Error -> ResultDialog(
            title = Strings.backup_operation_failed,
            message = current.message,
            onDismiss = { viewModel.reset() }
        )
        is BackupState.ImportSuccess -> ResultDialog(
            title = Strings.backup_import_done,
            message = buildImportSummary(context, current.stats),
            onDismiss = { viewModel.reset() }
        )
        is BackupState.WorkspaceSelection -> WorkspaceRestoreDialog(
            workspaces = current.workspaces,
            onConfirm = { selected -> viewModel.confirmImportSelection(selected) },
            onDismiss = { viewModel.cancelImportSelection() }
        )
        else -> {}
    }
}

private enum class PendingAction { ExportPassword, Import }

/** 本地备份页：导出数据范围 + 导出/导入操作。 */
@Composable
private fun LocalBackupTab(
    scrollState: ScrollState,
    exportOptions: BackupOptions,
    onUpdateOptions: (BackupOptions) -> Unit,
    working: Boolean,
    onExport: () -> Unit,
    onImport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Spacing.lg)
            .padding(bottom = 96.dp)
    ) {
        SettingsGroupHeader(text = Strings.backup_select_data)
        SettingsGroup {
            SettingsRow(
                title = Strings.common_ai_providers,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.providers,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(providers = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_remote,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.remoteConnections,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(remoteConnections = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_chat_history,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.chatHistory,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(chatHistory = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_mcp,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.mcpServers,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(mcpServers = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_permissions,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.permissionRules,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(permissionRules = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_app_settings,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.appSettings,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(appSettings = it)) }
                    )
                }
            )
            SettingsDivider()
            SettingsRow(
                title = Strings.backup_data_workspace,
                trailing = {
                    AppSwitch(
                        checked = exportOptions.workspaceFiles,
                        onCheckedChange = { onUpdateOptions(exportOptions.copy(workspaceFiles = it)) }
                    )
                }
            )
            WorkspaceBackupHint()
        }

        SettingsGroupHeader(text = Strings.backup_actions)
        SettingsGroup {
            SettingsRow(
                icon = FeatherIcons.Download,
                title = Strings.backup_export_title,
                subtitle = Strings.backup_export_subtitle,
                enabled = !working,
                onClick = onExport
            )
            SettingsDivider()
            SettingsRow(
                icon = FeatherIcons.Upload,
                title = Strings.backup_import_title,
                subtitle = Strings.backup_import_subtitle,
                enabled = !working,
                onClick = onImport
            )
        }
    }
}

/** 网络备份页：备份目标卡片列表（新增在右上角）。 */
@Composable
private fun NetworkBackupTab(
    scrollState: ScrollState,
    targets: List<BackupTarget>,
    working: Boolean,
    onEdit: (BackupTarget) -> Unit,
    onDelete: (BackupTarget) -> Unit,
    onUpload: (BackupTarget) -> Unit,
    onRestore: (BackupTarget) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Spacing.lg)
            .padding(bottom = 96.dp)
    ) {
        if (targets.isEmpty()) {
            // 空态与 MCP / 凭据页统一：图标 + 标题 + 提示，新增入口收在右上角「+」。
            SettingsEmptyState(
                icon = FeatherIcons.UploadCloud,
                title = Strings.backup_net_no_target,
                hint = Strings.backup_net_no_target_hint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp)
            )
        } else {
            SettingsGroupHeader(text = Strings.backup_net_targets)
            SettingsGroup {
                targets.forEachIndexed { index, target ->
                    if (index > 0) SettingsDivider()
                    BackupTargetCard(
                        target = target,
                        working = working,
                        onEdit = { onEdit(target) },
                        onDelete = { onDelete(target) },
                        onUpload = { onUpload(target) },
                        onRestore = { onRestore(target) }
                    )
                }
            }
        }
    }
}

/** 备份目标卡片：图标方块 + 名称（类型徽章）+ 地址副标题 + 编辑箭头，底部上传/恢复操作，左滑删除。 */
@Composable
private fun BackupTargetCard(
    target: BackupTarget,
    working: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onUpload: () -> Unit,
    onRestore: () -> Unit
) {
    SwipeToDeleteRow(onDelete = onDelete, onClick = onEdit) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TargetIconBox(target.type)
                Spacer(Modifier.width(Spacing.md))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = target.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(Modifier.width(Spacing.xs))
                        TargetTypeBadge(target.type)
                    }
                    Text(
                        text = targetSubtitle(target),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = FeatherIcons.ChevronRight,
                        contentDescription = Strings.common_edit,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            SettingsDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.sm),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onUpload,
                    enabled = !working,
                    modifier = Modifier.weight(1f).height(32.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = Strings.backup_net_upload_short,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1
                    )
                }
                OutlinedButton(
                    onClick = onRestore,
                    enabled = !working,
                    modifier = Modifier.weight(1f).height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = Strings.backup_net_remote_restore,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun TargetIconBox(type: BackupTargetType) {
    val icon = when (type) {
        BackupTargetType.WEBDAV -> FeatherIcons.Server
        BackupTargetType.S3 -> FeatherIcons.UploadCloud
    }
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp).align(Alignment.Center)
        )
    }
}

@Composable
private fun TargetTypeBadge(type: BackupTargetType) {
    val (text, color) = when (type) {
        BackupTargetType.WEBDAV -> Strings.backup_net_type_webdav to MaterialTheme.colorScheme.primary
        BackupTargetType.S3 -> Strings.backup_net_type_s3 to MaterialTheme.colorScheme.tertiary
    }
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(Radius.pill))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun WorkspaceBackupHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = Spacing.lg, vertical = Spacing.sm)
    ) {
        Icon(
            imageVector = FeatherIcons.AlertCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(Spacing.xs))
        Text(
            text = Strings.backup_workspace_git_hint,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun WorkspaceRestoreDialog(
    workspaces: List<WorkspaceBackupMeta>,
    onConfirm: (Set<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var checked by remember { mutableStateOf(workspaces.associate { it.name to true }) }
    val anyChecked = checked.values.any { it }
    val allChecked = workspaces.isNotEmpty() && checked.values.all { it }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(Strings.backup_restore_workspaces_title) },
        text = {
            Column {
                Text(
                    text = Strings.backup_restore_workspaces_warning,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(Spacing.md))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = allChecked,
                        onCheckedChange = { v -> checked = workspaces.associate { it.name to v } }
                    )
                    Text(
                        text = Strings.backup_restore_workspaces_select_all,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                workspaces.forEach { ws ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = checked[ws.name] ?: false,
                            onCheckedChange = { v -> checked = checked + (ws.name to v) }
                        )
                        Text(
                            text = ws.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = Strings.formatted(Strings.backup_restore_workspaces_files, ws.fileCount),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(checked.filterValues { it }.keys) },
                enabled = anyChecked
            ) {
                Text(Strings.backup_restore_workspaces_confirm)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(Strings.common_cancel) } }
    )
}

@Composable
private fun PasswordDialog(
    title: String,
    subtitle: String,
    confirmText: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmEnabled: Boolean = true
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(Spacing.sm))
                AppTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = Strings.backup_password_label,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = dialogTextFieldColors()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = confirmEnabled) { Text(confirmText) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(Strings.common_cancel) } }
    )
}

@Composable
private fun ProgressDialog() {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(Strings.backup_processing) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                Text(Strings.backup_processing_data)
            }
        },
        confirmButton = {}
    )
}

@Composable
private fun NetworkProgressDialog(messageRes: Int) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(Strings.backup_processing) },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                Text(stringResource(messageRes))
            }
        },
        confirmButton = {}
    )
}

@Composable
private fun targetSubtitle(target: BackupTarget): String = when (target.type) {
    BackupTargetType.WEBDAV -> "${Strings.backup_net_type_webdav} · ${target.endpoint}"
    BackupTargetType.S3 ->
        "${Strings.backup_net_type_s3} · ${target.endpoint.trimEnd('/')}/${target.bucket}"
}

@Composable
private fun ResultDialog(title: String, message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onDismiss) { Text(Strings.common_got_it) } }
    )
}

private fun buildImportSummary(context: DesktopContext, stats: RestoreStats): String = buildString {
    appendLine(Strings.backup_restored_data)
    if (stats.providers > 0) appendLine(Strings.formatted(Strings.backup_stat_providers, stats.providers))
    if (stats.remoteConnections > 0) appendLine(Strings.formatted(Strings.backup_stat_remote_connections, stats.remoteConnections))
    if (stats.remoteMounts > 0) appendLine(Strings.formatted(Strings.backup_stat_remote_mounts, stats.remoteMounts))
    if (stats.chatSessions > 0) appendLine(Strings.formatted(Strings.backup_stat_chat_sessions, stats.chatSessions))
    if (stats.agentMessages > 0) appendLine(Strings.formatted(Strings.backup_stat_chat_messages, stats.agentMessages))
    if (stats.todoItems > 0) appendLine(Strings.formatted(Strings.backup_stat_todo_items, stats.todoItems))
    if (stats.mcpServers > 0) appendLine(Strings.formatted(Strings.backup_stat_mcp_servers, stats.mcpServers))
    if (stats.globalPermissionRules > 0) appendLine(Strings.formatted(Strings.backup_stat_permission_rules, stats.globalPermissionRules))
    if (stats.workspaceFiles > 0) appendLine(Strings.formatted(Strings.backup_stat_workspace_files, stats.workspaceFiles))
    append(Strings.backup_settings_covered)
}
