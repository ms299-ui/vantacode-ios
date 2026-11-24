/*
 * VantaCode Desktop — bridge between the git credential helper and the app: watches
 * ~/.aicode/cred-req-* files, surfaces them as a StateFlow for the global dialog,
 * writes cred-resp-* responses on user input.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.credentials.data

import com.vantacode.core.platform.PlatformPaths
import com.vantacode.core.util.FileLogger
import com.vantacode.feature.credentials.domain.model.GitCredential
import com.vantacode.feature.credentials.domain.repository.CredentialRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The local git credential helper (~/.aicode/git-credential-aicode) writes
 * `cred-req-<id>` into ~/.aicode when git asks for credentials without an answer.
 * This bridge polls that directory (1s cadence — portable, no inotify needed),
 * surfaces [request] as state for the global Compose dialog, and writes
 * `cred-resp-<id>` on user input — the helper picks that up and feeds git.
 */
@Singleton
class CredentialRequestBridge @Inject constructor(
    private val credentialRepository: CredentialRepository
) {
    private companion object {
        const val TAG = "CredentialRequestBridge"
        const val REQ_PREFIX = "cred-req-"
        const val RESP_PREFIX = "cred-resp-"
        const val POLL_MS = 1_000L
        const val SEEN_CAP = 64
    }

    data class CredentialRequest(
        val requestId: String,
        val host: String
    )

    private val _request = MutableStateFlow<CredentialRequest?>(null)
    val request: StateFlow<CredentialRequest?> = _request.asStateFlow()

    private val seen = LinkedHashSetWithCap(SEEN_CAP)
    private val seenMutex = Mutex()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var started = false

    private val aicodeDir: File
        get() = PlatformPaths.agentConfigDir.apply { mkdirs() }

    fun start() {
        if (started) return
        started = true
        val dir = aicodeDir
        cleanupStale(dir)
        scope.launch { pollLoop(dir) }
        FileLogger.i(TAG, "已开始轮询凭据请求目录 ${dir.absolutePath}")
    }

    private suspend fun pollLoop(dir: File) {
        while (true) {
            delay(POLL_MS)
            runCatching {
                dir.listFiles { f -> f.name.startsWith(REQ_PREFIX) && !f.name.endsWith(".tmp") }
                    ?.forEach { f -> handleRequest(f, f.name) }
            }
        }
    }

    /** Startup cleanup of stale requests/responses so a crash doesn't trigger phantom dialogs. */
    private fun cleanupStale(dir: File) {
        runCatching {
            dir.listFiles { f -> f.name.startsWith(REQ_PREFIX) || f.name.startsWith(RESP_PREFIX) }
                ?.forEach { it.delete() }
        }
    }

    private suspend fun handleRequest(file: File, name: String) {
        val requestId = name.removePrefix(REQ_PREFIX)
        val fresh = seenMutex.withLock { seen.addCapped(requestId) }
        if (!fresh) return
        if (!file.exists() || file.length() == 0L) return
        val text = runCatching { file.readText() }.getOrNull() ?: return
        val host = text.lineSequence()
            .firstOrNull { it.startsWith("host=") }
            ?.removePrefix("host=")
            ?.trim()
            ?.lowercase()
            ?: ""
        if (host.isBlank()) return
        FileLogger.i(TAG, "收到凭据请求 host=$host id=$requestId")
        _request.value = CredentialRequest(requestId, host)
    }

    /** User filled the form: write resp KV + persist credential + clear state. */
    fun respond(requestId: String, host: String, username: String, token: String) {
        val dir = aicodeDir
        scope.launch {
            writeRespAtomically(dir, requestId, "username=$username\npassword=$token\n")
            runCatching {
                val h = host.trim().lowercase()
                credentialRepository.save(
                    GitCredential(id = h, host = h, username = username.trim(), token = token)
                )
            }.onFailure { FileLogger.e(TAG, "保存回填凭据失败", it) }
            clearIfCurrent(requestId)
            FileLogger.i(TAG, "已回填凭据 id=$requestId host=$host")
        }
    }

    /** User cancelled: write cancel response (git fails with auth error, like no credentials). */
    fun cancel(requestId: String) {
        val dir = aicodeDir
        scope.launch {
            writeRespAtomically(dir, requestId, "cancel=1\n")
            clearIfCurrent(requestId)
            FileLogger.i(TAG, "用户取消凭据弹窗 id=$requestId")
        }
    }

    private fun clearIfCurrent(requestId: String) {
        if (_request.value?.requestId == requestId) _request.value = null
    }

    /** .tmp → rename dance so the polling helper never reads a partial file. */
    private fun writeRespAtomically(dir: File, requestId: String, content: String) {
        val target = File(dir, RESP_PREFIX + requestId)
        val tmp = File(dir, "$RESP_PREFIX${requestId}.tmp")
        runCatching {
            tmp.writeText(content)
            if (target.exists()) target.delete()
            if (!tmp.renameTo(target)) {
                target.writeText(content)
                tmp.delete()
            }
        }.onFailure { FileLogger.e(TAG, "写凭据响应文件失败", it) }
    }
}

/** Bounded LIFO-ish set used for request-id dedup. */
private class LinkedHashSetWithCap(private val cap: Int) : LinkedHashSet<String>() {
    fun addCapped(id: String): Boolean {
        if (contains(id)) return false
        add(id)
        if (size > cap) {
            iterator().next().let { remove(it) }
        }
        return true
    }
}
