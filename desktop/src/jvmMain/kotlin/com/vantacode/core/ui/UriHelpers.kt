/*
 * VantaCode Desktop — shared small helpers (URL opening was previously defined in
 * UpdateCheckDialog.kt, which is gone; AboutSection still links upstream pages).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.core.ui

import com.vantacode.core.platform.DesktopActions

/** Open a URL in the system browser. */
fun openUrl(url: String) {
    DesktopActions.viewUri(url)
}

/** Build the GitHub release URL for a tag (falls back to the repository releases page). */
fun githubReleaseUrl(tag: String?): String = when {
    tag == null -> "https://github.com/jieapi/aicode/releases"
    tag.startsWith("http", ignoreCase = true) -> tag
    else -> "https://github.com/jieapi/aicode/releases/tag/$tag"
}
