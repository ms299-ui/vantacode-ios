/*
 * VantaCode Desktop — process-wide file-change watch hub. Desktop port of the Android
 * implementation (inotify/FileObserver replaced by recursive snapshot polling, which the
 * Android build already used as its fallback and as its only mechanism on some devices).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.watch

import com.vantacode.core.util.FileLogger
import com.vantacode.core.platform.PlatformPaths
import com.vantacode.feature.settings.data.repository.ExecutionMode
import com.vantacode.feature.settings.data.repository.ExecutionModeHolder
import com.vantacode.feature.workspace.data.repository.WorkspaceRepository
import com.vantacode.feature.workspace.domain.PathHomeResolver
import com.vantacode.feature.workspace.domain.WorkspacePathMapper
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/**
 * App-wide file-change watch hub: watches the active workspace and `~/.aicode`
 * (or any host directory); changes are merged into batches and broadcast.
 *
 * Implementation: recursive snapshot diffing (mtime/size/type) on a short interval.
 * The Android inotify layer is gone; snapshot polling is the single mechanism.
 * Subscribers decide scope and filter rules; nothing outside a subscription is polled.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class FileChangeHub @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val pathHomeResolver: PathHomeResolver,
    private val pathMapper: WorkspacePathMapper,
    private val executionModeHolder: ExecutionModeHolder
) {
    companion object {
        private const val TAG = "FileChangeHub"

        /** Batching window: changes within the window merge into one path entry. */
        const val DEFAULT_BATCH_WINDOW_MS = 300

        /** Max entries per batch; over-limit only means "something changed". */
        private const val MAX_BATCH_SIZE = 512

        /** Snapshot poll interval. */
        private const val POLL_INTERVAL_MS = 1_000L

        /** Probe interval for a subscription root that does not exist yet. */
        private const val ROOT_WAIT_INTERVAL_MS = 2_000L

        /** Max entries per snapshot — caps traversal cost on huge trees. */
        private const val MAX_SNAPSHOT_ENTRIES = 4096

        /** Raw events channel bound; over-limit drops oldest (snapshot rebuild covers it). */
        private const val MAX_PENDING_EVENTS = 4096

        /** Pending batches channel bound; over-limit drops oldest. */
        private const val MAX_PENDING_BATCHES = 256

        /** Workspace root path for the AI. */
        const val CONTAINER_ROOT = WorkspacePathMapper.CONTAINER_ROOT

        /** Agent config directory root for the AI. */
        const val AICODE_ROOT = WorkspacePathMapper.AICODE_ROOT
    }

    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /** Two roots, recursive. */
    fun watchAll(
        filter: WatchFilter = WatchFilter.DEFAULT,
        fallbackPoll: Boolean = true,
        batchWindowMs: Int = DEFAULT_BATCH_WINDOW_MS
    ): Flow<FileChangeBatch> = merge(
        watchWorkspace(
            recursive = true,
            filter = filter,
            fallbackPoll = fallbackPoll,
            batchWindowMs = batchWindowMs
        ),
        watchAicode(
            recursive = true,
            filter = filter,
            fallbackPoll = fallbackPoll,
            batchWindowMs = batchWindowMs
        )
    )

    /**
     * Subscribe to the current workspace (rebuilds on workspace switch).
     * In remote mode the workspace lives on the server; this degrades to an empty flow.
     */
    fun watchWorkspace(
        containerSubPath: String = CONTAINER_ROOT,
        recursive: Boolean = false,
        filter: WatchFilter = WatchFilter.DEFAULT,
        fallbackPoll: Boolean = true,
        batchWindowMs: Int = DEFAULT_BATCH_WINDOW_MS
    ): Flow<FileChangeBatch> = workspaceRepository.current
        .map { workspaceRepository.currentPath() }
        .distinctUntilChanged()
        .flatMapLatest {
            if (executionModeHolder.currentMode() == ExecutionMode.REMOTE_SSH) {
                emptyFlow()
            } else {
                watchHostDir(
                    hostDir = pathMapper.toHostFile(containerSubPath),
                    containerRootPath = containerSubPath.trimEnd('/'),
                    root = ChangeRoot.WORKSPACE,
                    recursive = recursive,
                    filter = filter,
                    fallbackPoll = fallbackPoll,
                    batchWindowMs = batchWindowMs
                )
            }
        }

    /** Subscribe to a sub path of the agent config dir; empty [containerSubPath] = the dir itself. */
    fun watchAicode(
        containerSubPath: String = "",
        recursive: Boolean = false,
        filter: WatchFilter = WatchFilter.DEFAULT,
        fallbackPoll: Boolean = true,
        batchWindowMs: Int = DEFAULT_BATCH_WINDOW_MS
    ): Flow<FileChangeBatch> {
        val sub = containerSubPath.trim('/')
        return watchHostDir(
            hostDir = if (sub.isEmpty()) PlatformPaths.agentConfigDir else File(PlatformPaths.agentConfigDir, sub),
            containerRootPath = if (sub.isEmpty()) AICODE_ROOT else "$AICODE_ROOT/$sub",
            root = ChangeRoot.AICODE,
            recursive = recursive,
            filter = filter,
            fallbackPoll = fallbackPoll,
            batchWindowMs = batchWindowMs
        )
    }

    /**
     * Subscribe to any host directory (e.g. a local mirror of a remote workspace).
     * A missing directory does not end the subscription — once it appears, we start
     * reporting from then on.
     */
    fun watchHostDir(
        hostDir: File,
        containerRootPath: String? = null,
        root: ChangeRoot = ChangeRoot.OTHER,
        recursive: Boolean = false,
        filter: WatchFilter = WatchFilter.DEFAULT,
        fallbackPoll: Boolean = true,
        batchWindowMs: Int = DEFAULT_BATCH_WINDOW_MS
    ): Flow<FileChangeBatch> = flow {
        val subscription = Subscription(
            rootDir = hostDir,
            containerRootPath = containerRootPath,
            rootKind = root,
            recursive = recursive,
            filter = filter,
            fallbackPoll = fallbackPoll,
            windowMs = batchWindowMs
        )
        try {
            subscription.start()
            emitAll(subscription.batches)
        } finally {
            subscription.stop()
        }
    }

    private data class RawEvent(val hostPath: String, val kind: ChangeKind)

    /** A subscription: its own scope, filter rules, batching window and snapshot poller. */
    private inner class Subscription(
        private val rootDir: File,
        private val containerRootPath: String?,
        private val rootKind: ChangeRoot,
        private val recursive: Boolean,
        private val filter: WatchFilter,
        private val fallbackPoll: Boolean,
        private val windowMs: Int
    ) {
        private val events = Channel<RawEvent>(
            capacity = MAX_PENDING_EVENTS,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        private val batchesChannel = Channel<FileChangeBatch>(
            capacity = MAX_PENDING_BATCHES,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        val batches: Flow<FileChangeBatch> = batchesChannel.receiveAsFlow()

        @Volatile
        private var rules: IgnoreRules = IgnoreRules.of(filter, emptyList())
        private var mergeJob: Job? = null
        private var pollJob: Job? = null
        private var attachJob: Job? = null

        fun start() {
            rules = IgnoreRules.of(
                filter,
                if (filter.followGitignore) readGitignorePatterns(rootDir) else emptyList()
            )
            mergeJob = ioScope.launch { mergeLoop() }
            if (rootDir.isDirectory) {
                startPolling()
            } else {
                awaitRoot()
            }
        }

        fun stop() {
            mergeJob?.cancel()
            mergeJob = null
            pollJob?.cancel()
            pollJob = null
            attachJob?.cancel()
            attachJob = null
            batchesChannel.close()
        }

        /** Subscription root is not there yet: wait for it, treat appearance as a change. */
        private fun awaitRoot() {
            attachJob = ioScope.launch {
                while (isActive && !rootDir.isDirectory) delay(ROOT_WAIT_INTERVAL_MS)
                if (!isActive) return@launch
                events.trySend(RawEvent(rootDir.absolutePath, ChangeKind.CREATED))
                startPolling()
            }
        }

        /** Snapshot poller: recursive diff (non-recursive subscriptions compare one level). */
        private fun startPolling() {
            if (!fallbackPoll || pollJob != null) return
            pollJob = ioScope.launch {
                var last = snapshotOfScope()
                while (isActive) {
                    delay(POLL_INTERVAL_MS)
                    val now = snapshotOfScope()
                    val diff = diffSnapshot(last, now)
                    last = now
                    for ((rel, kind) in diff) {
                        val hostPath =
                            if (rel.isEmpty()) rootDir.absolutePath else File(rootDir, rel).absolutePath
                        events.trySend(RawEvent(hostPath, kind))
                    }
                }
            }
        }

        /** Snapshot keyed by root-relative path (root itself is ""); pruned dirs excluded. */
        private fun snapshotOfScope(): Map<String, String> {
            val out = HashMap<String, String>()
            if (!rootDir.isDirectory) return out
            var count = 0

            fun walk(dir: File, rel: String) {
                val children = dir.listFiles() ?: return
                for (child in children) {
                    if (count >= MAX_SNAPSHOT_ENTRIES) return
                    val childRel = if (rel.isEmpty()) child.name else "$rel/${child.name}"
                    count++
                    if (child.isDirectory) {
                        out[childRel] = "d"
                        if (recursive && !rules.isIgnoredDir(relativePartsOf(rootDir, child))) {
                            walk(child, childRel)
                        }
                    } else {
                        out[childRel] = "f:${child.lastModified()}:${child.length()}"
                    }
                }
            }

            walk(rootDir, "")
            return out
        }

        private fun containerPathOf(hostPath: String): String {
            val base = containerRootPath ?: return pathMapper.toContainerPath(hostPath)
            val rel = relativePartsOf(rootDir, File(hostPath)).joinToString("/")
            return if (rel.isEmpty()) base else "$base/$rel"
        }

        private suspend fun mergeLoop() {
            while (true) {
                val first = events.receive()
                val merged = LinkedHashMap<String, ChangeKind>()
                var truncated = false

                fun accept(event: RawEvent) {
                    val existing = merged[event.hostPath]
                    if (existing == null) {
                        if (merged.size >= MAX_BATCH_SIZE) {
                            truncated = true
                            return
                        }
                        merged[event.hostPath] = event.kind
                    } else {
                        merged[event.hostPath] = mergeKind(existing, event.kind)
                    }
                }

                accept(first)
                // Fixed window: it always fires. Debouncing would swallow bursts during builds.
                val deadline = System.currentTimeMillis() + windowMs
                while (true) {
                    val remain = deadline - System.currentTimeMillis()
                    if (remain <= 0) break
                    val next = withTimeoutOrNull(remain) { events.receive() } ?: break
                    accept(next)
                }

                val changes = merged.map { (path, kind) ->
                    FileChange(rootKind, path, containerPathOf(path), kind)
                }
                batchesChannel.send(FileChangeBatch(changes, truncated))
            }
        }
    }
}
