/*
 * VantaCode Desktop — 终端页：Compose 原生渲染 PTY 终端（pty4j / SSH 后端）。
 * 独立路由：「终端」按钮 → 全屏终端；会话由 TerminalSessionManager 进程级托管。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vantacode.core.ui.R
import com.vantacode.di.AppGraph
import com.vantacode.feature.terminal.domain.TerminalTab
import com.vantacode.feature.terminal.domain.RunState
import com.termux.terminal.TerminalSession
import com.termux.terminal.TerminalBuffer
import com.termux.terminal.TextStyle
import com.vantacode.core.ui.Strings
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.Plus
import compose.icons.feathericons.X
import com.vantacode.core.theme.Spacing

private val CodeFontFamily: FontFamily = run {
    val bytes = runCatching {
        object {}.javaClass.getResourceAsStream("/composeResources/font/jetbrains_mono_nl.ttf")?.readBytes()
    }.getOrNull()
    if (bytes != null) {
        FontFamily(androidx.compose.ui.text.platform.Font("JetBrains Mono NL", bytes))
    } else {
        FontFamily.Monospace
    }
}

private const val FontSizeSp = 13
private const val LineHeightDp = 16

private val TerminalBackground = Color(0xFF141923)
private val TerminalSurfaceColor = Color(0xFF0F141D)
private val TerminalForeground = Color(0xFFD8DEE9)
private val TerminalCursor = Color(0xFF35E28B)
private val TerminalDim = Color(0xFF6B7280)

// ANSI 16 色（Termius 风格）
private val ANSI_COLORS = arrayOf(
    Color(0xFF1B222D), Color(0xFFE05252), Color(0xFF35E28B), Color(0xFFF0AF39),
    Color(0xFF4390F7), Color(0xFFB362FF), Color(0xFF2ED1D8), Color(0xFFD8DEE9),
    Color(0xFF4C566A), Color(0xFFFF6E6E), Color(0xFF5DFFA9), Color(0xFFFFC766),
    Color(0xFF6CA8FF), Color(0xFFCA8BFF), Color(0xFF5DF2F9), Color(0xFFECEFF4),
)

private fun ansiColor(index: Int): Color {
    if (index in 0..15) return ANSI_COLORS[index]
    if (index in 16..231) {
        val v = index - 16
        val r = v / 36
        val g = (v % 36) / 6
        val b = v % 6
        fun level(x: Int) = if (x == 0) 0 else 55 + x * 40
        return Color(level(r), level(g), level(b))
    }
    if (index in 232..255) {
        val gray = 8 + (index - 232) * 10
        return Color(gray, gray, gray)
    }
    return TerminalForeground
}

private fun fgOf(style: Long): Color {
    val v = TextStyle.decodeForeColor(style)
    return if (v < 0) Color(v) else ansiColor(v)
}

private fun bgOf(style: Long): Color? {
    val v = TextStyle.decodeBackColor(style)
    return if (v == TextStyle.COLOR_INDEX_BACKGROUND) null else if (v < 0) Color(v) else ansiColor(v)
}

private fun eff(style: Long, bit: Int) = (TextStyle.decodeEffect(style) and bit) != 0
private fun isBold(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_BOLD)
private fun isItalic(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_ITALIC)
private fun isUnderline(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_UNDERLINE)
private fun isStrike(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_STRIKETHROUGH)
private fun isDim(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_DIM)
private fun isInvisible(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_INVISIBLE)
private fun isInverse(style: Long) = eff(style, TextStyle.CHARACTER_ATTRIBUTE_INVERSE)

/** 终端页。全屏；会话由 TerminalSessionManager 托管、跨页面不丢失。 */
@Composable
fun TerminalScreen(onNavigateBack: () -> Unit) {
    val manager = AppGraph.terminalSessionManager
    val tabs by manager.tabs.collectAsStateWithLifecycle()
    val activeTabId by manager.activeTabId.collectAsStateWithLifecycle()
    val revision by manager.revision.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { manager.ensureInitialTab() }

    Column(modifier = Modifier.fillMaxSize().background(TerminalBackground)) {
        // 顶栏：返回 + 标签条 + 新建
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(TerminalSurfaceColor)
                .padding(horizontal = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onNavigateBack, modifier = Modifier.size(36.dp)) {
                Icon(FeatherIcons.ChevronLeft, contentDescription = Strings.common_back, tint = Color(0xFFD8DEE9), modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(Spacing.xs))
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(tabs, key = { it.id }) { tab ->
                    TerminalTabChip(
                        tab = tab,
                        isActive = tab.id == activeTabId,
                        onSelect = { manager.activate(tab.id) },
                        onClose = { manager.closeTab(tab.id) },
                    )
                }
            }
            IconButton(
                onClick = { kotlinx.coroutines.runBlocking { manager.createInteractiveTab() } },
                modifier = Modifier.size(36.dp),
            ) {
                Icon(FeatherIcons.Plus, contentDescription = "新建终端", tint = Color(0xFFD8DEE9), modifier = Modifier.size(18.dp))
            }
        }

        val activeTab = tabs.firstOrNull { it.id == activeTabId }
        if (activeTab == null) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("暂无终端标签", color = TerminalDim, style = MaterialTheme.typography.bodyMedium)
            }
            return
        }

        Surface(
            modifier = Modifier.weight(1f).fillMaxWidth().background(TerminalBackground),
            color = TerminalBackground,
        ) {
            TerminalSurface(activeTab, revision)
        }
    }
}

