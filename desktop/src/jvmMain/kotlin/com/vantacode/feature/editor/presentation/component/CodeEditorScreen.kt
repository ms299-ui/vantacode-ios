package com.vantacode.feature.editor.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vantacode.core.platform.PlatformToast
import com.vantacode.core.theme.Spacing
import com.vantacode.core.ui.Strings
import com.vantacode.feature.git.presentation.component.highlightCode
import com.vantacode.feature.git.presentation.component.inferSyntaxLanguage
import com.vantacode.feature.workspace.domain.FileAccessProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MAX_EDITOR_BYTES = 3_000_000L

/**
 * 桌面端内置文本编辑器：BasicTextField + highlights-jvm 语法着色。
 *
 * 相比 Android 版（sora-editor）做了取舍：保留「打开文件 / 编辑 / 保存 / 未保存保护 /
 * 语法高亮 / 冲突检测」主链路，暂不做缩略图、撤销栈可视化、缩进参考线等细节。
 */
@Composable
internal fun CodeEditorScreen(
    path: String,
    fileAccess: FileAccessProvider,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var fieldValue by remember(path) { mutableStateOf(TextFieldValue("")) }
    var original by remember(path) { mutableStateOf("") }
    var savedFingerprint by remember(path) { mutableStateOf<String?>(null) }
    var loading by remember(path) { mutableStateOf(true) }
    var error by remember(path) { mutableStateOf<String?>(null) }
    var dirty by remember(path) { mutableStateOf(false) }
    var showUnsaved by remember { mutableStateOf(false) }
    var conflict by remember { mutableStateOf(false) }

    val fileName = remember(path) { path.trimEnd('/').substringAfterLast('/') }
    val language = remember(path) { inferSyntaxLanguage(path) }
    val darkMode = MaterialTheme.colorScheme.background.luminance() < 0.5f

    // 载入文件（IO 线程），二进制 / 超大文件直接给出提示，不塞进编辑器。
    LaunchedEffect(path) {
        loading = true
        error = null
        val loaded: Result<String> = withContext(Dispatchers.IO) {
            runCatching {
                if (fileAccess.fileSize(path) > MAX_EDITOR_BYTES) {
                    throw IllegalStateException(Strings.editor_file_too_large)
                }
                val text = fileAccess.readFile(path)
                if (text.contains('\u0000')) {
                    throw IllegalStateException(Strings.editor_binary_unsupported)
                }
                text
            }
        }
        loaded.onSuccess { text ->
            original = text
            fieldValue = TextFieldValue(AnnotatedString(text), TextRange(0))
            savedFingerprint = withContext(Dispatchers.IO) { fileAccess.fingerprint(path) }
            dirty = false
        }.onFailure { error = it.message ?: Strings.editor_load_failed }
        loading = false
    }

    // 文本变化后异步重算语法高亮；只更新 annotatedString，不动光标位置。
    LaunchedEffect(fieldValue.text, language, darkMode) {
        if (fieldValue.text.isEmpty()) return@LaunchedEffect
        delay(80)
        val annotated = withContext(Dispatchers.Default) {
            runCatching { highlightCode(fieldValue.text, language, darkMode) }.getOrNull()
        }
        if (annotated != null) {
            fieldValue = fieldValue.copy(annotatedString = annotated)
        }
    }

    fun performSave() {
        scope.launch {
            val content = fieldValue.text
            runCatching {
                withContext(Dispatchers.IO) { fileAccess.writeFile(path, content, overwrite = true) }
                original = content
                savedFingerprint = withContext(Dispatchers.IO) { fileAccess.fingerprint(path) }
                dirty = false
                PlatformToast.show(Strings.editor_save_success)
            }.onFailure {
                PlatformToast.show(Strings.editor_save_failed)
            }
        }
    }

    fun requestSave() {
        scope.launch {
            val current = withContext(Dispatchers.IO) { fileAccess.fingerprint(path) }
            if (savedFingerprint != null && current != null && current != savedFingerprint) {
                conflict = true
            } else {
                performSave()
            }
        }
    }

    fun requestClose() {
        if (dirty) showUnsaved = true else onClose()
    }

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // 顶部工具条
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md, vertical = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(
                text = fileName + if (dirty) " •" else "",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            TextButton(onClick = { requestSave() }, enabled = !loading && error == null) {
                Text(Strings.common_save)
            }
            TextButton(onClick = { requestClose() }) {
                Text(Strings.common_close)
            }
        }

        // 编辑区
        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            when {
                loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text(
                    text = error!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(Spacing.lg)
                )
                else -> {
                    val scroll = rememberScrollState()
                    BasicTextField(
                        value = fieldValue,
                        onValueChange = { newValue ->
                            fieldValue = newValue
                            dirty = newValue.text != original
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scroll)
                            .padding(horizontal = Spacing.md, vertical = Spacing.sm)
                            .onPreviewKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown &&
                                    event.key == Key.S &&
                                    (event.isCtrlPressed || event.isMetaPressed)
                                ) {
                                    requestSave()
                                    true
                                } else {
                                    false
                                }
                            },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }

        // 状态栏
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md, vertical = Spacing.xs),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            val offset = fieldValue.selection.start.coerceAtMost(fieldValue.text.length)
            val before = fieldValue.text.substring(0, offset)
            val line = before.count { it == '\n' } + 1
            val col = offset - before.lastIndexOf('\n')
            Text(
                text = "Ln $line, Col $col",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = language?.name ?: "Plain Text",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    if (showUnsaved) {
        AlertDialog(
            onDismissRequest = { showUnsaved = false },
            title = { Text(Strings.editor_unsaved_title) },
            text = { Text(Strings.editor_unsaved_message) },
            confirmButton = {
                TextButton(onClick = {
                    showUnsaved = false
                    performSave()
                    onClose()
                }) { Text(Strings.editor_save_and_exit) }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = {
                        showUnsaved = false
                        onClose()
                    }) { Text(Strings.editor_dont_save) }
                    TextButton(onClick = { showUnsaved = false }) { Text(Strings.common_cancel) }
                }
            }
        )
    }

    if (conflict) {
        AlertDialog(
            onDismissRequest = { conflict = false },
            title = { Text(Strings.editor_save_conflict_title) },
            text = { Text(Strings.editor_save_conflict_message) },
            confirmButton = {
                TextButton(onClick = {
                    conflict = false
                    performSave()
                }) { Text(Strings.editor_save_conflict_overwrite) }
            },
            dismissButton = {
                TextButton(onClick = { conflict = false }) { Text(Strings.common_cancel) }
            }
        )
    }
}
