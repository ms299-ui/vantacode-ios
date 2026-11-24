/*
 * VantaCode Desktop — the platform context passed where the Android app used
 * android.content.Context. Provides the small surface actually used by the
 * ported code (filesDir/cacheDir/assets/shared_prefs/package info).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.io.File
import java.io.InputStream

class DesktopContext private constructor() {
    val packageName: String get() = PlatformInfo.PACKAGE_NAME

    /** Mirrors Context.applicationInfo for the few fields read (versionName/code). */
    val applicationInfo: DesktopApplicationInfo = DesktopApplicationInfo()

    val assets: DesktopAssetManager = DesktopAssetManager()

    val resources: DesktopResources = DesktopResources()

    fun getExternalFilesDir(type: String?): File = PlatformPaths.filesDir.apply { mkdirs() }
    fun getFilesDir(): File = PlatformPaths.filesDir.apply { mkdirs() }
    fun getCacheDir(): File = PlatformPaths.cacheDir.apply { mkdirs() }
    fun getExternalCacheDir(): File = PlatformPaths.cacheDir.apply { mkdirs() }
    fun getNoBackupFilesDir(): File = PlatformPaths.filesDir.apply { mkdirs() }
    fun getCodeCacheDir(): File = File(PlatformPaths.cacheDir, "code-cache").apply { mkdirs() }

    /** Room database path helper (used once). */
    fun getDatabasePath(name: String): File = File(PlatformPaths.databasesDir, name).apply { parentFile.mkdirs() }

    fun getSharedPreferences(name: String, mode: Int): DesktopPrefs = DesktopPrefs.get(name, mode)

    /** 系统服务名常量（对齐 Android 的 getSystemService 取值）。 */
    companion object {
        const val MODE_PRIVATE = 0
        const val MODE_APPEND = 32768

        const val CLIPBOARD_SERVICE = "clipboard"
        const val CONNECTIVITY_SERVICE = "connectivity"

        val instance: DesktopContext by lazy { DesktopContext() }

        private val connectivity = ConnectivityManager()
    }

    /** 取系统服务；桌面端目前只有剪贴板与网络状态。 */
    fun getSystemService(name: String): Any? = when (name) {
        CLIPBOARD_SERVICE -> ClipboardManager
        CONNECTIVITY_SERVICE -> connectivity
        else -> null
    }

    /**
     * 打开外部链接/文件：对应 Android 的 startActivity(Intent.ACTION_VIEW)。
     * 桌面端交给系统默认程序（浏览器 / 关联应用）。
     */
    fun startActivity(intent: Intent): Boolean {
        val target = intent.data?.toString().orEmpty()
        if (target.isBlank()) return false
        return DesktopActions.openUrl(target)
    }

    /** Wraps [DesktopAssets]; only read-only operations are supported. */
    class DesktopAssetManager {
        fun open(path: String): InputStream = DesktopAssets.open(path)
        fun openOrNull(path: String): InputStream? = DesktopAssets.openOrNull(path)
        fun list(path: String): Array<String>? = DesktopAssets.list(path)
        fun exists(path: String): Boolean = DesktopAssets.exists(path)
    }

    /** Only the bits used by ported code (getString of resources is ported separately). */
    class DesktopResources {
        fun getString(resId: Int): String = error("strings are accessed via com.vantacode.core.ui.Strings")
        fun getBoolean(resId: Int): Boolean = error("booleans are accessed via constants")
        fun obtainConfiguration(): java.util.Locale = java.util.Locale.getDefault()
    }
}

class DesktopApplicationInfo {
    val packageName: String get() = PlatformInfo.PACKAGE_NAME
    val versionName: String get() = PlatformInfo.VERSION_NAME
    val versionCode: Int get() = PlatformInfo.VERSION_CODE
    /** Debug builds on desktop are launched from Gradle; release is packaged by jpackage. */
    val debuggable: Boolean get() = java.lang.Boolean.getBoolean("vantacode.debug")
    val flags: Int get() = if (debuggable) 2 else 0
    val targetSdkVersion: Int get() = 0
    val minSdkVersion: Int get() = 0
}
