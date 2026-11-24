/*
 * VantaCode Desktop — manual DI graph (replaces Hilt object modules). Every entry is a
 * process-wide lazy singleton; constructors follow the upstream @Inject signatures, verified
 * against this source tree. Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.vantacode.core.db.MigrationLoader
import com.vantacode.core.net.applyTcpKeepAlive
import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.platform.PlatformInfo
import com.vantacode.core.platform.PlatformPaths
import com.vantacode.core.util.FileLogger
import com.vantacode.core.watch.FileChangeHub
import com.vantacode.feature.agent.data.local.dao.AgentMessageDao
import com.vantacode.feature.agent.data.local.dao.ChatSessionDao
import com.vantacode.feature.agent.data.local.dao.CheckpointDao
import com.vantacode.feature.agent.data.local.dao.LlmCallRecordDao
import com.vantacode.feature.agent.data.local.dao.TeamChatDao
import com.vantacode.feature.agent.data.local.dao.TodoItemDao
import com.vantacode.feature.agent.data.local.database.AgentDatabase
import com.vantacode.feature.backup.data.local.dao.BackupTargetDao
import com.vantacode.feature.backup.data.BackupManagerImpl
import com.vantacode.feature.backup.domain.BackupManager
import com.vantacode.feature.backup.data.BackupTargetRepository
import com.vantacode.feature.workspace.data.local.dao.RemoteConnectionDao
import com.vantacode.feature.settings.data.local.dao.AIProviderDao
import com.vantacode.feature.settings.data.repository.AIProviderRepositoryImpl
import com.vantacode.feature.settings.domain.repository.AIProviderRepository
import com.vantacode.feature.agent.data.remote.anthropic.AnthropicApi
import com.vantacode.feature.agent.data.remote.gemini.GeminiApi
import com.vantacode.feature.agent.data.remote.openai.OpenAIApi
import com.vantacode.feature.agent.domain.container.CommandEngine
import com.vantacode.feature.agent.domain.container.DelegatingCommandEngine
import com.vantacode.feature.agent.domain.container.NativeCommandEngine
import com.vantacode.feature.agent.domain.container.RemoteSshConnection
import com.vantacode.feature.agent.domain.container.RemoteSshEngine
import com.vantacode.feature.agent.domain.container.SharedPrefsSshHostKeyStore
import com.vantacode.feature.agent.domain.container.SharedPrefsSshLoginKeyStore
import com.vantacode.feature.agent.domain.container.SshHostKeyStore
import com.vantacode.feature.agent.domain.container.SshLoginKeyStore
import com.vantacode.feature.agent.domain.container.SshPrivateKeyStore
import com.vantacode.feature.agent.domain.container.SshHostKeyVerifier
import com.vantacode.feature.agent.domain.container.ActiveRemoteConnectionResolver
import com.vantacode.feature.agent.domain.container.ContainerInstaller
import com.vantacode.feature.workspace.domain.DelegatingFileAccess
import com.vantacode.feature.workspace.domain.FileAccessProvider
import com.vantacode.feature.workspace.domain.LocalFileAccess
import com.vantacode.feature.workspace.domain.RemoteSftpFileAccess
import com.vantacode.feature.workspace.domain.WorkspacePathMapper
import com.vantacode.feature.workspace.domain.PathHomeResolver
import com.vantacode.feature.workspace.domain.ProjectAicodeRoot
import com.vantacode.feature.workspace.data.repository.WorkspaceRepository
import com.vantacode.feature.settings.data.repository.ExecutionModeHolder
import com.vantacode.feature.settings.data.repository.ExecutionModeRepository
import com.vantacode.feature.agent.domain.tool.file.ReadFileTool
import com.vantacode.feature.agent.domain.tool.file.SendFileTool
import com.vantacode.feature.agent.domain.tool.file.GenerateImageTool
import com.vantacode.feature.agent.domain.tool.file.ViewImageTool
import com.vantacode.feature.agent.domain.tool.file.WriteFileTool
import com.vantacode.feature.agent.domain.tool.editor.EditFileTool
import com.vantacode.feature.agent.domain.tool.container.ExecuteCommandTool
import com.vantacode.feature.agent.domain.tool.explorer.ListFilesTool
import com.vantacode.feature.agent.domain.tool.explorer.SearchCodeTool
import com.vantacode.feature.agent.domain.tool.skill.LoadSkillTool
import com.vantacode.feature.agent.domain.tool.question.AskUserQuestionTool
import com.vantacode.feature.agent.domain.tool.todo.TodoTool
import com.vantacode.feature.agent.domain.tool.subagent.TaskTool
import com.vantacode.feature.agent.domain.tool.subagent.MessageParentTool
import com.vantacode.feature.agent.domain.subagent.SubAgentEventBus
import com.vantacode.feature.agent.domain.prompt.SystemPromptProvider
import com.vantacode.feature.agent.domain.workflow.AgentWorkflow
import com.vantacode.feature.agent.domain.tool.ToolPermissionManager
import com.vantacode.feature.agent.domain.permission.ToolPermissionPolicyEngine
import com.vantacode.feature.agent.domain.tool.ToolRegistry
import com.vantacode.feature.agent.domain.tool.ToolOutputStore
import com.vantacode.feature.settings.data.remote.ModelMetadataService
import com.vantacode.feature.settings.data.remote.ModelApiService
import com.vantacode.feature.settings.data.local.CustomModelMetadataStore
import com.vantacode.feature.settings.domain.service.ModelCostCalculator
import com.vantacode.feature.settings.domain.service.ProviderDashboardRunner
import com.vantacode.feature.settings.data.repository.ProviderKeyRotator
import com.vantacode.feature.settings.data.repository.ProviderProxyRegistry
import com.vantacode.feature.settings.data.repository.CompactionModelSettingsRepository
import com.vantacode.feature.settings.data.repository.DefaultModelSettingsRepository
import com.vantacode.feature.settings.data.repository.GeneralSettingsRepository
import com.vantacode.feature.settings.data.repository.TitleModelSettingsRepository
import com.vantacode.feature.settings.data.repository.SubagentTierSettingsRepository
import com.vantacode.feature.settings.data.repository.ThemeSettingsRepository
import com.vantacode.feature.settings.data.repository.BackgroundSettingsRepository
import com.vantacode.feature.settings.data.repository.ProxySettingsRepository
import com.vantacode.feature.settings.data.repository.SyncSettingsRepository
import com.vantacode.feature.settings.data.repository.LanguageSettingsRepository
import com.vantacode.feature.settings.data.repository.KeepaliveSettingsRepository
import com.vantacode.feature.settings.data.repository.ScreenOnSettingsRepository
import com.vantacode.feature.settings.data.repository.AgentSoundSettingsRepository
import com.vantacode.feature.settings.data.repository.LiveNotificationSettingsRepository
import com.vantacode.feature.settings.data.repository.ApprovalNotificationSettingsRepository
import com.vantacode.feature.settings.data.repository.LogSettingsRepository
import com.vantacode.feature.settings.data.repository.VisionModelSettingsRepository
import com.vantacode.feature.settings.data.repository.ImageGenModelSettingsRepository
import com.vantacode.feature.settings.data.repository.ToolSafetySettingsRepository
import com.vantacode.feature.settings.data.repository.ModelReasoningEffortRepository
import com.vantacode.feature.settings.data.repository.ExecutionModeHolder as ExecutionModeHolderType
import com.vantacode.feature.terminal.domain.DelegatingTerminalSessionProvider
import com.vantacode.feature.terminal.domain.RemoteTerminalSessionManager
import com.vantacode.feature.terminal.domain.TerminalSessionManager
import com.vantacode.feature.terminal.domain.TerminalSessionProvider
import com.vantacode.feature.agent.domain.notification.AgentEventInjector
import com.vantacode.feature.agent.domain.notification.DefaultAgentEventInjector
import com.vantacode.feature.agent.domain.notification.AgentNotificationCenter
import com.vantacode.feature.agent.domain.checkpoint.CheckpointManager
import com.vantacode.feature.agent.domain.session.MessagePersistenceUseCase
import com.vantacode.feature.agent.domain.session.SessionUseCase
import com.vantacode.feature.agent.domain.tool.mcp.ManageMcpTool
import com.vantacode.feature.agent.domain.tool.memory.MemoryTool
import com.vantacode.feature.agent.domain.tool.mode.PlanApprovalManager
import com.vantacode.feature.agent.domain.tool.mode.PlanModeTool
import com.vantacode.feature.agent.domain.tool.goal.GoalStatusTool
import com.vantacode.feature.agent.domain.tool.search.WebFetchTool
import com.vantacode.feature.agent.domain.tool.search.WebSearchTool
import com.vantacode.feature.agent.domain.workflow.ContextCompactor
import com.vantacode.feature.agent.domain.workflow.StatefulAgentWorkflow
import com.vantacode.feature.credentials.data.CredentialRequestBridge
import com.vantacode.feature.credentials.data.LegacyCredentialMigrator
import com.vantacode.feature.settings.data.SecretEncryptionMigrator
import com.vantacode.feature.credentials.data.repository.FileCredentialRepository
import com.vantacode.feature.credentials.domain.repository.CredentialRepository
import com.vantacode.feature.credentials.data.CredentialInjectSettingsRepository
import com.vantacode.feature.agent.domain.skill.SkillRepository
import com.vantacode.feature.agent.domain.memory.MemoryRepository
import com.vantacode.feature.agent.domain.skill.GlobalDirectorySkillSource
import com.vantacode.feature.agent.domain.skill.ProjectDirectorySkillSource
import com.vantacode.feature.agent.domain.skill.SkillConfigRepository
import com.vantacode.feature.agent.domain.memory.GlobalMemorySource
import com.vantacode.feature.agent.domain.prompt.PromptFragmentCatalog
import com.vantacode.feature.agent.domain.subagent.AgentDefinitionRepository
import com.vantacode.feature.agent.domain.subagent.GlobalDirectoryAgentSource
import com.vantacode.feature.agent.domain.subagent.ProjectDirectoryAgentSource
import com.vantacode.feature.agent.domain.subagent.AgentDefinitionConfigRepository
import com.vantacode.feature.agent.domain.tool.FileVersionRegistry
import com.vantacode.feature.agent.domain.tool.WorkspaceFileLock
import com.vantacode.feature.agent.domain.tool.file.VisionSessionStore
import com.vantacode.feature.agent.domain.tool.question.AskUserQuestionManager
import com.vantacode.feature.agent.domain.mention.FileMentionRepository
import com.vantacode.feature.agent.domain.command.SlashCommandRegistry
import com.vantacode.feature.agent.domain.command.InitCommandHandler
import com.vantacode.feature.agent.domain.command.CompressCommandHandler
import com.vantacode.feature.agent.domain.command.GoalCommandHandler
import com.vantacode.feature.agent.domain.command.UsageCommandHandler
import com.vantacode.feature.agent.domain.permission.PermissionRulesRepository
import com.vantacode.feature.git.domain.GitRepository
import com.vantacode.feature.git.presentation.GitViewModel
import com.vantacode.feature.settings.presentation.SettingsViewModel
import com.vantacode.feature.workspace.presentation.WorkspaceViewModel
import com.vantacode.feature.backup.presentation.BackupViewModel
import com.vantacode.feature.workspace.presentation.remote.RemoteServerViewModel
import com.vantacode.feature.settings.presentation.PromptsViewModel
import com.vantacode.feature.agent.presentation.AIAgentViewModel
import com.vantacode.feature.teams.presentation.TeamsViewModel
import com.vantacode.feature.credentials.presentation.CredentialViewModel
import com.vantacode.feature.credentials.data.remote.GitHubDeviceFlowClient
import com.vantacode.feature.git.domain.GitRepository as GitRepositoryType
import com.vantacode.feature.agent.domain.provider.ProviderInstanceFactory
import com.vantacode.feature.plugin.data.PluginBackupPermissionRepository
import com.vantacode.feature.plugin.data.PluginDevSettingsRepository
import com.vantacode.feature.plugin.data.PluginStoreSettingsRepository
import com.vantacode.feature.plugin.data.index.GitHubMirrorSelector
import com.vantacode.feature.plugin.data.index.PluginIndexClient
import com.vantacode.feature.plugin.data.index.PluginIndexSourceRepository
import com.vantacode.feature.plugin.domain.PluginAgentSource
import com.vantacode.feature.plugin.domain.PluginInstallService
import com.vantacode.feature.plugin.domain.PluginManager
import com.vantacode.feature.plugin.domain.PluginSkillSource
import com.vantacode.feature.plugin.runtime.PluginBackupArchiveService
import com.vantacode.feature.plugin.runtime.PluginChatService
import com.vantacode.feature.plugin.runtime.PluginDependencyPreparer
import com.vantacode.feature.plugin.runtime.PluginEventBus
import com.vantacode.feature.plugin.runtime.PluginHookDispatcher
import com.vantacode.feature.plugin.runtime.PluginHttpHookInterceptor
import com.vantacode.feature.plugin.runtime.PluginHttpService
import com.vantacode.feature.plugin.runtime.PluginIntrospectionService
import com.vantacode.feature.plugin.runtime.PluginModelService
import com.vantacode.feature.plugin.runtime.PluginNotifier
import com.vantacode.feature.plugin.runtime.PluginProviderRegistrar
import com.vantacode.feature.plugin.runtime.PluginRuntimeManager
import com.vantacode.feature.plugin.runtime.PluginSecretStore
import com.vantacode.feature.plugin.runtime.PluginShizukuRunner
import com.vantacode.feature.plugin.runtime.PluginSystemService
import com.vantacode.feature.plugin.runtime.PluginTerminalRunner
import com.vantacode.feature.plugin.runtime.PluginTipManager
import com.vantacode.feature.plugin.runtime.PluginToolManager
import com.vantacode.feature.plugin.runtime.PluginToolService
import com.vantacode.feature.plugin.tool.PluginTool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import okhttp3.Call
import okhttp3.Connection
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.File
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/** Process-wide dependency graph; access as AppGraph.xxx. Replaces Hilt. */
object AppGraph {

