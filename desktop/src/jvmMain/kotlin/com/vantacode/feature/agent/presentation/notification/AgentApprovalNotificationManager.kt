/*
 * VantaCode Desktop — approval-pending notification: tray balloon when sessions become
 * blocked awaiting user approval (permission / question / plan).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.presentation.notification

import com.vantacode.core.platform.TrayNotifier
import com.vantacode.core.ui.Strings
import com.vantacode.feature.settings.data.repository.ApprovalNotificationSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 授权审批通知：AI 在后台阻塞等待用户确认时弹出托盘气泡 + 请求用户注意。
 * 展示后不重复弹同一批（直到列表清空重新积累）。
 */
@Singleton
class AgentApprovalNotificationManager @Inject constructor(
    private val settings: ApprovalNotificationSettingsRepository
) {
    /** 待审批项的类型，决定提醒语气。 */
    enum class ApprovalKind { PERMISSION, QUESTION, PLAN }

    /** 一个等待用户审批的会话在通知里的展示信息。 */
    data class PendingApproval(
        val sessionId: String,
        val sessionTitle: String,
        val label: String,
        val kind: ApprovalKind,
        val requestId: String?
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val approvals = MutableStateFlow<List<PendingApproval>>(emptyList())

    init {
        scope.launch {
            var notifiedFor = emptySet<String>()
            approvals.collect { pending ->
                val currentIds = pending.map { it.requestId ?: it.sessionId + it.label }.toSet()
                if (pending.isEmpty()) {
                    notifiedFor = emptySet()
                    return@collect
                }
                if (!settings.isEnabled()) return@collect
                if (currentIds == notifiedFor) return@collect
                notifiedFor = currentIds
                val title = if (pending.size == 1) {
                    Strings.notification_approval_title
                } else {
                    Strings.formatted(Strings.notification_approval_multi_title, pending.size)
                }
                val body = pending.joinToString("\n") { "${it.sessionTitle} · ${it.label}" }
                TrayNotifier.notify(title, body)
            }
        }
    }

    /** 推入当前等待审批的会话列表；空列表等价于撤销。 */
    fun update(pending: List<PendingApproval>) {
        approvals.value = pending
    }

    /** 立即清空状态。 */
    fun dismiss() {
        approvals.value = emptyList()
    }
}
