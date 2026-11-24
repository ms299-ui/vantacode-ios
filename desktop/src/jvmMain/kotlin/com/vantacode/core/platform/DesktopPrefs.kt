/*
 * VantaCode Desktop — SharedPreferences-compatible key/value store backed by a
 * Java Properties file under ~/.vantacode/shared_prefs/<name>.properties.
 * Mirrors the Android SharedPreferences API used by the ported code.
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Properties
import java.util.concurrent.ConcurrentHashMap

class DesktopPrefs private constructor(private val file: File) {
    private val lock = Any()
    private var properties = Properties()

    init {
        reload()
    }

    private fun reload() {
        synchronized(lock) {
            properties = Properties()
            if (file.exists()) {
                runCatching { file.inputStream().use { properties.load(it) } }
            }
        }
    }

    private fun save() {
        synchronized(lock) {
            file.parentFile.mkdirs()
            val tmp = File(file.absolutePath + ".tmp")
            tmp.outputStream().writer(StandardCharsets.UTF_8).use { w ->
                properties.store(w, "VantaCode desktop preferences")
            }
            // 原子替换：先写 .tmp 再 move，崩溃时不会留下半截配置
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    fun contains(key: String): Boolean = synchronized(lock) { properties.containsKey(key) }

    fun getString(key: String, defaultValue: String?): String? = synchronized(lock) {
        (properties.getProperty(key) as? String) ?: defaultValue
    }

    fun containsKey(key: String): Boolean = contains(key)

    fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        synchronized(lock) { properties.getProperty(key)?.toBooleanStrictOrNull() ?: defaultValue }

    fun getInt(key: String, defaultValue: Int): Int =
        synchronized(lock) { properties.getProperty(key)?.toIntOrNull() ?: defaultValue }

    fun getLong(key: String, defaultValue: Long): Long =
        synchronized(lock) { properties.getProperty(key)?.toLongOrNull() ?: defaultValue }

    fun getFloat(key: String, defaultValue: Float): Float =
        synchronized(lock) { properties.getProperty(key)?.toFloatOrNull() ?: defaultValue }

    /** SharedPreferences.all 的替身（值都是字符串）。 */
    val all: Map<String, String>
        get() = synchronized(lock) {
            properties.stringPropertyNames().associateWith { properties.getProperty(it) }
        }

    fun getStringSet(key: String, default: Set<String>?): Set<String>? =
        synchronized(lock) {
            val raw = properties.getProperty(key) ?: return default
            if (raw.isEmpty()) emptySet() else raw.split('\u0001').toSet()
        }

    fun edit(): Editor = Editor()

    /** androidx KTX 的 prefs.edit { } 扩展等价物。 */
    fun edit(block: Editor.() -> Unit) {
        val editor = Editor()
        editor.block()
        editor.apply()
    }

    fun clear(): Unit = synchronized(lock) { properties.clear(); save() }

    fun remove(key: String): Unit = synchronized(lock) { properties.remove(key); save() }

    fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) = Unit
    fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) = Unit

    fun interface OnSharedPreferenceChangeListener {
        fun onSharedPreferenceChanged(sharedPreferences: DesktopPrefs, key: String?)
    }

    inner class Editor {
        private val pending = linkedMapOf<String, () -> Unit>()
        private var clearAll = false

        fun putString(key: String, value: String?): Editor {
            pending[key] = { if (value == null) properties.remove(key) else properties.setProperty(key, value) }
            return this
        }

        fun putBoolean(key: String, value: Boolean): Editor {
            pending[key] = { properties.setProperty(key, value.toString()) }
            return this
        }

        fun putInt(key: String, value: Int): Editor {
            pending[key] = { properties.setProperty(key, value.toString()) }
            return this
        }

        fun putLong(key: String, value: Long): Editor {
            pending[key] = { properties.setProperty(key, value.toString()) }
            return this
        }

        fun putStringSet(key: String, values: Set<String>?): Editor {
            pending[key] = {
                if (values == null) properties.remove(key)
                else properties.setProperty(key, values.joinToString("\u0001"))
            }
            return this
        }

        fun putFloat(key: String, value: Float): Editor {
            pending[key] = { properties.setProperty(key, value.toString()) }
            return this
        }

        fun remove(key: String): Editor {
            pending[key] = { properties.remove(key) }
            return this
        }

        fun clear(): Editor {
            clearAll = true
            pending.clear()
            return this
        }

        fun commit(): Boolean {
            synchronized(lock) {
                if (clearAll) properties.clear()
                pending.values.forEach { it() }
                save()
            }
            return true
        }

        fun apply() {
            commit()
        }
    }

    companion object {
        private const val MODE_PRIVATE = 0
        const val DIRECTORY = "shared_prefs"

        private val instances = ConcurrentHashMap<String, DesktopPrefs>()

        fun get(name: String, mode: Int = MODE_PRIVATE): DesktopPrefs =
            instances.getOrPut(name) {
                DesktopPrefs(File(PlatformPaths.filesDir, "$DIRECTORY/$name.properties"))
            }
    }
}