    val context: DesktopContext = DesktopContext.instance

    val agreementRepository: com.vantacode.feature.agreement.data.AgreementRepository by lazy {
        com.vantacode.feature.agreement.data.AgreementRepository(context)
    }
    val onboardingRepository: com.vantacode.feature.onboarding.data.OnboardingRepository by lazy {
        com.vantacode.feature.onboarding.data.OnboardingRepository(context)
    }

    // ── Database ──────────────────────────────────────────────
    val database: AgentDatabase by lazy {
        Room.databaseBuilder<AgentDatabase>(
            name = File(PlatformPaths.databasesDir, AgentDatabase.DATABASE_NAME).absolutePath
        )
            .addMigrations(*MigrationLoader.loadMigrations())
            // Room KMP 在 JVM 上必须显式提供 SQLiteDriver；bundled 驱动自带各平台原生 SQLite。
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    val checkpointDao: CheckpointDao by lazy { database.checkpointDao() }
    val agentMessageDao: AgentMessageDao by lazy { database.agentMessageDao() }
    val chatSessionDao: ChatSessionDao by lazy { database.chatSessionDao() }
    val aiProviderDao: AIProviderDao by lazy { database.aiProviderDao() }
    val remoteConnectionDao: RemoteConnectionDao by lazy { database.remoteConnectionDao() }
    val llmCallRecordDao: LlmCallRecordDao by lazy { database.llmCallRecordDao() }
    val todoItemDao: TodoItemDao by lazy { database.todoItemDao() }
    val backupTargetDao: BackupTargetDao by lazy { database.backupTargetDao() }
    val teamChatDao: TeamChatDao by lazy { database.teamChatDao() }
    val teamRepository: com.vantacode.feature.agent.domain.team.TeamRepository by lazy {
        com.vantacode.feature.agent.domain.team.TeamRepository(fileAccess, teamChatDao)
    }
    val teamChatRunner: com.vantacode.feature.agent.domain.team.TeamChatRunner by lazy {
        com.vantacode.feature.agent.domain.team.TeamChatRunner(
            aiProviderRepository, anthropicApi, openAIApi, geminiApi,
            providerKeyRotator, modelMetadataService, generalSettingsRepository,
            defaultModelSettingsRepository, agentDefinitionRepository, llmCallRecordDao,
        )
    }

    // ── Network ───────────────────────────────────────────────
    val okHttpClient: OkHttpClient by lazy { buildOkHttpClient() }
    private val retrofitOpenAI: Retrofit by lazy { buildRetrofit("https://api.openai.com/") }
    private val retrofitAnthropic: Retrofit by lazy { buildRetrofit("https://api.anthropic.com/") }
    private val retrofitGemini: Retrofit by lazy { buildRetrofit("https://generativelanguage.googleapis.com/") }

    val openAIApi: OpenAIApi by lazy { retrofitOpenAI.create(OpenAIApi::class.java) }
    val anthropicApi: AnthropicApi by lazy { retrofitAnthropic.create(AnthropicApi::class.java) }
    val geminiApi: GeminiApi by lazy { retrofitGemini.create(GeminiApi::class.java) }

    // ── Command engines ───────────────────────────────────────
    val nativeCommandEngine: NativeCommandEngine by lazy { NativeCommandEngine(context) }
    val remoteSshEngine: RemoteSshEngine by lazy { RemoteSshEngine(remoteSshConnection) }
    val executionModeHolder: ExecutionModeHolder by lazy { ExecutionModeHolder() }
    val executionModeRepository: ExecutionModeRepository by lazy { ExecutionModeRepository(context) }
    val commandEngine: CommandEngine by lazy {
        DelegatingCommandEngine(executionModeHolder, nativeCommandEngine, remoteSshEngine)
    }

    // ── Workspace & paths ─────────────────────────────────────
    val pathHomeResolver: PathHomeResolver by lazy { PathHomeResolver(executionModeHolder, remoteSshConnection) }
    val workspacePathMapper: WorkspacePathMapper by lazy { WorkspacePathMapper(workspaceRepository, pathHomeResolver) }
    val localFileAccess: LocalFileAccess by lazy { LocalFileAccess(workspacePathMapper) }
    val remoteSftpFileAccess: RemoteSftpFileAccess by lazy { RemoteSftpFileAccess(remoteSshConnection, workspaceRepository) }
    val delegatingFileAccess: DelegatingFileAccess by lazy {
        DelegatingFileAccess(executionModeHolder, localFileAccess, remoteSftpFileAccess)
    }
    val fileAccess: FileAccessProvider get() = delegatingFileAccess

    // ── SSH / remote ──────────────────────────────────────────
    val sshHostKeyStore: SshHostKeyStore by lazy { SharedPrefsSshHostKeyStore(context) }
    val sshLoginKeyStore: SshLoginKeyStore by lazy { SharedPrefsSshLoginKeyStore(context) }
    val sshPrivateKeyStore: SshPrivateKeyStore by lazy { SshPrivateKeyStore(context) }
    val sshHostKeyVerifier: SshHostKeyVerifier by lazy { SshHostKeyVerifier(sshHostKeyStore) }
    val remoteSshConnection: RemoteSshConnection by lazy {
        RemoteSshConnection(sshHostKeyStore, sshHostKeyVerifier, sshPrivateKeyStore)
    }
    val containerInstaller: ContainerInstaller by lazy { ContainerInstaller() }
    val projectAicodeRoot: ProjectAicodeRoot by lazy {
        ProjectAicodeRoot(workspaceRepository, executionModeHolder, containerInstaller, remoteSshConnection)
    }
    val fileChangeHub: FileChangeHub by lazy {
        FileChangeHub(workspaceRepository, pathHomeResolver, workspacePathMapper, executionModeHolder)
    }
    val remoteConnectionSelectionRepository: com.vantacode.feature.settings.data.repository.RemoteConnectionSelectionRepository by lazy {
        com.vantacode.feature.settings.data.repository.RemoteConnectionSelectionRepository(context)
    }
    val activeRemoteConnectionResolver: ActiveRemoteConnectionResolver by lazy {
        ActiveRemoteConnectionResolver(remoteConnectionSelectionRepository, remoteConnectionDao, sshLoginKeyStore)
    }

    val sessionUseCase: SessionUseCase by lazy { SessionUseCase(chatSessionDao, agentMessageDao) }
    val generalSettingsRepository: GeneralSettingsRepository by lazy { GeneralSettingsRepository(context) }
    val credentialInjectSettings: CredentialInjectSettingsRepository by lazy { CredentialInjectSettingsRepository(context) }
    val fileCredentialRepository: FileCredentialRepository by lazy { FileCredentialRepository(context) }
    val credentialRepository: CredentialRepository get() = fileCredentialRepository
    val workspaceRepository: WorkspaceRepository by lazy {
        WorkspaceRepository(context, executionModeHolder, remoteSshConnection, pathHomeResolver,
            sessionUseCase, generalSettingsRepository, credentialInjectSettings, credentialRepository)
    }
    val syncSettingsRepository: SyncSettingsRepository by lazy { SyncSettingsRepository(context) }
    val remoteRepository: com.vantacode.feature.workspace.domain.repository.RemoteRepository by lazy {
        com.vantacode.feature.workspace.domain.repository.RemoteRepository(
            remoteConnectionDao, syncSettingsRepository, workspaceRepository,
            sshHostKeyStore, sshHostKeyVerifier, sshPrivateKeyStore, sshLoginKeyStore,
            fileChangeHub, containerInstaller, database
        )
    }

    // ── Repositories (AI) ─────────────────────────────────────
    val aiProviderRepository: AIProviderRepository get() = aiProviderRepositoryImpl
    val aiProviderRepositoryImpl: AIProviderRepositoryImpl by lazy {
        AIProviderRepositoryImpl(aiProviderDao, database)
    }
    val customModelMetadataStore: CustomModelMetadataStore by lazy { CustomModelMetadataStore(context) }
    val modelMetadataService: ModelMetadataService by lazy {
        ModelMetadataService(context, customModelMetadataStore)
    }
    val modelApiService: ModelApiService by lazy { ModelApiService(okHttpClient, context) }
    val modelCostCalculator: ModelCostCalculator by lazy { ModelCostCalculator(modelMetadataService) }
    val providerKeyRotator: ProviderKeyRotator by lazy { ProviderKeyRotator() }
    val providerProxyRegistry: ProviderProxyRegistry by lazy { ProviderProxyRegistry(aiProviderRepository) }

    // ── Skills / memory / prompts ─────────────────────────────
    val skillConfigRepository: SkillConfigRepository by lazy {
        SkillConfigRepository(containerInstaller, projectAicodeRoot, fileChangeHub, fileAccess)
    }
    val globalDirectorySkillSource: GlobalDirectorySkillSource by lazy { GlobalDirectorySkillSource(fileAccess) }
    val projectDirectorySkillSource: ProjectDirectorySkillSource by lazy { ProjectDirectorySkillSource(fileAccess) }
    val skillRepository: SkillRepository by lazy {
        SkillRepository(globalDirectorySkillSource, projectDirectorySkillSource, skillConfigRepository, fileAccess)
    }
    val globalMemorySource: GlobalMemorySource by lazy { GlobalMemorySource(containerInstaller) }
    val memoryRepository: MemoryRepository by lazy {
        MemoryRepository(globalMemorySource, executionModeHolder, containerInstaller, projectAicodeRoot)
    }
    val promptFragmentCatalog: PromptFragmentCatalog by lazy {
        PromptFragmentCatalog(context, containerInstaller, projectAicodeRoot)
    }
    val globalDirectoryAgentSource: GlobalDirectoryAgentSource by lazy { GlobalDirectoryAgentSource(fileAccess) }
    val projectDirectoryAgentSource: ProjectDirectoryAgentSource by lazy { ProjectDirectoryAgentSource(fileAccess) }
    val agentDefinitionConfigRepository: AgentDefinitionConfigRepository by lazy {
        AgentDefinitionConfigRepository(containerInstaller, projectAicodeRoot, fileChangeHub, fileAccess)
    }
    val agentDefinitionRepository: AgentDefinitionRepository by lazy {
        AgentDefinitionRepository(globalDirectoryAgentSource, projectDirectoryAgentSource,
            agentDefinitionConfigRepository, fileAccess)
    }
    val permissionRulesRepository: PermissionRulesRepository by lazy {
        PermissionRulesRepository(context, workspaceRepository, projectAicodeRoot, fileChangeHub, workspacePathMapper)
    }
    val subAgentEventBus: SubAgentEventBus by lazy { SubAgentEventBus() }
    val toolOutputStore: ToolOutputStore by lazy {
        ToolOutputStore(containerInstaller, fileAccess, pathHomeResolver, context)
    }
    val toolPermissionManager: ToolPermissionManager by lazy { ToolPermissionManager() }
    val toolSafetySettings: ToolSafetySettingsRepository by lazy { ToolSafetySettingsRepository(context) }
    val toolPermissionPolicyEngine: ToolPermissionPolicyEngine by lazy {
        ToolPermissionPolicyEngine(permissionRulesRepository, toolSafetySettings, context)
    }
    val workspaceFileLock: WorkspaceFileLock by lazy { WorkspaceFileLock() }
    val fileVersionRegistry: FileVersionRegistry by lazy { FileVersionRegistry() }
    val visionSessionStore: VisionSessionStore by lazy { VisionSessionStore(containerInstaller) }
    val askUserQuestionManager: AskUserQuestionManager by lazy { AskUserQuestionManager() }
    val fileMentionRepository: FileMentionRepository by lazy { FileMentionRepository(commandEngine) }

    // ── Tools ─────────────────────────────────────────────────
    val readFileTool: ReadFileTool by lazy { ReadFileTool(fileAccess, fileVersionRegistry) }
    val sendFileTool: SendFileTool by lazy { SendFileTool(fileAccess, generalSettingsRepository) }
    val viewImageTool: ViewImageTool by lazy {
        ViewImageTool(fileAccess, visionSessionStore, aiProviderRepository, defaultModelSettingsRepository,
            generalSettingsRepository, visionModelSettingsRepository, modelMetadataService,
            sessionUseCase, llmCallRecordDao, openAIApi, anthropicApi, geminiApi, context)
    }
    val writeFileTool: WriteFileTool by lazy { WriteFileTool(fileAccess, workspaceFileLock, fileVersionRegistry) }
    val editFileTool: EditFileTool by lazy { EditFileTool(fileAccess, workspaceFileLock, fileVersionRegistry) }
    val generateImageTool: GenerateImageTool by lazy {
        GenerateImageTool(fileAccess, imageGenModelSettingsRepository, aiProviderRepository, openAIApi, geminiApi,
            okHttpClient, providerKeyRotator)
    }
    val executeCommandTool: ExecuteCommandTool by lazy { ExecuteCommandTool(commandEngine, workspaceRepository) }
    val listFilesTool: ListFilesTool by lazy { ListFilesTool(fileAccess) }
    val searchCodeTool: SearchCodeTool by lazy { SearchCodeTool(commandEngine, workspaceRepository, pathHomeResolver) }
    val loadSkillTool: LoadSkillTool by lazy { LoadSkillTool(skillRepository) }
    val askUserQuestionTool: AskUserQuestionTool by lazy { AskUserQuestionTool(askUserQuestionManager) }
    val manageMcpTool: ManageMcpTool by lazy { ManageMcpTool(mcpConfigRepository, context) }
    val webSearchTool: WebSearchTool by lazy { WebSearchTool() }
    val webFetchTool: WebFetchTool by lazy { WebFetchTool() }
    val planModeTool: PlanModeTool by lazy { PlanModeTool(chatSessionDao, context) }
    val goalStatusTool: GoalStatusTool by lazy { GoalStatusTool(chatSessionDao) }
    val todoTool: TodoTool by lazy { TodoTool(todoItemDao) }
    val memoryTool: MemoryTool by lazy { MemoryTool(memoryRepository, context) }
    val taskTool: TaskTool by lazy {
        TaskTool(sessionUseCase, chatSessionDao, agentMessageDao, subAgentEventBus, agentDefinitionRepository,
            aiProviderRepository, subagentTierSettingsRepository, context)
    }
    val messageParentTool: MessageParentTool by lazy { MessageParentTool(sessionUseCase, subAgentEventBus) }

    val toolRegistry: ToolRegistry by lazy {
        ToolRegistry().apply {
            register("readFile", readFileTool)
            register("sendFile", sendFileTool)
            register("viewImage", viewImageTool)
            register("writeFile", writeFileTool)
            register("editFile", editFileTool)
            register("generateImage", generateImageTool)
            register("Bash", executeCommandTool)
            register("list", listFilesTool)
            register("search", searchCodeTool)
            register("loadSkill", loadSkillTool)
            register("askUserQuestion", askUserQuestionTool)
            register("manageMcp", manageMcpTool)
            register("websearch", webSearchTool)
            register("webfetch", webFetchTool)
            register("planMode", planModeTool)
            register("goalStatus", goalStatusTool)
            register("todo", todoTool)
            register("memory", memoryTool)
            register("task", taskTool)
            register("messageParent", messageParentTool)
            // AI 可调用的插件管理工具；插件贡献的动态工具由 PluginToolManager 另行同步。
            register("plugin", pluginTool)
        }
    }

    // ── Agent infrastructure ──────────────────────────────────
    val planApprovalManager: PlanApprovalManager by lazy { PlanApprovalManager(chatSessionDao) }
    val systemPromptProvider: SystemPromptProvider by lazy {
        SystemPromptProvider(context, skillRepository, memoryRepository, promptFragmentCatalog,
            containerInstaller, agentDefinitionRepository, executionModeHolder)
    }
    val messagePersistenceUseCase: MessagePersistenceUseCase by lazy {
        MessagePersistenceUseCase(agentMessageDao, database)
    }
    val contextCompactor: ContextCompactor by lazy {
        ContextCompactor(agentMessageDao, modelMetadataService, systemPromptProvider,
            llmCallRecordDao, generalSettingsRepository, messagePersistenceUseCase)
    }
    val checkpointManager: CheckpointManager by lazy {
        CheckpointManager(context, checkpointDao, fileAccess, workspaceFileLock, fileVersionRegistry)
    }
    val agentNotificationCenter: AgentNotificationCenter by lazy { AgentNotificationCenter() }
    val agentEventInjector: AgentEventInjector by lazy { DefaultAgentEventInjector() }
    val compactionModelSettingsRepository: CompactionModelSettingsRepository by lazy { CompactionModelSettingsRepository(context) }
    val titleModelSettingsRepository: TitleModelSettingsRepository by lazy { TitleModelSettingsRepository(context) }
    val defaultModelSettingsRepository: DefaultModelSettingsRepository by lazy { DefaultModelSettingsRepository(context) }
    val modelReasoningEffortRepository: ModelReasoningEffortRepository by lazy { ModelReasoningEffortRepository(context) }
    val subagentTierSettingsRepository: SubagentTierSettingsRepository by lazy { SubagentTierSettingsRepository(context) }

    val agentWorkflow: AgentWorkflow by lazy {
        StatefulAgentWorkflow(
            toolRegistry,
            aiProviderRepository,
            openAIApi,
            anthropicApi,
            geminiApi,
            systemPromptProvider,
            toolPermissionManager,
            toolPermissionPolicyEngine,
            contextCompactor,
            planApprovalManager,
            toolOutputStore,
            modelMetadataService,
            compactionModelSettingsRepository,
            titleModelSettingsRepository,
            defaultModelSettingsRepository,
            generalSettingsRepository,
            sessionUseCase,
            messagePersistenceUseCase,
            checkpointManager,
            llmCallRecordDao,
            providerKeyRotator,
            agentNotificationCenter,
            agentEventInjector,
            fileAccess,
            context
        )
    }

    // ── Settings repos ────────────────────────────────────────
    val themeSettingsRepository: ThemeSettingsRepository by lazy { ThemeSettingsRepository(context) }
    val backgroundSettingsRepository: BackgroundSettingsRepository by lazy { BackgroundSettingsRepository(context) }
    val languageSettingsRepository: LanguageSettingsRepository by lazy { LanguageSettingsRepository(context) }
    val keepaliveSettingsRepository: KeepaliveSettingsRepository by lazy { KeepaliveSettingsRepository(context) }
    val screenOnSettingsRepository: ScreenOnSettingsRepository by lazy { ScreenOnSettingsRepository(context) }
    val agentSoundSettingsRepository: AgentSoundSettingsRepository by lazy { AgentSoundSettingsRepository(context) }
    val liveNotificationSettingsRepository: LiveNotificationSettingsRepository by lazy { LiveNotificationSettingsRepository(context) }
    val approvalNotificationSettingsRepository: ApprovalNotificationSettingsRepository by lazy { ApprovalNotificationSettingsRepository(context) }
    val logSettingsRepository: LogSettingsRepository by lazy { LogSettingsRepository(context) }
    val visionModelSettingsRepository: VisionModelSettingsRepository by lazy { VisionModelSettingsRepository(context) }
    val imageGenModelSettingsRepository: ImageGenModelSettingsRepository by lazy { ImageGenModelSettingsRepository(context) }
    val proxySettingsRepository: ProxySettingsRepository by lazy { ProxySettingsRepository(context) }
    val marketRepository: com.vantacode.feature.settings.data.repository.MarketRepository by lazy {
        com.vantacode.feature.settings.data.repository.MarketRepository()
    }
    val updateCheckSettingsRepository: com.vantacode.feature.settings.data.repository.UpdateCheckSettingsRepository by lazy {
        com.vantacode.feature.settings.data.repository.UpdateCheckSettingsRepository(context)
    }

    // ── Plugin system ─────────────────────────────────────────
    // PluginModelService 需要按供应商配置装配独立 Provider 实例；桌面端该工厂已移植，这里单独接线。
    private val pluginProviderInstanceFactory: ProviderInstanceFactory by lazy {
        ProviderInstanceFactory(
            openAIApi, anthropicApi, geminiApi, providerKeyRotator,
            modelMetadataService, generalSettingsRepository
        )
    }

    val pluginDependencyPreparer: PluginDependencyPreparer by lazy { PluginDependencyPreparer(commandEngine) }

    /** UI（PluginSection）与整个插件系统共用的单例；用接受 ContainerInstaller 的 public @Inject 构造器。 */
    val pluginManager: PluginManager by lazy { PluginManager(containerInstaller, pluginDependencyPreparer) }

    /** 插件贡献的技能 / 子代理来源，在 init 中注册进 SkillRepository / AgentDefinitionRepository。 */
    val pluginSkillSource: PluginSkillSource by lazy { PluginSkillSource(pluginManager) }
    val pluginAgentSource: PluginAgentSource by lazy { PluginAgentSource(pluginManager) }

    val pluginEventBus: PluginEventBus by lazy { PluginEventBus() }
    val pluginNotifier: PluginNotifier by lazy { PluginNotifier() }
    val pluginShizukuRunner: PluginShizukuRunner by lazy { PluginShizukuRunner() }
    val pluginSecretStore: PluginSecretStore by lazy { PluginSecretStore(pluginManager) }
    val pluginSystemService: PluginSystemService by lazy { PluginSystemService(pluginManager) }
    val pluginTerminalRunner: PluginTerminalRunner by lazy {
        PluginTerminalRunner(terminalSessionProvider, pluginManager)
    }
    val pluginProviderRegistrar: PluginProviderRegistrar by lazy {
        PluginProviderRegistrar(pluginManager, aiProviderRepository)
    }

    val pluginStoreSettingsRepository: PluginStoreSettingsRepository by lazy { PluginStoreSettingsRepository(context) }
    val pluginDevSettingsRepository: PluginDevSettingsRepository by lazy { PluginDevSettingsRepository(context) }
    val pluginBackupPermissionRepository: PluginBackupPermissionRepository by lazy {
        PluginBackupPermissionRepository(context)
    }
    val pluginIndexSourceRepository: PluginIndexSourceRepository by lazy { PluginIndexSourceRepository(context) }
    val gitHubMirrorSelector: GitHubMirrorSelector by lazy { GitHubMirrorSelector() }

    /** UI（PluginSection）直接读取；镜像选择器与商店设置均已在桌面端接线。 */
    val pluginIndexClient: PluginIndexClient by lazy {
        PluginIndexClient(okHttpClient, pluginStoreSettingsRepository, gitHubMirrorSelector)
    }

    val pluginHttpService: PluginHttpService by lazy { PluginHttpService(pluginManager, okHttpClient) }
    val pluginChatService: PluginChatService by lazy {
        PluginChatService(
            context, pluginManager, workspaceRepository, sessionUseCase, chatSessionDao, agentMessageDao,
            defaultModelSettingsRepository, modelReasoningEffortRepository, toolPermissionManager,
            askUserQuestionManager, planApprovalManager
        )
    }
    val pluginModelService: PluginModelService by lazy {
        PluginModelService(
            pluginManager, aiProviderRepository, pluginProviderInstanceFactory, modelMetadataService,
            defaultModelSettingsRepository, toolRegistry, fileAccess, pathHomeResolver,
            workspaceRepository, executionModeHolder
        )
    }
    val pluginIntrospectionService: PluginIntrospectionService by lazy {
        PluginIntrospectionService(pluginManager, toolRegistry, mcpManager)
    }
    val pluginToolService: PluginToolService by lazy {
        PluginToolService(
            pluginManager, workspaceRepository, toolRegistry, toolPermissionPolicyEngine,
            toolPermissionManager, executionModeHolder, remoteSshConnection
        )
    }

    // PluginTipManager ↔ PluginRuntimeManager 互为依赖：用 javax.inject.Provider 惰性取，打断初始化环。
    val pluginTipManager: PluginTipManager by lazy { PluginTipManager(pluginManager, Provider { pluginRuntimeManager }) }
    val pluginRuntimeManager: PluginRuntimeManager by lazy {
        PluginRuntimeManager(
            pluginManager, containerInstaller, nativeCommandEngine, fileAccess, pathHomeResolver,
            commandEngine, workspaceRepository, executionModeHolder, pluginEventBus, pluginNotifier,
            pluginTerminalRunner, pluginShizukuRunner, pluginTipManager, pluginChatService,
            pluginModelService, pluginSecretStore, pluginSystemService, pluginIntrospectionService,
            pluginToolService, pluginHttpService, aiProviderRepository
        )
    }
    val pluginHookDispatcher: PluginHookDispatcher by lazy {
        PluginHookDispatcher(pluginManager, pluginRuntimeManager)
    }
    val pluginHttpHookInterceptor: PluginHttpHookInterceptor by lazy {
        PluginHttpHookInterceptor(pluginHookDispatcher)
    }
    val pluginInstallService: PluginInstallService by lazy {
        PluginInstallService(pluginManager, pluginProviderRegistrar, pluginHookDispatcher)
    }
    val pluginBackupArchiveService: PluginBackupArchiveService by lazy {
        PluginBackupArchiveService(
            pluginManager, pluginRuntimeManager, pluginBackupPermissionRepository,
            fileAccess, pathHomeResolver
        )
    }
    val pluginToolManager: PluginToolManager by lazy {
        PluginToolManager(
            context, pluginManager, toolRegistry, pluginRuntimeManager,
            pluginTerminalRunner, pluginModelService
        )
    }

    /** AI 工具 "plugin" 的单例实现（已注册进 [toolRegistry]）。 */
    val pluginTool: PluginTool by lazy {
        PluginTool(
            pluginManager,
            Provider { pluginInstallService },
            Provider { pluginProviderRegistrar },
            Provider { pluginTerminalRunner }
        )
    }

    private val pluginSystemScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // 注意：pluginToolManager.start() 不能在 init 里调用——它会在 <clinit> 阶段
        // 过早触发 toolRegistry → mcpConfigRepository 的 lazy 依赖链（此时其它 lazy
        // 字段尚未初始化完），导致 NPE / ExceptionInInitializerError。改由 DesktopApp.onCreate 调用。
        // 让插件贡献的技能 / 子代理进入既有的 SkillSource / AgentDefinitionSource 聚合链。
        skillRepository.addSource(pluginSkillSource)
        agentDefinitionRepository.addSource(pluginAgentSource)
    }

