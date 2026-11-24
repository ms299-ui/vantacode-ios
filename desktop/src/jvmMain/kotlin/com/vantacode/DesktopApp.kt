/*
 * VantaCode Desktop — application entry: path/log init, global crash handler, and the
 * startup orchestration previously in AIEditorApp.onCreate.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode

import com.vantacode.core.assets.BuiltinAssets
import com.vantacode.core.net.AppProxy
import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.platform.PlatformInfo
import com.vantacode.core.platform.PlatformPaths
import com.vantacode.core.util.AILogger
import com.vantacode.core.util.FileLogger
import com.vantacode.di.AppGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.schmizz.sshj.common.SecurityUtils
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

/**
 * Desktop application shell. Everything that needs a process-wide singleton starts here,
 * before the compose window is created. All failures here must degrade, not kill startup.
 */
object DesktopApp {
    private const val TAG = "DesktopApp"

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /** Current navigation route, for crash reports. */
    @Volatile
    var currentRoute: String? = null

    /** Current workspace mode name, for crash reports. */
    @Volatile
    var currentWorkspaceMode: String? = null

    /** Called once before the window is shown. */
    fun onCreate() {
        PlatformPaths.ensure()
        FileLogger.init()
        AILogger.init()
        AppProxy.applyGlobal(DesktopContext.instance)
        installCrashHandler()

        // 当前导航模式，崩溃上报带出
        appScope.launch {
            AppGraph.executionModeRepository.executionModeFlow
                .collect { currentWorkspaceMode = it.name }
        }

        // Agent 内置资产同步（docs/prompts/scripts/agents → ~/.aicode）。仅补齐缺失、或覆盖写内置资产。
        appScope.launch { runCatching { BuiltinAssets.extractDocs() }.onFailure { FileLogger.w(TAG, "提取内置文档失败", it) } }
        appScope.launch { runCatching { BuiltinAssets.extractPrompts() }.onFailure { FileLogger.w(TAG, "提取内置提示词失败", it) } }
        appScope.launch { runCatching { BuiltinAssets.extractScripts() }.onFailure { FileLogger.w(TAG, "提取内置脚本失败", it) } }
        appScope.launch { runCatching { BuiltinAssets.extractAgents() }.onFailure { FileLogger.w(TAG, "提取内置子代理失败", it) } }

        // 插件运行时装配：插件工具注册由 AppGraph 启动，这里等安装记录读完再派发
        // app.started 拉起常驻插件。少了这一步，声明 runtime 的插件进程不会自己起来。
        appScope.launch {
            runCatching {
                // 注册插件声明的动态工具（必须在 AppGraph 完全构造后调用，见 AppGraph.init 注释）。
                AppGraph.pluginToolManager.start(appScope)
                AppGraph.pluginManager.refresh()
                AppGraph.pluginHookDispatcher.dispatchAppStarted(
                    com.vantacode.core.platform.PlatformInfo.VERSION_NAME
                )
            }.onFailure { FileLogger.w(TAG, "插件运行时初始化失败", it) }
        }

        // BouncyCastle 完整版（X25519）——sshj 依赖。Windows 自带的是裁剪版，需显式注册。
        appScope.launch {
            runCatching {
                java.security.Security.removeProvider("BC")
                java.security.Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
                SecurityUtils.setSecurityProvider("BC")
            }.onFailure { FileLogger.e(TAG, "注册 BouncyCastle 失败", it) }
        }

        // 凭据请求 IPC 监听 + 各迁移器 + 模型元数据刷新 + 日志级别同步
        AppGraph.credentialRequestBridge.start()

        appScope.launch { runCatching { AppGraph.legacyCredentialMigrator.migrateIfNeeded() }.onFailure { FileLogger.w(TAG, "旧凭据迁移失败", it) } }
        appScope.launch { runCatching { AppGraph.secretEncryptionMigrator.migrateIfNeeded() }.onFailure { FileLogger.w(TAG, "敏感字段加密迁移失败", it) } }
        appScope.launch { runCatching { AppGraph.fileCredentialRepository.migrateToEncoded() }.onFailure { FileLogger.w(TAG, "凭据文件迁移失败", it) } }
        appScope.launch { runCatching { AppGraph.modelMetadataService.refreshFromNetworkIfStale() }.onFailure { FileLogger.w(TAG, "模型元数据刷新失败", it) } }
        appScope.launch {
            AppGraph.logSettingsRepository.levelFlow.collect { FileLogger.setMinLevel(it) }
        }

        // 执行模式：启动时同步读首帧到 ExecutionModeHolder，供委托层转发。随后 SSH supervisor 按配置连接。
        appScope.launch {
            val mode = AppGraph.executionModeRepository.executionModeFlow.first()
            AppGraph.executionModeHolder.setMode(mode)
        }

        // 远程 SSH 模式：按激活 profile 自动连接/重连。失败不阻断 UI（首次命令会重试）。
        appScope.launch {
            kotlinx.coroutines.flow.combine(
                AppGraph.activeRemoteConnectionResolver.activeConfigFlow,
                AppGraph.executionModeHolder.mode
            ) { config, mode -> config to mode }
                .collect { (config, mode) ->
                    if (mode != com.vantacode.feature.settings.data.repository.ExecutionMode.REMOTE_SSH) {
                        AppGraph.remoteSshConnection.stopSupervisor()
                        runCatching {
                            AppGraph.remoteTerminalSessionManager.tabs.value.map { it.id }.forEach { AppGraph.remoteTerminalSessionManager.closeTab(it) }
                        }
                        kotlinx.coroutines.withContext(Dispatchers.IO) { AppGraph.remoteSshConnection.disconnect() }
                        return@collect
                    }
                    if (config == null) return@collect
                    AppGraph.remoteSshConnection.startSupervisor(appScope) {
                        runCatching { AppGraph.workspaceRepository.initialize() }
                            .onFailure { FileLogger.w(TAG, "SSH 重连后加载工作区失败", it) }
                        syncDocsToRemote()
                        releaseBuiltinAssetsToRemote()
                    }
                    if (config == AppGraph.remoteSshConnection.config && AppGraph.remoteSshConnection.isConnected()) return@collect
                    runCatching {
                        AppGraph.remoteSshConnection.connect(config)
                        syncDocsToRemote()
                        releaseBuiltinAssetsToRemote()
                    }.onFailure { FileLogger.e(TAG, "SSH 连接失败，将在首次命令时重试", it) }
                }
        }

        // 连接与同步：由 RemoteRepository 监听工作区变化自动执行。
        // MCP server：启动即连接并注册工具（内部自有 scope，失败不阻塞）。
        appScope.launch { AppGraph.permissionRulesRepository.startWatching() }
        AppGraph.mcpManager.start()

        FileLogger.i(TAG, "VantaCode Desktop ${PlatformInfo.VERSION_NAME} 启动完成，os=${PlatformInfo.operatingSystem}")
    }

