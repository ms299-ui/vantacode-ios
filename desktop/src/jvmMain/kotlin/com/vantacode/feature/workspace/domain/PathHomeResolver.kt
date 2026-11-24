package com.vantacode.feature.workspace.domain

import com.vantacode.feature.agent.domain.container.RemoteSshConnection
import com.vantacode.feature.settings.data.repository.ExecutionMode
import com.vantacode.feature.settings.data.repository.ExecutionModeHolder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Home directory of the current execution environment, unified for tools that expand `~`.
 * Desktop local mode = this machine's user home; remote SSH mode = the remote user's home
 * (cached on connect). Falls back to the local user home when unknown.
 */
@Singleton
class PathHomeResolver @Inject constructor(
    private val executionModeHolder: ExecutionModeHolder,
    private val remoteSshConnection: RemoteSshConnection
) {
    private val userHome: String get() = System.getProperty("user.home", "/root")

    /** 当前环境的 home：远程取远程用户 home（连接后缓存），本地取宿主用户 home。 */
    fun home(): String = when {
        executionModeHolder.currentMode() == ExecutionMode.REMOTE_SSH ->
            remoteSshConnection.remoteHome ?: userHome
        else -> userHome
    }

    /**
     * 当前执行环境下的 AI 配置根目录（`<home>/.aicode`）：本地为 `$HOME/.aicode`，
     * 远程为服务器用户 home 下的 `.aicode`。
     */
    fun aicodeRoot(): String = home().trimEnd('/') + "/.aicode"

    /** 展开 `~` 或 `~/` 前缀为当前环境的 home 路径；其它路径原样返回。 */
    fun expandHome(path: String): String {
        val h = home()
        return when {
            path == "~" -> h
            path.startsWith("~/") -> h.trimEnd('/') + path.removePrefix("~")
            else -> path
        }
    }
}