    // ── Backup ────────────────────────────────────────────────
    val backupTargetRepository: BackupTargetRepository by lazy { BackupTargetRepository(backupTargetDao, okHttpClient) }
    val backupManager: BackupManager by lazy {
        BackupManagerImpl(context, aiProviderDao, remoteConnectionDao, chatSessionDao,
            agentMessageDao, todoItemDao, mcpConfigRepository, mcpManager, permissionRulesRepository,
            themeSettingsRepository, keepaliveSettingsRepository, liveNotificationSettingsRepository,
            approvalNotificationSettingsRepository, screenOnSettingsRepository, agentSoundSettingsRepository,
            generalSettingsRepository, logSettingsRepository, visionModelSettingsRepository,
            compactionModelSettingsRepository, syncSettingsRepository, workspaceRepository)
    }

    // ── Credential helpers ────────────────────────────────────
    val credentialRequestBridge: CredentialRequestBridge by lazy { CredentialRequestBridge(credentialRepository) }
    val legacyCredentialMigrator: LegacyCredentialMigrator by lazy { LegacyCredentialMigrator(database, fileCredentialRepository) }
    val secretEncryptionMigrator: SecretEncryptionMigrator by lazy { SecretEncryptionMigrator(context, aiProviderDao, remoteConnectionDao) }

