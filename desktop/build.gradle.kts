import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") version "2.3.10"
    id("org.jetbrains.compose") version "1.12.2"
    kotlin("plugin.compose") version "2.3.10"
    kotlin("plugin.serialization") version "2.3.10"
    id("com.google.devtools.ksp") version "2.3.10"
}

// Build-time asset manifest so DesktopAssets.list() can enumerate packaged
// resources (classpath resources are not listable at runtime).
val genAssetManifest = tasks.register("genAssetManifest") {
    val assetsDir = layout.projectDirectory.dir("src/jvmMain/resources/assets").asFile
    val outDir = layout.buildDirectory.dir("generated/assets")
    inputs.dir(assetsDir)
    outputs.dir(outDir)
    doLast {
        val lines = mutableListOf<String>()
        assetsDir.walkTopDown().filter { it.isFile }.forEach { f ->
            val rel = f.relativeTo(assetsDir).invariantSeparatorsPath
            val idx = rel.lastIndexOf('/')
            val dir = if (idx < 0) "" else rel.substring(0, idx)
            val name = if (idx < 0) rel else rel.substring(idx + 1)
            lines += "$dir\t$name"
        }
        outDir.get().asFile.resolve("assets/manifest.txt").apply {
            parentFile.mkdirs()
            writeText(lines.joinToString("\n") + "\n")
        }
    }
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
    sourceSets {
        commonMain.dependencies {
            // Room persistence — KMP artifact (Android/iOS/JVM)
            implementation("androidx.room:room-runtime:2.7.1")
            implementation("androidx.sqlite:sqlite-bundled:2.7.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.compose.material3:material3:1.12.0-alpha03")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation("org.jetbrains.compose.components:components-resources:1.12.2")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")

            // Lifecycle (KMP, JVM/desktop target)
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")

            // Datastore preferences (KMP)
            implementation("androidx.datastore:datastore-preferences:1.1.4")

            // Navigation Compose (KMP, desktop target). androidx 的 desktop 变体是空壳，
            // rememberNavController 会抛 "Implemented only in JetBrains fork"，必须用 JetBrains 构建。
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.10.0")

            // Networking
            implementation("com.squareup.retrofit2:retrofit:2.11.0")
            implementation("com.squareup.retrofit2:converter-gson:2.11.0")
            implementation("com.squareup.okhttp3:okhttp:4.12.0")
            implementation("org.jsoup:jsoup:1.18.1")

            // Dagger annotations used by the ported @Inject constructors (DI graph is manual/lazy).
            implementation("javax.inject:javax.inject:1")

            // SSH / SFTP / FTP (remote workspaces)
            implementation("com.hierynomus:sshj:0.38.0")
            implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")
            implementation("commons-net:commons-net:3.10.0")
            implementation("org.apache.ftpserver:ftpserver-core:1.2.0")

            // Archives for user-imported images
            implementation("org.apache.commons:commons-compress:1.26.2")
            implementation("org.tukaani:xz:1.10")

            // YAML (prompt fragments, skills frontmatter)
            implementation("org.yaml:snakeyaml:2.2")

            // Terminal emulator core (this project, adapted from upstream)
            implementation(project(":terminal-emulator"))
            // Local PTY backend for the terminal panel
            implementation("org.jetbrains.pty4j:pty4j:0.13.13")

            // Charts (usage dashboard)
            implementation("com.patrykandpatrick.vico:compose-m3:3.2.2")

            // Compose icons — feather (same set as Android app)
            implementation("br.com.devsrsouza.compose.icons:feather:1.1.1")

            // Markdown rendering (multiplatform, desktop target)
            implementation("com.mikepenz:multiplatform-markdown-renderer-m3:0.41.0")
            implementation("com.mikepenz:multiplatform-markdown-renderer-code:0.41.0")
            implementation("dev.snipme:highlights-jvm:1.1.0")

            // JCEF — 渲染插件自带的 HTML/Vue 面板。natives jar (~163MiB) 随 app image 进 lib/，
            // 首次运行由 jcefmaven 解包到 ~/.vantacode/jcef（installDir 见 PluginCef）。
            val jcefVersion = "152.0.6"
            implementation("me.friwi:jcefmaven:$jcefVersion")
            implementation(
                "me.friwi:jcef-natives-windows-amd64:" +
                    "jcef-17e805a+cef-152.0.6+g708dc14+chromium-152.0.7977.83"
            )

            // Drag & drop reorder (settings lists)
            implementation("sh.calvin.reorderable:reorderable:3.1.0")

            // LaTeX math rendering — pure JVM build of jlatexmath
            implementation("org.scilab.forge:jlatexmath:1.0.7")
            implementation("org.scilab.forge:jlatexmath-font-greek:1.0.7")
            implementation("org.scilab.forge:jlatexmath-font-cyrillic:1.0.7")

            // MP4 video background decoding — pure Java (MIT license)
            implementation("org.jcodec:jcodec:0.2.5")
            implementation("org.jcodec:jcodec-javase:0.2.5")

            implementation("org.slf4j:slf4j-simple:2.0.9")
        }
        jvmTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation("io.mockk:mockk:1.14.11")
        }
        jvmMain {
            resources.srcDir(layout.buildDirectory.dir("generated/assets"))
        }
    }
}

