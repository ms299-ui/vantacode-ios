package com.vantacode.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.vantacode.core.ui.R
import com.vantacode.core.ui.LogoVectors

/**
 * 容器系统类型 → 单色 logo；未识别（含 null）返回 null，由调用方回退通用容器图标。
 * 「容器与镜像」列表与文件树的当前容器根共用这一套映射，保证两处图标一致。
 */
@Composable
fun containerOsLogo(osId: String?): Painter? = when (osId) {
    "alpine" -> rememberVectorPainter(LogoVectors.Alpine)
    "centos" -> rememberVectorPainter(LogoVectors.Centos)
    "ubuntu" -> rememberVectorPainter(LogoVectors.Ubuntu)
    "debian" -> rememberVectorPainter(LogoVectors.Debian)
    else -> null
}