    // ── MCP ───────────────────────────────────────────────────
    val mcpConfigRepository: com.vantacode.feature.agent.domain.mcp.McpConfigRepository by lazy {
        com.vantacode.feature.agent.domain.mcp.McpConfigRepository(
            containerInstaller, workspaceRepository, projectAicodeRoot, fileChangeHub
        )
    }
    val mcpOAuthFile: File by lazy { File(PlatformPaths.agentConfigDir, "mcp-oauth.json") }
    val mcpTokenStore: com.vantacode.feature.agent.domain.mcp.auth.McpTokenStore by lazy {
        com.vantacode.feature.agent.domain.mcp.auth.McpTokenStore(mcpOAuthFile)
    }
    val mcpOAuthDiscovery: com.vantacode.feature.agent.domain.mcp.auth.McpOAuthDiscovery by lazy {
        com.vantacode.feature.agent.domain.mcp.auth.McpOAuthDiscovery(okHttpClient)
    }
    val mcpClientRegistrar: com.vantacode.feature.agent.domain.mcp.auth.McpClientRegistrar by lazy {
        com.vantacode.feature.agent.domain.mcp.auth.McpClientRegistrar(okHttpClient, mcpTokenStore)
    }
    val mcpOAuthManager: com.vantacode.feature.agent.domain.mcp.auth.McpOAuthManager by lazy {
        com.vantacode.feature.agent.domain.mcp.auth.McpOAuthManager(context, okHttpClient, mcpOAuthDiscovery, mcpClientRegistrar, mcpTokenStore)
    }
    val mcpManager: com.vantacode.feature.agent.domain.mcp.McpManager by lazy {
        com.vantacode.feature.agent.domain.mcp.McpManager(
            mcpConfigRepository, toolRegistry, okHttpClient, nativeCommandEngine,
            workspaceRepository, mcpOAuthManager
        )
    }

