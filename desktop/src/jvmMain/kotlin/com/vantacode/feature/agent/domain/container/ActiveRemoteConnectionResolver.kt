/*
 * VantaCode Desktop — resolver for the currently-active remote connection.
 * The Android version went through container profiles; the desktop maps the user's
 * selected connection (RemoteConnectionSelectionRepository) straight onto a config.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.domain.container

import com.vantacode.core.security.KeystoreCipher
import com.vantacode.feature.settings.data.repository.RemoteConnectionSelectionRepository
import com.vantacode.feature.settings.data.repository.DEFAULT_REMOTE_WORKSPACE_ROOT
import com.vantacode.feature.workspace.data.local.dao.RemoteConnectionDao
import com.vantacode.feature.workspace.domain.remote.RemoteAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 当前激活的远程连接配置解析器：选中的连接（或没有时为 null）解析为可直接交给
 * [RemoteSshConnection.connect] 的 [RemoteConnectionConfig]。修改连接、切换选中项都会
 * 实时重新解析，连接即时生效。
 *
 * Remote workspace 根目录：所有远程服务器统一用 [DEFAULT_REMOTE_WORKSPACE_ROOT]
 *（桌面端没有「每个 profile 各配一个远程路径」的概念）。
 */
@Singleton
class ActiveRemoteConnectionResolver @Inject constructor(
    private val selection: RemoteConnectionSelectionRepository,
    private val dao: RemoteConnectionDao,
    private val loginKeyStore: SshLoginKeyStore
) {
    val activeConfigFlow: Flow<RemoteConnectionConfig?> = combine(
        selection.activeIdFlow,
        dao.getAllConnections()
    ) { activeId, connections ->
        val id = activeId ?: return@combine null
        val conn = connections.firstOrNull { it.id == id } ?: return@combine null
        val auth = if (conn.authType.equals("PASSWORD", ignoreCase = true)) {
            RemoteAuth.Password(KeystoreCipher.decryptString(conn.authData))
        } else {
            val passphrase = loginKeyStore.entries().firstOrNull { it.path == conn.authData }?.passphrase
                ?: conn.passphrase?.let { KeystoreCipher.decryptString(it) }
            RemoteAuth.PrivateKey(
                privateKeyPath = conn.authData,
                passphrase = passphrase
            )
        }
        RemoteConnectionConfig(
            host = conn.host,
            port = conn.port,
            username = conn.username,
            auth = auth,
            remoteWorkspacePath = DEFAULT_REMOTE_WORKSPACE_ROOT
        )
    }.distinctUntilChanged()
}
