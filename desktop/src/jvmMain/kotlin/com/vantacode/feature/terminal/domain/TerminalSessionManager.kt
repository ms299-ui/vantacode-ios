/*
 * VantaCode Desktop — local PTY terminal session pool (Git Bash / PowerShell / cmd).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.domain

import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.agent.domain.container.NativeCommandEngine
import com.vantacode.feature.terminal.presentation.component.AppTerminalSessionClient
import com.vantacode.feature.workspace.data.repository.WorkspaceRepository
import com.termux.terminal.TerminalSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Process-wide pool of local PTY terminal sessions. All [TerminalSession]s are owned here
 * so they survive navigation; ViewModels stay read-only observers. Mirrors the upstream
 * TerminalSessionManager with the proot container replaced by [NativeCommandEngine] +
 * [Pty4jSessionBackend].
 *
 * Each tab has a stable AI-friendly id (`term-N`).
 */
@Singleton
class TerminalSessionManager @Inject constructor(
    private val engine: NativeCommandEngine,
    private val workspaceRepository: WorkspaceRepository
) : TerminalSessionProvider {

    private companion object {
        const val TAG = "TerminalSessionManager"
        const val TRANSCRIPT_ROWS = 2000
        const val DEFAULT_COLUMNS = 80
        const val DEFAULT_ROWS = 24
        const val EXIT_MARKER_GRACE_MS = 1_500L
        const val EXIT_MARKER_POLL_MS = 1_000L
        const val EXIT_MARKER_PREFIX = "[command exited: "
    }

    private val _tabs = MutableStateFlow<List<TerminalTab>>(emptyList())
    val tabs: StateFlow<List<TerminalTab>> = _tabs.asStateFlow()

    private val _activeTabId = MutableStateFlow<String?>(null)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()

    /** Bumps on any output/state change; the Compose terminal collects it to redraw. */
    private val _revision = MutableStateFlow(0)
    val revision: StateFlow<Int> = _revision.asStateFlow()

    private val _tabFinishedEvents = MutableSharedFlow<TabFinishedEvent>(extraBufferCapacity = 16)
    override val tabFinishedEvents: SharedFlow<TabFinishedEvent> = _tabFinishedEvents.asSharedFlow()

    private val idCounter = AtomicInteger(0)
    private val monitorScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val activeTab: TerminalTab? get() = _tabs.value.firstOrNull { it.id == _activeTabId.value }

    fun tab(id: String): TerminalTab? = _tabs.value.firstOrNull { it.id == id }

    fun hasBackgroundTabs(): Boolean =
        _tabs.value.any { it.isBackground && it.runState is RunState.Running }

    /** 终端页进入时调用：没有任何标签则建一个交互 shell。幂等。 */
    suspend fun ensureInitialTab() {
        if (_tabs.value.isEmpty()) {
            createInteractiveTab()
        } else if (_activeTabId.value == null) {
            _activeTabId.value = _tabs.value.first().id
        }
    }

    suspend fun createInteractiveTab(): String =
        openTab(command = null, isBackground = false, notify = false, title = null, sourceSessionId = null)

    override suspend fun startBackgroundCommand(
        command: String,
        title: String?,
        notify: Boolean,
        sourceSessionId: String?
    ): String {
        val id = openTab(command, isBackground = true, notify = notify, title, sourceSessionId)
        if (notify) monitorBackgroundExit(id)
        FileLogger.i(TAG, "后台命令标签 $id: $command")
        return id
    }

    override fun sendInput(id: String, input: String, appendNewline: Boolean): Boolean {
        val tab = runningTab(id) ?: return false
        val text = if (appendNewline && !input.endsWith("\n")) input + "\n" else input
        writeToSession(tab.session, text)
        return true
    }

    override fun writeToTab(id: String, text: String): Boolean {
        val tab = runningTab(id) ?: return false
        writeToSession(tab.session, text)
        return true
    }

    override fun writeBytesToTab(id: String, vararg bytes: Int): Boolean {
        val tab = runningTab(id) ?: return false
        val arr = ByteArray(bytes.size) { bytes[it].toByte() }
        tab.session.write(arr, 0, arr.size)
        return true
    }

    fun writeToActive(text: String) {
        activeTab?.let { writeToSession(it.session, text) }
    }

    fun writeBytesToActive(vararg bytes: Int) {
        val tab = activeTab ?: return
        val arr = ByteArray(bytes.size) { bytes[it].toByte() }
        tab.session.write(arr, 0, arr.size)
    }

    override fun getTabOutput(id: String): String? {
        val tab = tab(id) ?: return null
        return runCatching {
            tab.session.emulator?.screen?.transcriptText?.trimEnd('\n')
        }.getOrNull() ?: ""
    }

    override fun listTabs(): List<TabInfo> = _tabs.value.map {
        TabInfo(
            id = it.id,
            title = it.title,
            isBackground = it.isBackground,
            running = it.runState is RunState.Running,
            command = it.command
        )
    }

    fun activate(id: String) {
        if (_tabs.value.any { it.id == id }) _activeTabId.value = id
    }

    override fun closeTab(id: String): Boolean {
        val tab = tab(id) ?: return false
        runCatching { tab.session.finishIfRunning() }
        val remaining = _tabs.value.filterNot { it.id == id }
        _tabs.value = remaining
        if (_activeTabId.value == id) {
            _activeTabId.value = remaining.lastOrNull()?.id
        }
        bumpRevision()
        FileLogger.i(TAG, "关闭终端标签 $id")
        return true
    }

    fun rename(id: String, title: String) {
        tab(id)?.let {
            it.title = title
            bumpRevision()
        }
    }

    private fun runningTab(id: String): TerminalTab? {
        val t = tab(id) ?: return null
        return if (t.runState is RunState.Running) t else null
    }

    private fun nextId(): String = "term-${idCounter.incrementAndGet()}"

    private suspend fun openTab(
        command: String?,
        isBackground: Boolean,
        notify: Boolean,
        title: String?,
        sourceSessionId: String?
    ): String {
        val id = nextId()
        val (session, client) = buildSession()
        val workspace = workspaceRepository.currentPath()
        if (workspace.isNotBlank()) {
            session.write(LocalShell.cdCommand(workspace))
        }
        if (command != null) {
            val kind = LocalShell.resolve().kind
            session.write(LocalShell.backgroundCommand(command, notify, kind))
        }
        addTab(
            TerminalTab(
                id = id,
                title = title ?: id,
                session = session,
                isBackground = isBackground,
                command = command,
                notifyOnExit = notify,
                sourceSessionId = sourceSessionId,
                client = client,
                runState = RunState.Running
            )
        )
        if (_activeTabId.value == null) _activeTabId.value = id
        FileLogger.i(TAG, "新建终端标签 $id: command=${command ?: "(interactive)"}")
        return id
    }

    private fun buildSession(): Pair<TerminalSession, AppTerminalSessionClient> {
        val resolution = LocalShell.resolve()
        val workspace = workspaceRepository.currentPath()
        val cwd = workspace.takeIf { it.isNotBlank() }?.let { File(it) }
        lateinit var session: TerminalSession
        val client = AppTerminalSessionClient(
            onScreenChanged = { bumpRevision() },
            onFinished = { finished ->
                _tabs.value.firstOrNull { it.session === finished }?.let { target ->
                    target.runState = RunState.Finished(finished.exitStatus)
                    bumpRevision()
                    FileLogger.i(TAG, "终端标签 ${target.id} 会话结束 exit=${finished.exitStatus}")
                    if (target.notifyOnExit && !target.finishedNotified) {
                        target.finishedNotified = true
                        _tabFinishedEvents.tryEmit(
                            TabFinishedEvent(
                                target.id, target.title, target.command, finished.exitStatus,
                                target.sourceSessionId,
                                tailOutput = getTabOutput(target.id)?.takeTailLines(TAIL_LINES)
                            )
                        )
                    }
                }
            }
        )
        val invocationEnv = LocalShell.environment(DesktopContext.instance)
        val backend = Pty4jSessionBackend(
            argv = resolution.interactiveArgv,
            workingDirectory = cwd,
            environment = invocationEnv,
            rows = DEFAULT_ROWS,
            columns = DEFAULT_COLUMNS
        )
        session = TerminalSession(TRANSCRIPT_ROWS, client, backend)
        session.updateSize(DEFAULT_COLUMNS, DEFAULT_ROWS)
        return session to client
    }

    private fun monitorBackgroundExit(tabId: String) {
        monitorScope.launch {
            var seenMarker = false
            var lastOutputLen = -1
            while (true) {
                val tab = tab(tabId) ?: return@launch
                if (tab.runState !is RunState.Running) return@launch
                val output = getTabOutput(tabId) ?: return@launch
                if (!seenMarker) {
                    if (output.length != lastOutputLen) {
                        lastOutputLen = output.length
                        if (extractExitCode(output) != null) seenMarker = true
                    }
                } else {
                    delay(EXIT_MARKER_GRACE_MS)
                    val current = tab(tabId) ?: return@launch
                    if (current.runState is RunState.Running) {
                        val exitCode = extractExitCode(getTabOutput(tabId) ?: "") ?: 0
                        current.runState = RunState.Finished(exitCode)
                        bumpRevision()
                        FileLogger.i(TAG, "兜底：标签 $tabId 检测到退出标记，强制收尾 exit=$exitCode")
                        if (current.notifyOnExit && !current.finishedNotified) {
                            current.finishedNotified = true
                            _tabFinishedEvents.tryEmit(
                                TabFinishedEvent(
                                    current.id, current.title, current.command, exitCode,
                                    current.sourceSessionId,
                                    tailOutput = getTabOutput(current.id)?.takeTailLines(TAIL_LINES)
                                )
                            )
                        }
                    }
                    return@launch
                }
                delay(EXIT_MARKER_POLL_MS)
            }
        }
    }

    private fun extractExitCode(output: String): Int? {
        val tail = output.takeLast(1000)
        val idx = tail.lastIndexOf(EXIT_MARKER_PREFIX)
        if (idx < 0) return null
        if (idx > 0 && tail[idx - 1] != '\n' && tail[idx - 1] != '\r') return null
        var end = idx + EXIT_MARKER_PREFIX.length
        if (end >= tail.length || !tail[end].isDigit()) return null
        var code = 0
        while (end < tail.length && tail[end].isDigit()) {
            code = code * 10 + (tail[end] - '0')
            end++
        }
        return if (end < tail.length && tail[end] == ']') code else null
    }

    private fun addTab(tab: TerminalTab) {
        _tabs.value = _tabs.value + tab
        bumpRevision()
    }

    private fun writeToSession(session: TerminalSession, text: String) {
        val bytes = text.toByteArray(Charsets.UTF_8)
        session.write(bytes, 0, bytes.size)
    }

    private fun bumpRevision() {
        _revision.value = _revision.value + 1
    }
}