compose.resources {
    packageOfResClass = "com.vantacode.resources"
}

tasks.named("jvmProcessResources") {
    dependsOn(genAssetManifest)
}

dependencies {
    add("kspCommonMainMetadata", "androidx.room:room-compiler:2.7.1")
    add("kspJvm", "androidx.room:room-compiler:2.7.1")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

compose.desktop {
    application {
        mainClass = "com.vantacode.MainKt"
        jvmArgs += listOf(
            "-Xmx1536m",
            // JCEF / OSR 离屏渲染所需模块开放（fat jlink 已含全部模块）
            "--add-exports=java.base/java.lang=ALL-UNNAMED",
            "--add-exports=java.desktop/sun.awt=ALL-UNNAMED",
            "--add-exports=java.desktop/sun.java2d=ALL-UNNAMED",
            "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
            "--add-opens=java.desktop/java.awt.peer=ALL-UNNAMED"
        )

        nativeDistributions {
            // MVP：正式分发 = 绿色 app image + EXE 安装包（WiX）。
            targetFormats(TargetFormat.Exe)
            // 安装时显示许可证接受对话框（GPL 分发必须）：LICENSE 全文。
            licenseFile.set(rootProject.file("LICENSE"))
            packageName = "VantaCode"
            packageVersion = "1.4.0"
            description = "VantaCode — AI coding agent for desktop (GPL-3.0)"
            copyright = "GPL-3.0. Contains code from AiCode (https://github.com/jieapi/AiCode)."
            vendor = "VantaCode"

            windows {
                menuGroup = "VantaCode"
                perUserInstall = true
                shortcut = true
                upgradeUuid = "3f6a1c2e-7b4d-4c2a-9a9c-9f2a3e5b6c01"
                iconFile.set(project.file("icons/icon.ico"))
            }
        }
    }
}

// Compose 插件会把运行时镜像裁成 ~4 模块（自动分析），含 jdk.crypto.ec 但缺 jdk.unsupported
// （androidx.datastore protobuf 需要 sun.misc.Unsafe）。其模块属性被 finalize 后无法在 doFirst
// 里改、配置阶段的设置也会被插件覆盖，所以 MVP 走强行替换：在 createRuntimeImage 之后
// 删掉它产出的默认镜像，用 jlink 直接打出全模块镜像（构建 JDK 全部 70 模块，含 jdk.unsupported），
// 再让后续打包任务照常 copy 这个完整镜像。
val ALL_JDK_MODULES = listOf(
    "java.base", "java.compiler", "java.datatransfer", "java.desktop", "java.instrument",
    "java.logging", "java.management", "java.management.rmi", "java.naming", "java.net.http",
    "java.prefs", "java.rmi", "java.scripting", "java.se", "java.security.jgss",
    "java.security.sasl", "java.smartcardio", "java.sql", "java.sql.rowset", "java.transaction.xa",
    "java.xml", "java.xml.crypto",
    "jdk.accessibility", "jdk.attach", "jdk.charsets", "jdk.compiler", "jdk.crypto.cryptoki",
    "jdk.crypto.ec", "jdk.crypto.mscapi", "jdk.dynalink", "jdk.editpad", "jdk.hotspot.agent",
    "jdk.httpserver", "jdk.incubator.vector", "jdk.internal.ed", "jdk.internal.jvmstat",
    "jdk.internal.le", "jdk.internal.opt", "jdk.internal.vm.ci", "jdk.internal.vm.compiler",
    "jdk.internal.vm.compiler.management", "jdk.jartool", "jdk.javadoc", "jdk.jcmd", "jdk.jconsole",
    "jdk.jdeps", "jdk.jdi", "jdk.jdwp.agent", "jdk.jfr", "jdk.jlink", "jdk.jpackage", "jdk.jshell",
    "jdk.jsobject", "jdk.jstatd", "jdk.localedata", "jdk.management", "jdk.management.agent",
    "jdk.management.jfr", "jdk.naming.dns", "jdk.naming.rmi", "jdk.net", "jdk.nio.mapmode",
    "jdk.random", "jdk.sctp", "jdk.security.auth", "jdk.security.jgss", "jdk.unsupported",
    "jdk.unsupported.desktop", "jdk.xml.dom", "jdk.zipfs",
)

val fatRuntimeImage = tasks.register("fatRuntimeImage") {
    dependsOn("createRuntimeImage")
    outputs.upToDateWhen { false }
    doLast {
        val runtimeDir = layout.buildDirectory.dir("compose/tmp/main/runtime").get().asFile
        println("[fatRuntimeImage] 替换 Compose 默认镜像为全模块镜像：$runtimeDir")
        if (runtimeDir.exists()) runtimeDir.deleteRecursively()
        // jlink 要求 --output 目录不存在，不能先 mkdirs；内存紧张时限制 jlink 自身堆
        val jlink = "${System.getProperty("java.home")}/bin/jlink"
        val cmd = if (System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) {
            listOf("${System.getProperty("java.home")}\\bin\\jlink.exe",
                "-J-Xmx256m", "-J-XX:MaxMetaspaceSize=96m", "-J-XX:+UseSerialGC",
                "--no-header-files", "--no-man-pages", "--strip-debug",
                "--compress", "2", "--add-modules", ALL_JDK_MODULES.joinToString(","), "--output", runtimeDir.absolutePath)
        } else {
            listOf(jlink, "-J-Xmx256m", "-J-XX:MaxMetaspaceSize=96m", "-J-XX:+UseSerialGC",
                "--no-header-files", "--no-man-pages", "--strip-debug",
                "--compress", "2", "--add-modules", ALL_JDK_MODULES.joinToString(","), "--output", runtimeDir.absolutePath)
        }
        println("[fatRuntimeImage] 执行: jlink --add-modules <70 模块> --output $runtimeDir")
        exec { commandLine(cmd) }
        println("[fatRuntimeImage] 完成。modules=${ALL_JDK_MODULES.size}")
    }
}

// 让所有会用到临时镜像的打包任务在 fatRuntimeImage 之后执行（createRuntimeImage 先产出默认镜像，我们再覆盖）。
tasks.matching { it.name.contains("unpackDefaultComposeDesktopJvmApplicationResources", ignoreCase = true) }.configureEach {
    dependsOn(fatRuntimeImage)
}

// 开发期 gradlew run 也继承 JCEF/OSR 所需的模块开放参数（打包时上面 jvmArgs 已含）。
val jcefJvmArgs = listOf(
    "--add-exports=java.base/java.lang=ALL-UNNAMED",
    "--add-exports=java.desktop/sun.awt=ALL-UNNAMED",
    "--add-exports=java.desktop/sun.java2d=ALL-UNNAMED",
    "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
    "--add-opens=java.desktop/java.awt.peer=ALL-UNNAMED"
)
tasks.withType<JavaExec>().configureEach {
    jvmArgs(jcefJvmArgs)
}
