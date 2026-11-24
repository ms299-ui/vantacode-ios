/*
 * VantaCode Desktop — terminal session client + key modifiers.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.presentation.component

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.termux.terminal.TerminalSession
import com.termux.terminal.TerminalSessionClient
import com.vantacode.core.platform.AppClipboard
import com.vantacode.core.util.FileLogger

/**
 * Extra on-screen key strip state (Esc/Ctrl/Alt etc.).
 * One-shot modifiers auto-reset after a character is emitted.
 */
class TerminalKeyModifiers {
    var ctrl by mutableStateOf(false)
    var alt by mutableStateOf(false)
    var shift by mutableStateOf(false)
    var fn by mutableStateOf(false)

    fun consume() {
        ctrl = false
        alt = false
        shift = false
        fn = false
    }
}

/**
 * [TerminalSessionClient] implementation for the desktop Compose terminal:
 * forwards screen-change callbacks (via [onScreenChanged]) and bridges the system clipboard.
 */
class AppTerminalSessionClient(
    private val onScreenChanged: () -> Unit,
    private val onFinished: (TerminalSession) -> Unit,
    var cursorStyle: Int = 0
) : TerminalSessionClient {

    private companion object { const val TAG = "TerminalSession" }

    override fun onTextChanged(changedSession: TerminalSession) {
        onScreenChanged()
    }

    override fun onTitleChanged(changedSession: TerminalSession) { /* 暂不展示标题 */ }

    override fun onSessionFinished(finishedSession: TerminalSession) {
        onFinished(finishedSession)
    }

    override fun onCopyTextToClipboard(session: TerminalSession, text: String?) {
        if (text.isNullOrEmpty()) return
        AppClipboard.setText(text)
    }

    override fun onPasteTextFromClipboard(session: TerminalSession?) {
        val text = AppClipboard.getText() ?: return
        val bytes = text.toByteArray(Charsets.UTF_8)
        session?.write(bytes, 0, bytes.size)
    }

    override fun onBell(session: TerminalSession) { /* 忽略响铃 */ }
    override fun onColorsChanged(session: TerminalSession) { onScreenChanged() }
    override fun onTerminalCursorStateChange(state: Boolean) {}
    override fun getTerminalCursorStyle(): Int? = cursorStyle

    override fun logError(tag: String?, message: String?) { FileLogger.e(tag ?: TAG, message ?: "") }
    override fun logWarn(tag: String?, message: String?) { FileLogger.w(tag ?: TAG, message ?: "") }
    override fun logInfo(tag: String?, message: String?) { FileLogger.i(tag ?: TAG, message ?: "") }
    override fun logDebug(tag: String?, message: String?) { FileLogger.d(tag ?: TAG, message ?: "") }
    override fun logVerbose(tag: String?, message: String?) { FileLogger.d(tag ?: TAG, message ?: "") }
    override fun logStackTraceWithMessage(tag: String?, message: String?, e: Exception?) {
        FileLogger.e(tag ?: TAG, message ?: "", e)
    }
    override fun logStackTrace(tag: String?, e: Exception?) { FileLogger.e(tag ?: TAG, "", e) }
}