    // ── Terminal ──────────────────────────────────────────────
    val terminalSessionManager: TerminalSessionManager by lazy {
        TerminalSessionManager(nativeCommandEngine, workspaceRepository)
    }
    val remoteTerminalSessionManager: RemoteTerminalSessionManager by lazy {
        RemoteTerminalSessionManager(remoteSshConnection, executionModeHolder, workspaceRepository)
    }
    val terminalSessionProvider: TerminalSessionProvider by lazy {
        DelegatingTerminalSessionProvider(executionModeHolder, terminalSessionManager, remoteTerminalSessionManager)
    }

    // ── Slash commands ────────────────────────────────────────
    val initCommandHandler: InitCommandHandler by lazy { InitCommandHandler(systemPromptProvider) }
    val compressCommandHandler: CompressCommandHandler by lazy { CompressCommandHandler() }
    val goalCommandHandler: GoalCommandHandler by lazy { GoalCommandHandler() }
    val usageCommandHandler: UsageCommandHandler by lazy { UsageCommandHandler() }
    val slashCommandRegistry: SlashCommandRegistry by lazy {
        SlashCommandRegistry(
            setOf(initCommandHandler, compressCommandHandler, goalCommandHandler, usageCommandHandler),
            skillRepository,
            context
        )
    }

