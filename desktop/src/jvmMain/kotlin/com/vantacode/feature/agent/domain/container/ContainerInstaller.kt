package com.vantacode.feature.agent.domain.container

import java.io.File
import java.io.FileInputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Desktop stand-in for the Android PRoot container installer: no rootfs/proot exists on
 * desktop, so this only exposes the agent config directory (~/.aicode, same layout the
 * Android app uses). Kept as a single point so call sites don't hardcode paths.
 */
@Singleton
class ContainerInstaller @Inject constructor() {

    /** Agent config directory: ~/.aicode (skills, agents, prompts, mcp.json, keys, credentials). */
    val aicodeDir: File get() = com.vantacode.core.platform.PlatformPaths.agentConfigDir.apply { mkdirs() }
}