    /** Read docs from packaged assets and upload to remote ~/.aicode/docs over SSH. */
    private suspend fun syncDocsToRemote() {
        runCatching {
            val docs = linkedMapOf<String, String>()
            collectAssetDocs("docs", "", docs)
            AppGraph.remoteSshConnection.uploadDocs(docs)
        }.onFailure { FileLogger.w(TAG, "同步内置文档到远程失败", it) }
    }

    /** Upload built-in agents/scripts to remote (fills gaps, never overwrites user files). */
    private suspend fun releaseBuiltinAssetsToRemote() {
        fun collectAssetBytes(assetDir: String): Map<String, ByteArray> {
            val out = linkedMapOf<String, ByteArray>()
            for (entry in com.vantacode.core.platform.DesktopAssets.list(assetDir) ?: emptyArray()) {
                runCatching {
                    out[entry] = com.vantacode.core.platform.DesktopAssets.readBytes("$assetDir/$entry")
                }.onFailure { FileLogger.w(TAG, "读取内置资源失败: $assetDir/$entry", it) }
            }
            return out
        }

        AppGraph.remoteSshConnection.uploadBuiltinFiles("agents", collectAssetBytes("agents"))
        AppGraph.remoteSshConnection.uploadBuiltinFiles("scripts", collectAssetBytes("aicode/scripts"), executable = true)
    }

    private fun collectAssetDocs(assetDir: String, relativePrefix: String, out: MutableMap<String, String>) {
        val entries = com.vantacode.core.platform.DesktopAssets.list(assetDir) ?: return
        for (entry in entries) {
            val assetPath = "$assetDir/$entry"
            val relativePath = if (relativePrefix.isEmpty()) entry else "$relativePrefix/$entry"
            try {
                out[relativePath] = com.vantacode.core.platform.DesktopAssets.readText(assetPath)
            } catch (e: Exception) {
                collectAssetDocs(assetPath, relativePath, out)
            }
        }
    }

    /** 全局未捕获异常处理：落盘 + 弹窗 + 退出。不做自动重启（桌面不递归重启）。 */
    private fun installCrashHandler() {
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val stack = StringWriter().also { sw -> throwable.printStackTrace(PrintWriter(sw)) }.toString()
            FileLogger.e(TAG, "未捕获异常 (thread=${thread.name}, route=$currentRoute, mode=$currentWorkspaceMode)\n$stack")
            FileLogger.flushSync()
            try {
                SwingUtilities.invokeAndWait {
                    JOptionPane.showMessageDialog(
                        null,
                        "VantaCode 遇到未处理的错误并即将退出。\n详情已写入日志（~/.vantacode/logs）。\n\n错误：${throwable.javaClass.simpleName}\n${throwable.message ?: ""}\n（Route: ${currentRoute ?: "?"}）",
                        "VantaCode — 崩溃",
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            } catch (_: Throwable) {
                // 弹窗失败也要先落盘再退出
            }
            previous?.uncaughtException(thread, throwable) ?: kotlin.system.exitProcess(1)
        }
    }
}