    // ── Git ───────────────────────────────────────────────────
    val gitRepository: GitRepository by lazy { GitRepository(commandEngine, workspaceRepository) }
    val gitHubDeviceFlow: GitHubDeviceFlowClient by lazy { GitHubDeviceFlowClient() }
    val providerDashboardRunner: ProviderDashboardRunner by lazy {
        ProviderDashboardRunner(commandEngine, providerKeyRotator, workspaceRepository, fileAccess, pathHomeResolver)
    }

    // ── ViewModels ────────────────────────────────────────────
    val viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: kotlin.reflect.KClass<T>, extras: CreationExtras): T {
            val vm: ViewModel = when (modelClass) {
                AIAgentViewModel::class -> AIAgentViewModel(
                    agentWorkflow, toolRegistry, agentMessageDao, chatSessionDao, llmCallRecordDao,
                    modelCostCalculator, aiProviderRepository, defaultModelSettingsRepository,
                    modelReasoningEffortRepository, toolPermissionManager, askUserQuestionManager,
                    nativeCommandEngine, sessionUseCase, messagePersistenceUseCase, planApprovalManager,
                    terminalSessionManager, terminalSessionProvider, slashCommandRegistry, fileMentionRepository,
                    checkpointManager, checkpointDao, backupManager, mcpManager, agentSoundSettingsRepository,
                    generalSettingsRepository, liveNotificationSettingsRepository,
                    com.vantacode.feature.agent.presentation.notification.AgentLiveNotificationManager(liveNotificationSettingsRepository),
                    approvalNotificationSettingsRepository,
                    com.vantacode.feature.agent.presentation.notification.AgentApprovalNotificationManager(approvalNotificationSettingsRepository),
                    subAgentEventBus, agentNotificationCenter, agentDefinitionRepository, todoItemDao,
                    fileAccess, fileChangeHub, context, executionModeHolder
                )
                SettingsViewModel::class -> SettingsViewModel(
                    context, aiProviderRepository, modelApiService, modelMetadataService,
                    logSettingsRepository, themeSettingsRepository, backgroundSettingsRepository,
                    generalSettingsRepository,
                    languageSettingsRepository, mcpConfigRepository, mcpManager, permissionRulesRepository,
                    toolSafetySettings, skillRepository, agentDefinitionRepository, agentDefinitionConfigRepository,
                    toolRegistry, skillConfigRepository, visionModelSettingsRepository, imageGenModelSettingsRepository,
                    compactionModelSettingsRepository, titleModelSettingsRepository, subagentTierSettingsRepository,
                    defaultModelSettingsRepository, executionModeHolder, remoteSshConnection, remoteRepository,
                    llmCallRecordDao, modelCostCalculator, providerDashboardRunner,
                    proxySettingsRepository
                )
                WorkspaceViewModel::class -> WorkspaceViewModel(workspaceRepository, executionModeHolder)
                GitViewModel::class -> GitViewModel(gitRepository, context, themeSettingsRepository, agentWorkflow)
                CredentialViewModel::class -> CredentialViewModel(
                    credentialRepository, gitRepository, credentialInjectSettings, remoteSshConnection,
                    workspaceRepository, gitHubDeviceFlow, context
                )
                TeamsViewModel::class -> TeamsViewModel(
                    teamRepository, teamChatRunner, teamChatDao, defaultModelSettingsRepository,
                )
                BackupViewModel::class -> BackupViewModel(context, backupManager, backupTargetRepository)
                RemoteServerViewModel::class -> RemoteServerViewModel(
                    context, remoteRepository, workspaceRepository, syncSettingsRepository,
                    remoteSshConnection, sshLoginKeyStore, sshPrivateKeyStore,
                    com.vantacode.feature.workspace.domain.remote.ftp.FtpServerManager(context)
                )
                PromptsViewModel::class -> PromptsViewModel(promptFragmentCatalog, workspaceRepository)
                else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.simpleName}")
            }
            return vm as T
        }
    }


    // ── HTTP helpers ──────────────────────────────────────────
    private fun buildOkHttpClient(): OkHttpClient {
        val userAgent = "vantacode/${PlatformInfo.VERSION_NAME} (Desktop; ${PlatformInfo.operatingSystem})"
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .eventListenerFactory(EventListener.Factory { call ->
                object : EventListener() {
                    private val callId = Integer.toHexString(System.identityHashCode(call))
                    private val host = call.request().url.host
                    private var callStartNanos = 0L
                    private var connectStartNanos = 0L
                    private var connectMs: Long? = null
                    private var connectionId = "none"
                    private var protocol = "unknown"

                    private fun logResult(stage: String, details: String = "") {
                        val totalMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - callStartNanos)
                        FileLogger.i(
                            "OkHttp",
                            "call=$callId host=$host connection=$connectionId protocol=$protocol " +
                                "stage=$stage totalMs=$totalMs" +
                                (connectMs?.let { " connectMs=$it" } ?: "") + details
                        )
                    }

                    override fun callStart(call: Call) {
                        callStartNanos = System.nanoTime()
                    }

                    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
                        connectStartNanos = System.nanoTime()
                        connectMs = null
                        connectionId = "none"
                        protocol = "unknown"
                    }

                    override fun connectEnd(
                        call: Call,
                        inetSocketAddress: InetSocketAddress,
                        proxy: Proxy,
                        protocol: Protocol?
                    ) {
                        this.protocol = protocol?.toString() ?: "unknown"
                        connectMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectStartNanos)
                    }

                    override fun connectFailed(
                        call: Call,
                        inetSocketAddress: InetSocketAddress,
                        proxy: Proxy,
                        protocol: Protocol?,
                        ioe: IOException
                    ) {
                        this.protocol = protocol?.toString() ?: "unknown"
                        connectMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectStartNanos)
                    }

                    override fun connectionAcquired(call: Call, connection: Connection) {
                        connectionId = Integer.toHexString(System.identityHashCode(connection))
                        protocol = connection.protocol().toString()
                        connection.socket().applyTcpKeepAlive()
                    }

                    override fun callFailed(call: Call, ioe: IOException) {
                        logResult("callFailed", " exception=${ioe.javaClass.name}")
                    }

                    override fun callEnd(call: Call) {
                        logResult("callEnd")
                    }
                }
            })
            .addInterceptor { chain ->
                val request = chain.request()
                val finalRequest = if (request.header("User-Agent") != null) {
                    request
                } else {
                    request.newBuilder().header("User-Agent", userAgent).build()
                }
                chain.proceed(finalRequest)
            }
            .build()
    }

    private fun buildRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
