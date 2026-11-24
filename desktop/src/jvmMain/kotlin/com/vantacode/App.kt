/*
 * VantaCode Desktop — root composable. Mirrors Android's MainActivity: agreement gate,
 * onboarding overlay, drawer/nav host, background-image watermark, toast host, global
 * credential dialog. Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode

import com.vantacode.core.ui.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vantacode.core.theme.AIEditorTheme
import com.vantacode.core.theme.AppThemePreset
import com.vantacode.core.ui.ImageSource
import com.vantacode.core.ui.ImageViewerHost
import com.vantacode.core.ui.ImageViewerRequest
import com.vantacode.core.ui.LocalImageViewer
import com.vantacode.core.ui.PAGE_MOTION_MS
import com.vantacode.core.ui.VerticalSplitHandle
import com.vantacode.core.ui.drawerWidth
import com.vantacode.core.ui.isExpandedWidth
import com.vantacode.core.ui.pageEnter
import com.vantacode.core.ui.pageExit
import com.vantacode.core.ui.rememberImageViewerState
import com.vantacode.core.ui.rememberViewerDecodeSpec
import com.vantacode.core.platform.DesktopFilePicker
import com.vantacode.core.platform.LocalDesktopContext
import com.vantacode.core.platform.PlatformToast
import com.vantacode.core.ui.Strings
import com.vantacode.core.util.FileLogger
import androidx.compose.ui.Alignment
import com.vantacode.di.AppGraph
import com.vantacode.feature.agent.presentation.AIAgentViewModel
import com.vantacode.feature.agent.presentation.component.AIChatPanel
import com.vantacode.feature.agent.presentation.component.ChatDrawerContent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.input.pointer.pointerInput
import org.jetbrains.compose.resources.decodeToImageBitmap
import com.vantacode.core.theme.Spacing
import com.vantacode.core.theme.semanticColors
import compose.icons.FeatherIcons
import compose.icons.feathericons.Minus
import compose.icons.feathericons.Minimize2
import compose.icons.feathericons.Square
import compose.icons.feathericons.X
import com.vantacode.feature.agent.presentation.component.chatImageLoader
import com.vantacode.feature.settings.presentation.component.settingsPageBackground
import com.vantacode.feature.agreement.presentation.AgreementGate
import com.vantacode.feature.git.presentation.GitViewModel
import com.vantacode.feature.git.presentation.component.GitScreen
import com.vantacode.feature.settings.data.repository.AppThemeMode
import com.vantacode.feature.settings.data.repository.BackgroundSettingsRepository
import com.vantacode.feature.settings.data.repository.BackgroundKind
import com.vantacode.feature.settings.presentation.SettingsViewModel
import com.vantacode.feature.settings.presentation.component.SettingsScreen
import com.vantacode.feature.settings.presentation.component.decodeBackgroundBitmap
import com.vantacode.feature.workspace.presentation.WorkspaceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

@Composable
fun App(window: java.awt.Frame? = null, onRequestClose: () -> Unit = { kotlin.system.exitProcess(0) }) {
    val themeMode by AppGraph.themeSettingsRepository.themeModeFlow
        .collectAsStateWithLifecycle(initialValue = AppThemeMode.AUTO)
    val themePresetId by AppGraph.themeSettingsRepository.themePresetIdFlow
        .collectAsStateWithLifecycle(initialValue = null)
    val systemDarkTheme = isSystemInDarkTheme()
    val darkTheme = when (themeMode) {
        AppThemeMode.AUTO -> systemDarkTheme
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
    }

    // 全局语言同步：语言偏好 → Strings._current，避免 Compose snapshot 问题。
    val languageTag by AppGraph.languageSettingsRepository.languageFlow
        .collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(languageTag) {
        val tag = languageTag
        com.vantacode.core.ui.Strings._current.set(when {
            tag == null ->
                if (java.util.Locale.getDefault().language.startsWith("zh", ignoreCase = true)) "zh" else "en"
            tag.startsWith("en", ignoreCase = true) -> "en"
            else -> "zh"
        })
    }

    val agreementRepository = remember { AppGraph.agreementRepository }
    val onboardingRepository = remember { AppGraph.onboardingRepository }
    val snackbarHostState = remember { SnackbarHostState() }

    // 全局 Toast 监听 → Snackbar（底部悬浮、短消息）。
    LaunchedEffect(Unit) {
        PlatformToast.events.collect { message ->
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }

    AIEditorTheme(
        darkTheme = darkTheme,
        preset = AppThemePreset.findById(themePresetId),
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
            DesktopTitleBar(window = window, onRequestClose = onRequestClose)
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {

            Box(modifier = Modifier.fillMaxSize()) {
                AgreementGate(repository = agreementRepository) { agreementAccepted ->
                    AppNavigation(
                        onboardingRepository = onboardingRepository,
                        agreementAccepted = agreementAccepted
                    )
                    if (agreementAccepted) {
                        // 全局凭据弹窗：覆盖所有页面（git 缺凭据时弹出回填）。
                        com.vantacode.feature.credentials.presentation.component.GlobalCredentialDialogHost(
                            bridge = AppGraph.credentialRequestBridge
                        )
                    }
                }

                // Toast Snackbar：所有页面共享
                SnackbarHost(
                    modifier = Modifier.fillMaxSize(),
                    hostState = snackbarHostState
                )
            }
            }
        }
    }
}
}


/** 大屏右栏默认占宽比，以及拖拽分栏的上下限——两边都至少留 30% 宽度。 */
private const val DEFAULT_PANE_SPLIT = 0.5f
private const val MIN_PANE_SPLIT = 0.3f
private const val MAX_PANE_SPLIT = 0.7f

