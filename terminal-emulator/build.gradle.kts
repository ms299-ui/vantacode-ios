plugins {
    `java-library`
}

// Adapted from the upstream Android library module (com.android.library) to a plain
// JVM library: the Android-only call sites (JNI pty, KeyEvent codes, main-thread
// Handler, android.util.Base64) were replaced with JVM equivalents.
// Upstream Termux terminal-emulator, Apache-2.0. See NOTICE.

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// The original output jar path is locked by another process on this host; emit a
// fresh artifact name instead of fighting the lock. API consumers use project deps.
tasks.named<Jar>("jar") {
    archiveBaseName.set("terminal-emulator-core")
}
