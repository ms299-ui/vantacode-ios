/*
 * VantaCode Desktop — agent-live notification: tray balloon on session state transitions
 * (start/complete) instead of a persistent ongoing notification.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.presentation.notification

import com.vantacode.core.platform.TrayNotifier
import com.vantacode.core.ui.Strings
import com.vantacode.feature.settings.data.repository.LiveNotificationSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AI 工作会话的实时通知：进入/完成 edge-trigger 式弹托盘气泡（不刷屏）。
 * 持续状态不做常驻展示——桌面端的可见性由主窗口本身承担。
 */
@Singleton
class AgentLiveNotificationManager @Inject constructor(
    private val settings: LiveNotificationSettingsRepository
) {
    /** 一个运行中的会话在通知里的展示信息。 */
    data class RunningSession(
        val sessionId: String,
        val title: String,
        val statusLabel: String
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val sessions = MutableStateFlow<List<RunningSession>>(emptyList())

    init {
        scope.launch {
            var previouslyRunning = false
            sessions.collect { running ->
                val isRunning = running.isNotEmpty()
                if (isRunning && !previouslyRunning && settings.isEnabled()) {
                    val title = if (running.size == 1) {
                        running.first().statusLabel
                    } else {
                        Strings.formatted(Strings.notification_agent_live_title_multi, running.size)
                    }
                    TrayNotifier.notify(title, running.joinToString("\n") { "${it.title} · ${it.statusLabel}" })
                }
                previouslyRunning = isRunning
            }
        }
    }

    /** 推入当前运行中的会话列表；空列表等价于撤销。 */
    fun update(running: List<RunningSession>) {
        sessions.value = running
    }

    /** 立即清空状态。 */
    fun dismiss() {
        sessions.value = emptyList()
    }
}
