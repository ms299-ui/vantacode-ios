/*
 * VantaCode Desktop — read-only access to packaged assets under resources/assets.
 * Replaces Android's AssetManager(context.assets). Optionally overlaid by files on
 * disk under ~/.vantacode/assets/<path> when present (developer drops).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.platform

import java.io.File
import java.io.IOException
import java.io.InputStream

object DesktopAssets {
    private val overrideRoot: File? get() = PlatformPaths.filesDir.let { File(it, "assets-override") }.takeIf { it.isDirectory }

    /** Classpath resource path for an asset path like "docs/guide/x.md". */
    private fun resourcePath(path: String): String = "/assets/" + path.trim('/')

    /** Returns the asset as text, or throws IOException if missing. */
    fun open(path: String): InputStream {
        openOverride(path)?.let { return it }
        return javaClass.getResourceAsStream(resourcePath(path))
            ?: throw IOException("asset not found on classpath: $path")
    }

    fun openOrNull(path: String): InputStream? = try { open(path) } catch (e: IOException) { null }

    /** Whether an asset exists (file or directory marker). */
    fun exists(path: String): Boolean = openOverride(path)?.let { it.close(); true }
        ?: (javaClass.getResource(resourcePath(path)) != null)

    /**
     * Lists direct children of an asset directory (returns null if unknown).
     * Works by scanning a manifest baked at build time when present (assets/manifest.txt),
     * falling back to a small classpath probe of common extensions.
     */
    fun list(path: String): Array<String>? {
        val normalized = path.trim('/')
        // override tree listing
        overrideRoot?.let { root ->
            val dir = File(root, normalized)
            if (dir.isDirectory) return dir.listFiles()?.map { it.name }?.toTypedArray()
        }
        return assetManifest?.get(normalized)?.toTypedArray()
    }

    fun readText(path: String): String = open(path).bufferedReader().use { it.readText() }

    fun readBytes(path: String): ByteArray = open(path).use { it.readBytes() }

    private val assetManifest: Map<String, List<String>>? by lazy {
        val stream = javaClass.getResourceAsStream("/assets/manifest.txt") ?: return@lazy null
        val map = HashMap<String, MutableList<String>>()
        stream.bufferedReader().useLines { lines ->
            for (line in lines) {
                val tab = line.indexOf('\t')
                if (tab <= 0) continue
                val dir = line.substring(0, tab)
                val name = line.substring(tab + 1)
                map.getOrPut(dir) { mutableListOf() }.add(name)
            }
        }
        map
    }

    private fun openOverride(path: String): InputStream? {
        val root = overrideRoot ?: return null
        val file = File(root, path.trim('/'))
        return if (file.isFile) file.inputStream() else null
    }
}
