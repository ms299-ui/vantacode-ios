package com.vantacode.feature.settings.data.remote

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.vantacode.core.net.AppProxy
import com.vantacode.core.ui.Strings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

/** Release 里的一个更新包资产（桌面端只关心 .exe）。 */
data class UpdateAsset(
    val name: String,
    val url: String,
    val size: Long,
    /** GitHub API 的 `digest` 字段（形如 `sha256:...`），缺失时不阻断展示。 */
    val sha256: String? = null
)

/** 单个版本的更新日志与更新包。 */
data class VersionUpdate(
    val tag: String,
    val changelog: String,
    val asset: UpdateAsset? = null
)

/** 拉取到的更新信息：最新版本号 + 从当前版本到最新的更新日志。 */
data class UpdateInfo(
    val latestTag: String,
    val changelog: String,
    val updates: List<VersionUpdate>
) {
    /** 最新版本的更新包；该 Release 没挂 exe 资产时为 null。 */
    val latestAsset: UpdateAsset? get() = updates.firstOrNull()?.asset
}

/** 检查更新结果。 */
sealed interface UpdateCheckResult {
    data object UpToDate : UpdateCheckResult
    data class NewVersion(val info: UpdateInfo) : UpdateCheckResult
    data class Error(val message: String) : UpdateCheckResult
}

/** 更新通道：稳定版过滤预发布，最新版包含预发布。 */
enum class UpdateChannel {
    STABLE,
    BETA
}

/**
 * 解析版本标签里的数字版本号：`v1.3.0` / `v1.4.0-rc1` → `1.3.0`。
 * 标签里找不到数字版本时返回 null，该 Release 会被忽略。
 */
fun parseVersionTag(tag: String): String? =
    Regex("(\\d+(?:\\.\\d+){1,3})").find(tag)?.value

/** 逐段比较 a、b 的数字版本：a>b 返回正数，a<b 返回负数，相等返回 0。 */
fun compareVersions(a: String, b: String): Int {
    val pa = a.split('.').map { it.toIntOrNull() ?: 0 }
    val pb = b.split('.').map { it.toIntOrNull() ?: 0 }
    for (i in 0 until maxOf(pa.size, pb.size)) {
        val x = pa.getOrElse(i) { 0 }
        val y = pb.getOrElse(i) { 0 }
        if (x != y) return x.compareTo(y)
    }
    return 0
}

/**
 * 从 GitHub Releases 拉取版本信息并生成更新日志。
 * 桌面端不在应用内安装：只返回版本信息和资产，由界面展示变更日志并打开 Release 页面。
 */
suspend fun checkForUpdate(
    currentVersion: String,
    channel: UpdateChannel
): UpdateCheckResult = withContext(Dispatchers.IO) {
    runCatching {
        val req = Request.Builder()
            .url("$GITHUB_RELEASES_API?per_page=50")
            .header("Accept", "application/vnd.github+json")
            .build()
        SHARED_CLIENT.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) {
                return@use UpdateCheckResult.Error("HTTP ${resp.code}")
            }
            val body = resp.body?.string().orEmpty()
            val array = runCatching { JsonParser.parseString(body).asJsonArray }
                .getOrNull()
                ?: return@use UpdateCheckResult.Error(Strings.about_parse_version_failed)

            val releases = array.mapNotNull { el ->
                val obj = runCatching { el.asJsonObject }.getOrNull() ?: return@mapNotNull null
                val tag = obj.get("tag_name")?.asString ?: return@mapNotNull null
                val version = parseVersionTag(tag) ?: return@mapNotNull null
                ReleaseInfo(
                    version = version,
                    rawTag = tag,
                    notes = obj.get("body")?.asString.orEmpty(),
                    prerelease = obj.get("prerelease")?.asBoolean ?: false,
                    assets = parseAssets(obj)
                )
            }

            val updates = releases
                .filter { if (channel == UpdateChannel.STABLE) !it.prerelease else true }
                .filter { compareVersions(it.version, currentVersion) > 0 }
                .sortedWith { a, b -> compareVersions(b.version, a.version) }

            if (updates.isEmpty()) {
                UpdateCheckResult.UpToDate
            } else {
                val versionUpdates = updates.map(::toVersionUpdate)
                UpdateCheckResult.NewVersion(
                    UpdateInfo(
                        latestTag = versionUpdates.first().tag,
                        changelog = versionUpdates.joinToString("\n\n") { "${it.tag}\n${it.changelog}" },
                        updates = versionUpdates
                    )
                )
            }
        }
    }.getOrElse { UpdateCheckResult.Error(it.message ?: Strings.about_network_error) }
}

private data class ReleaseInfo(
    val version: String,
    val rawTag: String,
    val notes: String,
    val prerelease: Boolean,
    val assets: List<UpdateAsset>
)

private fun toVersionUpdate(release: ReleaseInfo): VersionUpdate {
    val notes = release.notes.trim()
    return VersionUpdate(
        tag = release.rawTag,
        changelog = if (notes.isEmpty()) Strings.about_no_changelog else cleanMarkdown(notes),
        asset = selectUpdateAsset(release.assets)
    )
}

/** 轻量清理 markdown：去掉标题符与粗体标记，并删除 GitHub 自动生成的标题/链接行。 */
private fun cleanMarkdown(text: String): String = text
    .lines()
    .map { it.trimEnd().replace("**", "") }
    .map { it.replace(Regex("^#{1,6}\\s+"), "") }
    .filterNot { line ->
        val t = line.trim()
        t.startsWith("What's Changed") ||
            t.startsWith("What's New") ||
            t.startsWith("Full Changelog:")
    }
    .joinToString("\n")

/** 解析 Release 的资产列表；`digest` 只认 sha256，其它算法（或缺失）一律置 null。 */
private fun parseAssets(obj: JsonObject): List<UpdateAsset> =
    obj.getAsJsonArray("assets")?.mapNotNull { el ->
        val asset = runCatching { el.asJsonObject }.getOrNull() ?: return@mapNotNull null
        val name = asset.get("name")?.asString ?: return@mapNotNull null
        val url = asset.get("browser_download_url")?.asString ?: return@mapNotNull null
        UpdateAsset(
            name = name,
            url = url,
            size = asset.get("size")?.asLong ?: 0L,
            sha256 = asset.get("digest")?.asString
                ?.takeIf { it.startsWith(SHA256_PREFIX, ignoreCase = true) }
                ?.substring(SHA256_PREFIX.length)
                ?.takeIf { it.isNotBlank() }
        )
    } ?: emptyList()

/** 从资产里挑出 Windows 安装包：优先含 VantaCode 的 .exe，其次任意 .exe。 */
private fun selectUpdateAsset(assets: List<UpdateAsset>): UpdateAsset? {
    val exes = assets.filter { it.name.endsWith(".exe", ignoreCase = true) }
    return exes.firstOrNull { it.name.contains("VantaCode", ignoreCase = true) }
        ?: exes.firstOrNull()
}

private const val GITHUB_RELEASES_API = "https://api.github.com/repos/Hye-steam/VantaCode-Desktop/releases"
private const val SHA256_PREFIX = "sha256:"

private val SHARED_CLIENT: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .proxyAuthenticator(AppProxy.okHttpAuthenticator)
        .build()
}