/** 标签 chip：运行状态点 + 名称 + 关闭键。 */
@Composable
private fun TerminalTabChip(
    tab: TerminalTab,
    isActive: Boolean,
    onSelect: () -> Unit,
    onClose: () -> Unit,
) {
    val bgColor = if (isActive) Color(0xFF1F2937) else Color.Transparent
    val textColor = if (isActive) Color(0xFFECEFF4) else Color(0xFF9CA3AF)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable(onClick = onSelect)
            .height(32.dp)
            .padding(horizontal = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (tab.runState is RunState.Running) Color(0xFF35E28B) else Color(0xFF4B5563)),
        )
        Spacer(Modifier.width(Spacing.xs))
        Text(tab.title, color = textColor, fontSize = 12.sp, maxLines = 1)
        Spacer(Modifier.width(Spacing.xs))
        IconButton(onClick = onClose, modifier = Modifier.size(22.dp)) {
            Icon(FeatherIcons.X, contentDescription = "关闭", tint = textColor, modifier = Modifier.size(12.dp))
        }
    }
}

/** 终端网格：Column(Text) 渲染 + 光标叠层；重绘由 revision 驱动。 */
@Composable
private fun TerminalSurface(activeTab: TerminalTab, revision: Int) {
    val session = activeTab.session
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val lineHeightDp = LineHeightDp.dp
    val bodyFontStyle = remember {
        androidx.compose.ui.text.TextStyle(fontFamily = CodeFontFamily, fontSize = FontSizeSp.sp)
    }
    val cellWidthPx = remember(textMeasurer) {
        textMeasurer.measure("W", bodyFontStyle).size.width.toFloat()
    }
    val cellWidthDp = with(density) { cellWidthPx.toDp() }

    androidx.compose.foundation.layout.BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(TerminalBackground)
            .focusRequester(focusRequester)
            .focusable()
            .onPreviewKeyEvent { handleTerminalKeyEvent(it, session) }
            .pointerInput(Unit) { detectTapGestures { focusRequester.requestFocus() } },
    ) {
        val emulator = session.getEmulator()
        val cols = minOf((constraints.maxWidth / cellWidthPx).toInt().coerceAtLeast(20), emulator.mColumns)
        val rows = (constraints.maxHeight / with(density) { lineHeightDp.toPx() }).toInt().coerceAtLeast(4)
        // 首次布局时会话可能尚未 resize 完成（尺寸与窗口不同），渲染始终按会话当前尺寸画，避免越界
        val actualRows = emulator.mRows
        LaunchedEffect(emulator.mColumns) { session.updateSize(cols, rows) }

        val screen = emulator.screen
        val rowsToDraw = minOf(rows, actualRows)
        // revision 被读取 → 有变更时整体重组重绘
        @Suppress("UNUSED_VARIABLE")
        val trigger = revision
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.xs)
                .background(TerminalBackground),
        ) {
            for (r in 0 until rowsToDraw) {
                Text(
                    text = buildTerminalRow(screen, r, cols),
                    fontFamily = CodeFontFamily,
                    fontSize = FontSizeSp.sp,
                    lineHeight = LineHeightDp.sp,
                    softWrap = false,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Clip,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(lineHeightDp),
                )
            }
        }

        // 光标叠层
        if (emulator.shouldCursorBeVisible() && emulator.isCursorEnabled()) {
            val cursorRow = emulator.getCursorRow()
            val cursorCol = emulator.getCursorCol()
            if (cursorRow in 0 until rowsToDraw) {
                Box(
                    modifier = Modifier
                        .offset(x = Spacing.xs + cellWidthDp * cursorCol, y = lineHeightDp * cursorRow)
                        .size(cellWidthDp, lineHeightDp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TerminalCursor.copy(alpha = 0.35f)),
                )
            }
        }
    }
}

