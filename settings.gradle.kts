@file:Suppress("unused")

pluginManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        // Tencent mirror proxies mavenCentral + google() (androidx). Direct google() is
        // unreachable from some networks, so it stays behind the mirror.
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

rootProject.name = "vantacode-desktop"

include(":terminal-emulator")
include(":desktop")