/**
 * 根导航容器。
 *
 * [ModalNavigationDrawer] 放在 [NavHost] **外面**，使 Drawer 的生命周期独立于页面切换。
 *
 * ViewModel 提升到这一层创建，以便 Drawer 内容和 AIChatPanel 共享同一实例。
 */
@Composable
fun AppNavigation(
    onboardingRepository: com.vantacode.feature.onboarding.data.OnboardingRepository,
    agreementAccepted: Boolean
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 用于判断当前路由：仅在聊天页允许 Drawer 手势。
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // 记录当前路由到全局，崩溃报告用它定位崩溃页面。
    LaunchedEffect(currentRoute) {
        DesktopApp.currentRoute = currentRoute
    }

    // Activity 级别的 ViewModel——Drawer 和 AIChatPanel 共享同一个实例。
    val agentViewModel: AIAgentViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
    val settingsViewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
    val workspaceViewModel: WorkspaceViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)

    // ── 首次启动引导 ──
    val onboardingStateHolder = remember { com.vantacode.feature.onboarding.domain.OnboardingStateHolder() }
    val onboardingUiState by onboardingStateHolder.state.collectAsStateWithLifecycle()
    val onboardingRegistry = remember { com.vantacode.feature.onboarding.presentation.OnboardingTargetRegistry() }
    val onboardingScope = rememberCoroutineScope()
    val onboardingCoordinator = remember {
        com.vantacode.feature.onboarding.domain.OnboardingCoordinator(
            stateHolder = onboardingStateHolder,
            onPersist = { status ->
                onboardingRepository.markStatus(status)
            },
            scope = onboardingScope
        )
    }
    // 启动判断：IN_PROGRESS 则激活引导。
    LaunchedEffect(agreementAccepted) {
        if (!agreementAccepted) return@LaunchedEffect
        val status = onboardingRepository.status()
        onboardingCoordinator.initialize(status ?: com.vantacode.feature.onboarding.data.OnboardingStatus.IN_PROGRESS)
    }

    // 侧边栏打开时，系统返回键先收起侧边栏。
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    // 侧边栏需要的数据。
    val currentWorkspace by workspaceViewModel.current.collectAsStateWithLifecycle()
    LaunchedEffect(currentWorkspace) {
        val path = currentWorkspace?.path ?: return@LaunchedEffect
        agentViewModel.setWorkspace(path)
    }

    val sessions by agentViewModel.sessions.collectAsStateWithLifecycle()
    val currentSessionId by agentViewModel.currentSessionId.collectAsStateWithLifecycle()
    val agentStates by agentViewModel.agentStates.collectAsStateWithLifecycle()
    val awaitingPermissionSessionIds by agentViewModel.awaitingPermissionSessionIds.collectAsStateWithLifecycle()
    val subSessionsByParent by agentViewModel.subSessionsByParent.collectAsStateWithLifecycle()
    val expandedPaths by agentViewModel.expandedPaths.collectAsStateWithLifecycle()
    val expandingPath by agentViewModel.expandingPath.collectAsStateWithLifecycle()
    val fileOpPaths by agentViewModel.fileOpPaths.collectAsStateWithLifecycle()
    val browseState by agentViewModel.browseState.collectAsStateWithLifecycle()
    val browseClipboard by agentViewModel.browseClipboard.collectAsStateWithLifecycle()
    val pasteConflict by agentViewModel.pasteConflict.collectAsStateWithLifecycle()
    val chatSearchQuery by agentViewModel.chatSearchQuery.collectAsStateWithLifecycle()
    val chatSearchState by agentViewModel.chatSearchState.collectAsStateWithLifecycle()

    // ── 导出会话：桌面文件对话框选择保存位置。 ──
    val context = LocalDesktopContext.current
    val exportSession: (String) -> Unit = { sessionId ->
        scope.launch {
            val defaultName = "aicode-session-$sessionId.tar.gz"
            val file = DesktopFilePicker.pickSaveFile(parent = null, title = "导出会话", defaultName = defaultName, filter = "*.tar.gz")
            if (file != null) {
                withContext(Dispatchers.IO) {
                    runCatching {
                        FileOutputStream(file).use { os ->
                            agentViewModel.exportSession(sessionId, os) { success ->
                                PlatformToast.show(if (success) Strings.chat_export_session_done else Strings.chat_export_session_failed, !success)
                            }
                        }
                    }.onFailure { e ->
                        FileLogger.e("AppNavigation", "导出会话失败", e)
                        PlatformToast.show(Strings.chat_export_session_failed, true)
                    }
                }
            }
        }
    }

    // ── 大屏（平板横屏及以上）布局 ──
    val expanded = isExpandedWidth()
    val permanentDrawer = expanded

    val sidebarVisible = if (permanentDrawer) currentRoute == "chat" else drawerState.isOpen
    LaunchedEffect(sidebarVisible) {
        if (sidebarVisible) agentViewModel.refreshBrowse()
    }

    // 右栏工作台：大屏下编辑器 / 终端 / Git 与聊天并排，窄窗仍走全屏路由。
    var paneKind by rememberSaveable { mutableStateOf(WorkbenchPaneKind.NONE) }
    var editorPath by rememberSaveable { mutableStateOf<String?>(null) }
    var sidebarCollapsed by rememberSaveable { mutableStateOf(false) }
    var paneSplit by rememberSaveable { mutableFloatStateOf(DEFAULT_PANE_SPLIT) }

    // 打开文件：文本类进内置编辑器；明显的二进制/文档类型交系统默认程序。
    val openFile: (String, Int, Boolean) -> Unit = { filePath, _, _ ->
        scope.launch { drawerState.close() }
        val name = filePath.trimEnd('/').substringAfterLast('/')
        val ext = name.substringAfterLast('.', "").lowercase()
        if (ext in SYSTEM_OPEN_EXTS) {
            val target = com.vantacode.di.AppGraph.workspacePathMapper.toHostFile(filePath)
            val opened = com.vantacode.core.platform.DesktopActions.open(target)
            if (!opened) {
                PlatformToast.show(Strings.chat_open_file_no_app, true)
            }
        } else {
            editorPath = filePath
            if (expanded) {
                paneKind = WorkbenchPaneKind.EDITOR
            } else {
                navController.navigate("editor")
            }
        }
    }

    // 文件树里点文件：图片进内置全屏查看器，其余进编辑器。
    val drawerImageViewerState = rememberImageViewerState()
    val drawerDecodeSpec = rememberViewerDecodeSpec()
    val drawerImageLoad = remember(agentViewModel.fileAccess, drawerDecodeSpec) {
        chatImageLoader(agentViewModel.fileAccess, drawerDecodeSpec.maxEdge, drawerDecodeSpec.maxPixels)
    }
    val openTreeEntry: (String) -> Unit = { path ->
        val name = path.trimEnd('/').substringAfterLast('/')
        val ext = name.substringAfterLast('.', "").lowercase()
        when {
            ext in PREVIEWABLE_IMAGE_EXTS -> drawerImageViewerState.show(
                ImageViewerRequest(listOf(ImageSource.ContainerPath(path)), name)
            )
            else -> openFile(path, 0, true)
        }
    }

    // Git：大屏在右栏内开合切换，窄窗跳全屏页。
    val openWorkbench: (WorkbenchPaneKind) -> Unit = { target ->
        if (expanded) {
            paneKind = if (paneKind == target) WorkbenchPaneKind.NONE else target
        } else {
            val route = when (target) {
                WorkbenchPaneKind.GIT -> "git"
                else -> "chat"
            }
            navController.navigate(route)
        }
    }

    // 侧栏内容：modal 抽屉与大屏常驻栏共用同一份，不在两处重复几十行参数。
    val drawerBody: @Composable () -> Unit = {
        ChatDrawerContent(
            sessions = sessions,
            currentSessionId = currentSessionId,
            agentStates = agentStates,
            awaitingPermissionSessionIds = awaitingPermissionSessionIds,
            subSessionsByParent = subSessionsByParent,
            browseState = browseState,
            onCollapseSidebar = { sidebarCollapsed = true },
            expandedPaths = expandedPaths,
            expandingPath = expandingPath,
            fileOpPaths = fileOpPaths,
            clipboard = browseClipboard,
            pasteConflict = pasteConflict,
            onToggleExpand = { agentViewModel.toggleExpand(it) },
            onOpenFile = openTreeEntry,
            onRefreshBrowse = { agentViewModel.refreshBrowse() },
            onCreateFile = { parent, name ->
                agentViewModel.createBrowseFile(parent, name) { ok ->
                    if (!ok) PlatformToast.show(Strings.file_browser_create_failed)
                }
            },
            onCreateFolder = { parent, name ->
                agentViewModel.createBrowseFolder(parent, name) { ok ->
                    if (!ok) PlatformToast.show(Strings.file_browser_create_failed)
                }
            },
            onRenameEntry = { path, newName ->
                agentViewModel.renameBrowseEntry(path, newName) { ok ->
                    if (!ok) PlatformToast.show(Strings.file_browser_rename_failed)
                }
            },
            onDeleteEntry = { path ->
                agentViewModel.deleteBrowseEntry(path) { ok ->
                    if (!ok) PlatformToast.show(Strings.file_browser_delete_failed)
                }
            },
            onCopyEntry = { path, name ->
                agentViewModel.copyBrowseEntry(path, name)
            },
            onCutEntry = { path, name ->
                agentViewModel.cutBrowseEntry(path, name)
            },
            onPasteEntry = { targetDir, onResult ->
                agentViewModel.pasteBrowseEntry(targetDir, onResult)
            },
            onClearClipboard = { agentViewModel.clearBrowseClipboard() },
            onSetAsWorkspace = { path ->
                workspaceViewModel.addExternalWorkspace(path) { created ->
                    if (created != null) {
                        workspaceViewModel.selectWorkspace(created.name)
                        PlatformToast.show(Strings.formatted(Strings.workspace_external_added, created.name))
                    } else {
                        PlatformToast.show(Strings.workspace_external_invalid)
                    }
                }
            },
            onPasteOverwrite = { agentViewModel.pasteBrowseEntryOverwrite() },
            onCancelPasteOverwrite = { agentViewModel.clearPasteConflict() },
            onSelect = {
                agentViewModel.selectSession(it.id)
                if (!permanentDrawer) scope.launch { drawerState.close() }
            },
            onDelete = { agentViewModel.deleteSession(it.id) },
            onDeleteSessions = { agentViewModel.deleteSessions(it) },
            onRename = { session, title -> agentViewModel.renameSession(session.id, title) },
            onTogglePin = { agentViewModel.togglePinSession(it.id) },
            onExport = { session -> exportSession(session.id) },
            onNavigateToSettings = {
                navController.navigate("settings")
                if (!permanentDrawer) scope.launch { drawerState.snapTo(DrawerValue.Closed) }
            },
            searchQuery = chatSearchQuery,
            searchState = chatSearchState,
            onSearchQueryChange = { agentViewModel.updateChatSearchQuery(it) },
            onClearSearch = { agentViewModel.clearChatSearch() },
            onOpenSearchHit = { hit ->
                agentViewModel.openChatSearchHit(hit)
                if (!permanentDrawer) scope.launch { drawerState.close() }
            }
        )
    }

    // 页面主体（NavHost）：大屏放进 Row 的右侧，窄窗放进 modal 抽屉容器里。
    val navBody: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = "chat",
            modifier = Modifier.fillMaxSize().clipToBounds(),
            enterTransition = { pageEnter(forward = true) },
            exitTransition = { pageExit(forward = true) },
            popEnterTransition = { pageEnter(forward = false) },
            popExitTransition = { pageExit(forward = false) }
        ) {
            composable("chat") {
                val defaultUriHandler = androidx.compose.ui.platform.LocalUriHandler.current
                val fileUriHandler = remember(defaultUriHandler, expanded) {
                    FileAwareUriHandler(defaultUriHandler) { filePath, line ->
                        openFile(filePath, line, false)
                    }
                }
                val paneOpen = expanded && paneKind != WorkbenchPaneKind.NONE
                var rowWidthPx by remember { mutableIntStateOf(0) }
                var leftPaneWidthPx by remember { mutableIntStateOf(0) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .onSizeChanged { rowWidthPx = it.width }
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .weight(if (paneOpen) paneSplit else 1f)
                                .onSizeChanged { leftPaneWidthPx = it.width }
                        ) {
                            CompositionLocalProvider(
                                androidx.compose.ui.platform.LocalUriHandler provides fileUriHandler
                            ) {
                                AIChatPanel(
                                    viewModel = agentViewModel,
                                    settingsViewModel = settingsViewModel,
                                    workspaceViewModel = workspaceViewModel,
                                    onOpenDrawer = { sidebarCollapsed = false; scope.launch { drawerState.open() } },
                                    showMenuButton = !permanentDrawer || sidebarCollapsed,
                                                    onNavigateToGit = { openWorkbench(WorkbenchPaneKind.GIT) },
                                                     onNavigateToTerminal = { navController.navigate("terminal") },
                                                      onNavigateToTeams = { navController.navigate("teams") },
                                                    gitActive = paneOpen && paneKind == WorkbenchPaneKind.GIT,
                                                     teamsActive = currentRoute == "teams",
                                    onboardingStep = if (onboardingUiState.active) onboardingUiState.step else null,
                                    onSelectModelInOnboarding = {
                                        if (onboardingUiState.active && onboardingUiState.step == com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_CHOOSE_MODEL) {
                                            onboardingCoordinator.nextStep()
                                        }
                                    },
                                    onDismissModelSheetInOnboarding = {
                                        if (onboardingUiState.active && onboardingUiState.step == com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_CHOOSE_MODEL) {
                                            onboardingCoordinator.goToStep(com.vantacode.feature.onboarding.domain.OnboardingStep.OPEN_MODEL_PICKER)
                                        }
                                    }
                                )
                            }
                        }
                        if (paneOpen) {
                            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            WorkbenchPaneContent(
                                kind = paneKind,
                                onClose = { paneKind = WorkbenchPaneKind.NONE },
                                onOpenFile = { path -> openFile(path, 0, false) },
                                editorPath = editorPath,
                                modifier = Modifier.weight(1f - paneSplit)
                            )
                        }
                    }
                    if (paneOpen && leftPaneWidthPx > 0) {
                        VerticalSplitHandle(
                            anchorX = leftPaneWidthPx,
                            onDragDelta = { dx ->
                                if (rowWidthPx > 0) {
                                    paneSplit = (paneSplit + dx / rowWidthPx)
                                        .coerceIn(MIN_PANE_SPLIT, MAX_PANE_SPLIT)
                                }
                            }
                        )
                    }
                }
            }
            composable("settings") {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                        if (!expanded) scope.launch { drawerState.open() }
                    },
                    onStopAllAndCloseTerminal = { agentViewModel.stopAllAndCloseTerminal() },
                    onRerunOnboarding = {
                        scope.launch {
                            onboardingRepository.resetToInProgress()
                            onboardingCoordinator.resetAndStart()
                            navController.navigate("chat") {
                                popUpTo("chat") { inclusive = false }
                            }
                        }
                    },
                    onboardingStep = onboardingUiState.step.takeIf { onboardingUiState.active },
                    onOnboardingModelAdded = {
                        if (onboardingUiState.active && onboardingUiState.step == com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_FETCH_DIALOG) {
                            navController.popBackStack()
                            onboardingCoordinator.nextStep()
                        }
                    },
                    onOnboardingDismissFetchDialog = {
                        if (onboardingUiState.active && onboardingUiState.step == com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_FETCH_DIALOG) {
                            onboardingCoordinator.goToStep(com.vantacode.feature.onboarding.domain.OnboardingStep.PROVIDER_FETCH_MODELS)
                        }
                    }
                )
            }
            composable("git") {
                val gitViewModel: GitViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
                GitScreen(
                    viewModel = gitViewModel,
                    onNavigateToCredentials = { navController.navigate("credentials") },
                    onNavigateBack = { navController.popBackStack() },
                    onOpenFile = { path -> openFile(path, 0, false) }
                )
            }
            composable("editor") {
                com.vantacode.feature.editor.presentation.component.CodeEditorScreen(
                    path = editorPath.orEmpty(),
                    fileAccess = AppGraph.fileAccess,
                    onClose = { navController.popBackStack() }
                )
            }
            composable("credentials") {
                val credentialViewModel: com.vantacode.feature.credentials.presentation.CredentialViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
                com.vantacode.feature.credentials.presentation.component.CredentialScreen(
                    viewModel = credentialViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("teams") {
                val teamsViewModel: com.vantacode.feature.teams.presentation.TeamsViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
                com.vantacode.feature.teams.presentation.TeamsScreen(
                    viewModel = teamsViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onOpenChat = { teamName ->
                        com.vantacode.feature.teams.presentation.TeamChatRouteTarget.teamName = teamName
                        navController.navigate("teams/chat")
                    },
                )
            }
            composable("teams/chat") {
                val teamName = com.vantacode.feature.teams.presentation.TeamChatRouteTarget.teamName
                LaunchedEffect(Unit) {
                    com.vantacode.feature.teams.presentation.TeamChatRouteTarget.teamName = null
                }
                if (teamName.isNullOrBlank()) {
                    navController.popBackStack()
                } else {
                    val teamsViewModel: com.vantacode.feature.teams.presentation.TeamsViewModel =
                        androidx.lifecycle.viewmodel.compose.viewModel(factory = AppGraph.viewModelFactory)
                    com.vantacode.feature.teams.presentation.TeamChatScreen(
                        teamName = teamName,
                        viewModel = teamsViewModel,
                        onNavigateBack = { navController.popBackStack() },
                    )
                }
            }
            composable("terminal") {
                com.vantacode.feature.terminal.presentation.TerminalScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }

    CompositionLocalProvider(
        com.vantacode.feature.onboarding.presentation.LocalOnboardingTargetRegistry provides onboardingRegistry,
        LocalImageViewer provides drawerImageViewerState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 文件树里点开的图片走这个查看器；聊天面板自带一个，两层互不影响。
            ImageViewerHost(state = drawerImageViewerState, load = drawerImageLoad)
            if (expanded) {
                // 大屏不套 ModalNavigationDrawer：侧栏常驻在左。
                val sidebarWidth by animateDpAsState(
                    targetValue = if (currentRoute == "chat" && !sidebarCollapsed) drawerWidth() else 0.dp,
                    animationSpec = tween(durationMillis = PAGE_MOTION_MS),
                    label = "sidebar-width"
                )
                val sidebarStateHolder = rememberSaveableStateHolder()
                Row(modifier = Modifier.fillMaxSize()) {
                    if (sidebarWidth > 0.dp) {
                        Box(
                            modifier = Modifier
                                .width(sidebarWidth)
                                .fillMaxHeight()
                                .clipToBounds()
                        ) {
                            // 内容按完整宽度渲染再裁剪：收起过程中不去重排侧栏内部布局。
                            Box(
                                modifier = Modifier
                                    .width(drawerWidth())
                                    .fillMaxHeight()
                            ) {
                                sidebarStateHolder.SaveableStateProvider("chat-sidebar") {
                                    drawerBody()
                                }
                            }
                        }
                        VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        navBody()
                    }
                }
            } else {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    // 仅在聊天页启用手势滑出；其他页面禁止（但已打开时始终可关闭）。
                    gesturesEnabled = currentRoute == "chat" || drawerState.isOpen,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerShape = RectangleShape,
                            drawerContainerColor = settingsPageBackground(),
                            drawerTonalElevation = 0.dp,
                            modifier = Modifier.width(drawerWidth())
                        ) {
                            drawerBody()
                        }
                    }
                ) {
                    navBody()
                }
            }

            BackgroundWatermark(
                modifier = Modifier.fillMaxSize()
            )

            // 首次启动引导覆层（覆盖所有路由之上，全程常驻顶层）。
            if (agreementAccepted && onboardingUiState.active) {
                com.vantacode.feature.onboarding.presentation.OnboardingOverlay(
                    stateHolder = onboardingStateHolder,
                    coordinator = onboardingCoordinator,
                    registry = onboardingRegistry,
                    onNextStep = {
                        val step = onboardingStateHolder.state.value.step
                        when (step) {
                            com.vantacode.feature.onboarding.domain.OnboardingStep.OPEN_SIDEBAR -> {
                                scope.launch { drawerState.open() }
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.ENTER_SETTINGS -> {
                                navController.navigate("settings")
                                scope.launch { drawerState.snapTo(DrawerValue.Closed) }
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.CONFIG_PROVIDER -> {
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.PROVIDER_ADD -> {
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.PROVIDER_CONFIG_INFO -> {
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.PROVIDER_FETCH_MODELS -> {
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_FETCH_DIALOG -> {
                                val current = settingsViewModel.providers.value.firstOrNull()
                                if (current != null && current.models.isEmpty()) {
                                    settingsViewModel.saveProvider(current.copy(models = listOf("deepseek-v4-flash")))
                                }
                                navController.popBackStack()
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.OPEN_MODEL_PICKER -> {
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.SIMULATE_CHOOSE_MODEL -> {
                                val current = settingsViewModel.providers.value.firstOrNull()
                                if (current != null && current.models.isNotEmpty()) {
                                    agentViewModel.setSessionProviderModel(current.id, current.effectiveModel.ifEmpty { current.models.first() })
                                }
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.SEND_MESSAGE -> {
                                onboardingCoordinator.complete()
                            }
                        }
                    },
                    onTargetClick = {
                        val step = onboardingStateHolder.state.value.step
                        when (step) {
                            com.vantacode.feature.onboarding.domain.OnboardingStep.OPEN_SIDEBAR -> {
                                scope.launch { drawerState.open() }
                                onboardingCoordinator.nextStep()
                            }
                            com.vantacode.feature.onboarding.domain.OnboardingStep.ENTER_SETTINGS -> {
                                navController.navigate("settings")
                                scope.launch { drawerState.snapTo(DrawerValue.Closed) }
                                onboardingCoordinator.nextStep()
                            }
                            else -> onboardingCoordinator.nextStep()
                        }
                    }
                )
            }
        }
    }
}

/** 带 `//` 的层级式 scheme：`http://`、`https://`、`file://` 等。 */
private val HIERARCHICAL_SCHEME = Regex("^([a-zA-Z][a-zA-Z0-9+.-]*)://")

/** 不带 `//` 的网址型 scheme 白名单（mailto:x 这类）。 */
private val OPAQUE_URL_SCHEME = Regex(
    "^(mailto|tel|sms|smsto|geo|data|market|intent|about|javascript):",
    RegexOption.IGNORE_CASE
)

/** 文件树里点击即在内置查看器预览的图片格式。 */
private val PREVIEWABLE_IMAGE_EXTS = setOf("png", "jpg", "jpeg", "gif", "webp", "bmp")

/** 这些类型不适合塞进文本编辑器，直接交给系统默认程序打开。 */
private val SYSTEM_OPEN_EXTS = setOf(
    "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "odt", "ods",
    "zip", "rar", "7z", "tar", "gz", "jar", "exe", "dll", "so", "dylib",
    "mp3", "mp4", "avi", "mov", "wav", "flac", "ttf", "otf", "woff", "woff2"
)

private val TRAILING_LINE = Regex("^(.*):(\\d+)$")

/**
 * 聊天区 Markdown 链接处理：把「容器文件路径」链接拦下来交给 [onOpenFile] 打开编辑器，
 * 其余（http/https/mailto 等网址）委托给系统默认 [delegate]（浏览器）。
 */
private class FileAwareUriHandler(
    private val delegate: androidx.compose.ui.platform.UriHandler,
    private val onOpenFile: (path: String, line: Int) -> Unit
) : androidx.compose.ui.platform.UriHandler {
    override fun openUri(uri: String) {
        val filePath = asChatFilePath(uri)
        if (filePath == null) {
            delegate.openUri(uri)
            return
        }
        val (path, line) = splitPathAndLine(filePath)
        onOpenFile(path, line)
    }

    private fun asChatFilePath(uri: String): String? {
        val trimmed = uri.trim()
        if (OPAQUE_URL_SCHEME.matches(trimmed)) return null
        if (HIERARCHICAL_SCHEME.matches(trimmed)) {
            if (trimmed.startsWith("file://")) {
                val path = trimmed.removePrefix("file://").replace('/', java.io.File.separatorChar)
                return path.ifBlank { null }
            }
            return null
        }
        return trimmed.ifBlank { null }
    }

    private fun splitPathAndLine(filePath: String): Pair<String, Int> {
        val m = TRAILING_LINE.matchEntire(filePath)
        return if (m != null) m.groupValues[1] to (m.groupValues[2].toIntOrNull() ?: 0)
        else filePath to 0
    }
}

/** 自绘标题栏：语义页面背景色 + 应用图标 + 最小化/最大化/关闭；拖拽空白区可移动窗口。 */
@Composable
private fun DesktopTitleBar(window: java.awt.Frame?, onRequestClose: () -> Unit) {
    var isMaximized by remember(window?.extendedState) {
        mutableStateOf(window?.extendedState == java.awt.Frame.MAXIMIZED_BOTH)
    }
    val icon = remember {
        runCatching { object {}.javaClass.getResourceAsStream("/icon.png")?.readBytes()?.decodeToImageBitmap() }.getOrNull()
    }
    val dragStartMouse = remember { java.awt.Point(0, 0) }
    val dragStartWindow = remember { java.awt.Point(0, 0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .background(MaterialTheme.semanticColors.pageBackground)
            .then(
                if (window != null) {
                    Modifier.pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                dragStartMouse.setLocation(java.awt.MouseInfo.getPointerInfo().location)
                                dragStartWindow.setLocation(window.location)
                            },
                            onDrag = { change, _ ->
                                if (window.extendedState == java.awt.Frame.MAXIMIZED_BOTH) {
                                    dragStartMouse.setLocation(java.awt.MouseInfo.getPointerInfo().location)
                                    dragStartWindow.setLocation(window.location)
                                    return@detectDragGestures
                                }
                                val m = java.awt.MouseInfo.getPointerInfo().location
                                window.setLocation(
                                    dragStartWindow.x + (m.x - dragStartMouse.x),
                                    dragStartWindow.y + (m.y - dragStartMouse.y),
                                )
                                change.consume()
                            }
                        )
                    }
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(Spacing.md))
        if (icon != null) {
            Image(
                bitmap = icon,
                contentDescription = "VantaCode",
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.width(Spacing.xs))
        Text(
            text = "VantaCode",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { window?.extendedState = java.awt.Frame.ICONIFIED },
            modifier = Modifier.size(38.dp),
        ) {
            Icon(
                imageVector = FeatherIcons.Minus,
                contentDescription = Strings.window_minimize,
                modifier = Modifier.size(16.dp),
            )
        }
        IconButton(
            onClick = {
                if (window != null) {
                    window.extendedState = if (window.extendedState == java.awt.Frame.MAXIMIZED_BOTH) {
                        java.awt.Frame.NORMAL
                    } else {
                        java.awt.Frame.MAXIMIZED_BOTH
                    }
                    isMaximized = window.extendedState == java.awt.Frame.MAXIMIZED_BOTH
                }
            },
            modifier = Modifier.size(38.dp),
        ) {
            Icon(
                imageVector = if (isMaximized) FeatherIcons.Minimize2 else FeatherIcons.Square,
                contentDescription = Strings.window_maximize,
                modifier = Modifier.size(16.dp),
            )
        }
        IconButton(
            onClick = onRequestClose,
            modifier = Modifier.size(38.dp),
        ) {
            Icon(
                imageVector = FeatherIcons.X,
                contentDescription = Strings.window_close,
                modifier = Modifier.size(16.dp),
            )
        }
        Spacer(Modifier.width(Spacing.xs))
    }
}

/** 全局背景水印：盖在页面上（透明度可调）。 */
@Composable
private fun BackgroundWatermark(modifier: Modifier = Modifier) {
    val backgroundImagePath by AppGraph.backgroundSettingsRepository.imagePathFlow
        .collectAsStateWithLifecycle(initialValue = null)
    val backgroundKind by AppGraph.backgroundSettingsRepository.backgroundKindFlow
        .collectAsStateWithLifecycle(initialValue = BackgroundKind.IMAGE)
    val backgroundAlpha by AppGraph.backgroundSettingsRepository.alphaFlow
        .collectAsStateWithLifecycle(initialValue = BackgroundSettingsRepository.DEFAULT_ALPHA)
    // 首次启动自动解压内置默认视频背景（幂等）。
    LaunchedEffect(Unit) { AppGraph.backgroundSettingsRepository.ensureDefaultBackground() }

    if (backgroundImagePath != null && backgroundAlpha > 0f) {
        when (backgroundKind) {
            BackgroundKind.IMAGE -> {
                val bitmap by produceState<ImageBitmap?>(initialValue = null, backgroundImagePath) {
                    value = kotlinx.coroutines.withContext(Dispatchers.IO) {
                        decodeBackgroundBitmap(
                            backgroundImagePath!!,
                            1280,
                            1280
                        )
                    }
                }
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.alpha(backgroundAlpha)
                    )
                }
            }
            BackgroundKind.GIF -> {
                com.vantacode.feature.settings.presentation.component.GifBackground(
                    path = backgroundImagePath!!,
                    alpha = backgroundAlpha,
                    modifier = modifier
                )
            }
            BackgroundKind.MP4 -> {
                com.vantacode.feature.settings.presentation.component.VideoBackground(
                    path = backgroundImagePath!!,
                    alpha = backgroundAlpha,
                    modifier = modifier
                )
            }
        }
    }
}
