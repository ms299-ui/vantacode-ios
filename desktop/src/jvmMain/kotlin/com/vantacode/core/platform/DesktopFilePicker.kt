/*
 * VantaCode Desktop — AWT-based file pickers (replaces Android ActivityResultContracts).
 * All dialogs run synchronously on the AWT event thread; call sites get blocking results.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter

object DesktopFilePicker {
    /** Pick an existing file; null if cancelled. [filter] is a FileDialog-style pattern, e.g. "*.png". */
    fun pickFile(parent: Frame? = null, title: String = "打开", filter: String? = null): File? {
        val dialog = FileDialog(parent, title, FileDialog.LOAD)
        if (filter != null) dialog.file = filter
        dialog.isVisible = true
        return selectedFile(dialog)
    }

    /** Pick a path for saving; null if cancelled. */
    fun pickSaveFile(parent: Frame? = null, title: String = "保存", defaultName: String? = null, filter: String? = null): File? {
        val dialog = FileDialog(parent, title, FileDialog.SAVE)
        if (defaultName != null) dialog.file = defaultName
        if (filter != null) dialog.file = filter
        dialog.isVisible = true
        return selectedFile(dialog)
    }

    private fun selectedFile(dialog: FileDialog): File? {
        val dir = dialog.directory ?: return null
        val name = dialog.file ?: return null
        if (dir.isBlank() || name.isBlank()) return null
        return File(dir, name)
    }

    /** Pick an existing directory; null if cancelled. */
    fun pickDirectory(parent: Frame? = null, title: String = "选择文件夹"): File? {
        val chooser = JFileChooser().apply {
            dialogTitle = title
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
        }
        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return null
        return chooser.selectedFile?.takeIf { it.isDirectory }
    }

    /** Pick several files at once (OpenMultipleDocuments). Null if cancelled. [filter] e.g. "*.png;*.jpg". */
    fun pickFiles(parent: Frame? = null, title: String = "打开", filter: String? = null): List<File>? {
        // AWT FileDialog 的多选是平台相关实现（Windows 下 setMultipleMode 会抛
        // UnsupportedOperationException），这里统一走 Swing 的 JFileChooser。
        val chooser = JFileChooser().apply {
            dialogTitle = title
            isMultiSelectionEnabled = true
            fileSelectionMode = JFileChooser.FILES_ONLY
            filter?.let { pattern ->
                fileFilter = object : FileFilter() {
                    override fun accept(f: File): Boolean =
                        f.isDirectory || pattern.split(';').any { p -> f.name.endsWith(p.trim().removePrefix("*")) }

                    override fun getDescription(): String = filter
                }
            }
        }
        val result = chooser.showOpenDialog(parent)
        if (result != JFileChooser.APPROVE_OPTION) return null
        val files = chooser.selectedFiles
        if (files.isEmpty()) return null
        return files.filter { it.exists() }
    }
}