/** 构造一行的 AnnotatedString：按样式分片，每段 SpanStyle。 */
private fun buildTerminalRow(screen: TerminalBuffer, row: Int, cols: Int): AnnotatedString =
    buildAnnotatedString {
        var col = 0
        while (col < cols) {
            val raw = screen.getStyleAt(row, col)
            var end = col + 1
            while (end < cols && screen.getStyleAt(row, end) == raw) end++
            val fg = fgOf(raw)
            val bg = bgOf(raw)
            val decorations = mutableSetOf<androidx.compose.ui.text.style.TextDecoration>()
            if (isUnderline(raw)) decorations += androidx.compose.ui.text.style.TextDecoration.Underline
            if (isStrike(raw)) decorations += androidx.compose.ui.text.style.TextDecoration.LineThrough
            val invisible = isInvisible(raw)
            val dim = isDim(raw)
            withStyle(
                SpanStyle(
                    color = if (invisible) Color.Transparent else if (dim) fg.copy(alpha = 0.55f) else fg,
                    background = bg ?: Color.Unspecified,
                    fontWeight = if (isBold(raw)) FontWeight.Bold else FontWeight.Normal,
                    fontStyle = if (isItalic(raw)) FontStyle.Italic else FontStyle.Normal,
                    textDecoration = when {
                        decorations.size == 1 -> decorations.first()
                        decorations.size > 1 -> androidx.compose.ui.text.style.TextDecoration.combine(decorations.toList())
                        else -> null
                    },
                ),
            ) {
                if (!invisible) {
                    append(screen.getSelectedText(col, row, end - 1, row, false))
                }
            }
            col = end
        }
    }

/** 键盘 → PTY 桥。返回 true = 已消费。 */
private fun handleTerminalKeyEvent(event: KeyEvent, session: TerminalSession): Boolean {
    if (event.type != KeyEventType.KeyDown) return false
    val cp = event.utf16CodePoint
    val isCtrl = event.isCtrlPressed
    val isAlt = event.isAltPressed
    val isShift = event.isShiftPressed
    val emulator = session.getEmulator()
    val appCursor = emulator.isCursorKeysApplicationMode()
    fun send(text: String) { val b = text.toByteArray(Charsets.UTF_8); session.write(b, 0, b.size) }
    when (event.key) {
        Key.Backspace -> send(if (isShift) "\b" else "\u007f")
        Key.Enter -> send("\r")
        Key.Tab -> send("\t")
        Key.Escape -> send("\u001b")
        Key.DirectionLeft -> send(if (appCursor) "\u001b[D" else "\u001bOD")
        Key.DirectionRight -> send(if (appCursor) "\u001b[C" else "\u001bOC")
        Key.DirectionUp -> send(if (appCursor) "\u001b[A" else "\u001bOA")
        Key.DirectionDown -> send(if (appCursor) "\u001b[B" else "\u001bOB")
        Key.MoveHome -> send(if (appCursor) "\u001b[H" else "\u001b[1~")
        Key.MoveEnd -> send(if (appCursor) "\u001b[F" else "\u001b[4~")
        Key.Delete -> send("\u001b[3~")
        else -> {
            if (isCtrl && cp != 0) {
                val ch = cp.toChar().lowercaseChar()
                if (ch in 'a'..'z') {
                    send((ch.code and 0x1f).toChar().toString())
                    return true
                }
            }
            if (isAlt && cp != 0) {
                send("\u001b" + cp.toChar())
                return true
            }
            if (cp != 0) {
                send(String(Character.toChars(cp)))
                return true
            }
            return false
        }
    }
    return true
}
