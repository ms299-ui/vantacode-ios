/*
 * VantaCode Desktop — 终端字体管理（桌面版）。
 * 内置字体直接读 classpath 资源；用户导入的 ttf/otf 存放于应用私有目录。
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.terminal.domain.font

import com.vantacode.core.platform.DesktopContext
import java.io.File

object TerminalFontManager {
    /** 内置字体的伪路径。不是真实文件路径，校验 fontPath 存在性的地方要先放行它。 */
    const val BUILTIN_PATH = "builtin:jetbrains-mono-nl"

    private const val BUILTIN_ASSET = "/composeResources/font/jetbrains_mono_nl.ttf"
    private const val DIR_NAME = "terminal_fonts"

    fun fontsDir(context: DesktopContext): File =
        File(context.getFilesDir(), DIR_NAME).apply { mkdirs() }

    /** 读取字体字节：内置字体读 classpath，其余按绝对路径读盘。不存在返回 null。 */
    fun readFontBytes(path: String): ByteArray? = runCatching {
        if (path == BUILTIN_PATH) {
            TerminalFontManager::class.java.getResourceAsStream(BUILTIN_ASSET)?.readBytes()
        } else {
            File(path).takeIf { it.isFile }?.readBytes()
        }
    }.getOrNull()
}
