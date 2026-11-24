/*
 * VantaCode Desktop — chat attachment pipeline (native files instead of content URIs).
 * Copyright (C) 2026 VantaCode contributors. GPL-3.0.
 */
package com.vantacode.feature.agent.presentation.component

import com.vantacode.core.platform.DesktopContext
import com.vantacode.core.util.ImageDownscaler
import com.vantacode.core.ui.Strings
import com.vantacode.feature.agent.domain.model.AgentImage
import com.vantacode.feature.agent.presentation.AgentAttachment
import com.vantacode.feature.workspace.domain.FileAccessProvider
import com.vantacode.feature.workspace.domain.WorkspacePathMapper
import java.io.File
import java.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal data class UploadedWorkspaceFile(
    val fileName: String,
    val containerPath: String,
    val localPath: String,
    val mimeType: String,
    val sizeBytes: Long,
    val image: AgentImage? = null
)

internal data class PendingUploadAttachment(
    val fileName: String,
    val containerPath: String,
    val localPath: String,
    val mimeType: String,
    val sizeBytes: Long,
    val image: AgentImage? = null,
)

internal val PendingUploadAttachment.isImage: Boolean
    get() = image != null

private fun List<PendingUploadAttachment>.toAttachmentText(context: DesktopContext): String {
    if (isEmpty()) return ""
    return buildString {
        append(Strings.chat_attachment_prefix)
        this@toAttachmentText.forEach { attachment ->
            append('\n')
            append("- ")
            append(attachment.fileName)
            append("：")
            append(attachment.containerPath)
        }
    }
}

internal fun appendAttachmentsToRequest(
    context: DesktopContext,
    request: String,
    attachments: List<PendingUploadAttachment>
): String {
    val attachmentText = attachments.toAttachmentText(context)
    if (attachmentText.isBlank()) return request
    if (request.isBlank()) return attachmentText
    return request.trimEnd() + "\n\n" + attachmentText
}

internal fun UploadedWorkspaceFile.toPendingAttachment(): PendingUploadAttachment =
    PendingUploadAttachment(
        fileName = fileName,
        containerPath = containerPath,
        localPath = localPath,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        image = image
    )

internal fun AgentAttachment.toPendingAttachment(): PendingUploadAttachment {
    val image = if (isImage && localPath.isNotBlank()) {
        val file = File(localPath)
        if (file.exists() && file.isFile && file.length() > 0) {
            try {
                val (mime, base64) = ImageDownscaler.downscaleToBase64(
                    file.readBytes(), mimeType.ifBlank { "image/jpeg" }
                )
                AgentImage(
                    mimeType = mime,
                    base64Data = base64,
                    path = containerPath
                )
            } catch (e: Exception) {
                null
            }
        } else null
    } else null

    return PendingUploadAttachment(
        fileName = fileName,
        containerPath = containerPath,
        localPath = localPath,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        image = image
    )
}

private fun PendingUploadAttachment.toAgentAttachment(): AgentAttachment =
    AgentAttachment(
        fileName = fileName,
        containerPath = containerPath,
        localPath = localPath,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        isImage = isImage
    )

internal fun List<PendingUploadAttachment>.toAgentAttachments(): List<AgentAttachment> =
    map { it.toAgentAttachment() }

private fun PendingUploadAttachment.toAgentImage(): AgentImage? = image

internal fun List<PendingUploadAttachment>.toAgentImages(): List<AgentImage> =
    mapNotNull { it.toAgentImage() }

internal fun maxAttachmentMessage(context: DesktopContext, max: Int): String =
    Strings.formatted(Strings.chat_attachment_max, max)

internal fun uploadSuccessMessage(context: DesktopContext, count: Int): String =
    Strings.formatted(Strings.chat_attachment_uploaded, count)

internal fun partialUploadMessage(context: DesktopContext, count: Int): String =
    Strings.formatted(Strings.chat_attachment_uploaded_partial, count)

internal fun selectedAttachments(
    files: List<File>,
    currentCount: Int
): List<File> = files.take((MAX_PENDING_ATTACHMENTS - currentCount).coerceAtLeast(0))

internal fun hasAttachmentSlots(currentCount: Int): Boolean =
    currentCount < MAX_PENDING_ATTACHMENTS

private fun imageLimitError(context: DesktopContext, limit: Long): String =
    Strings.formatted(Strings.chat_image_too_large, formatBytes(limit))

internal fun emptyWorkspaceMessage(context: DesktopContext): String =
    Strings.chat_select_workspace_first

internal fun unreadableFileMessage(context: DesktopContext): String =
    Strings.chat_read_file_failed

internal fun uploadFallbackError(context: DesktopContext): String =
    Strings.chat_upload_failed

private fun unsupportedImageTypeError(context: DesktopContext): String =
    Strings.chat_unsupported_image_type

private fun mimeTypeOf(file: File): String =
    when (file.extension.lowercase()) {
        "txt", "md", "kt", "java", "js", "ts", "json", "xml", "html", "css", "py", "sh", "gradle", "kts", "yaml", "yml", "toml" -> "text/plain"
        "pdf" -> "application/pdf"
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "gif" -> "image/gif"
        "webp" -> "image/webp"
        "bmp" -> "image/bmp"
        else -> "application/octet-stream"
    }

private fun imageMimeTypeOf(fileName: String): String? =
    when (fileName.substringAfterLast('.', "").lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "webp" -> "image/webp"
        else -> null
    }

internal suspend fun copyFileToWorkspace(
    context: DesktopContext,
    file: File,
    fileAccess: FileAccessProvider,
    includeImageData: Boolean = false
): UploadedWorkspaceFile = withContext(Dispatchers.IO) {
    val attachmentsDir = "${WorkspacePathMapper.CONTAINER_ROOT}/.aicode/attachments"
    val fileName = uniqueUploadName(fileAccess, attachmentsDir, sanitizeUploadFileName(file.name))
    val containerPath = "$attachmentsDir/$fileName"

    val mimeType: String = if (includeImageData) {
        imageMimeTypeOf(file.name) ?: throw IllegalArgumentException(unsupportedImageTypeError(context))
    } else {
        mimeTypeOf(file)
    }

    if (includeImageData) {
        val size = file.length()
        if (size > MAX_IMAGE_SOURCE_BYTES) error(imageLimitError(context, MAX_IMAGE_SOURCE_BYTES))
    }

    val sizeBytes: Long
    val image: AgentImage?
    if (includeImageData) {
        val bytes = file.readBytes()
        if (bytes.size > MAX_IMAGE_SOURCE_BYTES) error(imageLimitError(context, MAX_IMAGE_SOURCE_BYTES))
        val scaled = ImageDownscaler.downscale(bytes, mimeType)
        if (scaled.bytes.size > MAX_IMAGE_UPLOAD_BYTES) error(imageLimitError(context, MAX_IMAGE_UPLOAD_BYTES))
        fileAccess.writeBytes(containerPath, bytes, overwrite = true)
        sizeBytes = bytes.size.toLong()
        image = AgentImage(
            mimeType = scaled.mimeType,
            base64Data = Base64.getEncoder().encodeToString(scaled.bytes),
            path = containerPath
        )
    } else {
        // 普通附件流式落盘：不再整份读进内存
        sizeBytes = file.inputStream().use { input ->
            fileAccess.writeStream(containerPath, input, overwrite = true)
        }
        image = null
    }

    // 缩略图与图片数据需要本地文件：本地模式直接给宿主文件，远程模式下载到临时文件
    val localFile = fileAccess.copyToLocal(containerPath)

    UploadedWorkspaceFile(
        fileName = fileName,
        containerPath = containerPath,
        localPath = localFile.absolutePath,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        image = image
    )
}

private fun sanitizeUploadFileName(name: String): String {
    val cleaned = name
        .map { ch -> if (ch.code < 32 || ch in "\\/:*?\"<>|") '_' else ch }
        .joinToString("")
        .trim()
        .trim('.')
    return cleaned.ifBlank { "upload" }.take(160)
}

private fun uniqueUploadName(fileAccess: FileAccessProvider, dir: String, fileName: String): String {
    if (!fileAccess.exists("$dir/$fileName")) return fileName

    val dotIndex = fileName.lastIndexOf('.')
    val stem = if (dotIndex > 0) fileName.substring(0, dotIndex) else fileName
    val extension = if (dotIndex > 0) fileName.substring(dotIndex) else ""
    var index = 1
    while (fileAccess.exists("$dir/$stem-$index$extension")) {
        index += 1
    }
    return "$stem-$index$extension"
}

private const val MAX_IMAGE_UPLOAD_BYTES = 5L * 1024 * 1024
private const val MAX_IMAGE_SOURCE_BYTES = 40L * 1024 * 1024
internal const val MAX_PENDING_ATTACHMENTS = 8

internal fun formatBytes(bytes: Long): String {
    val kb = 1024.0
    val mb = kb * 1024.0
    return when {
        bytes >= mb -> String.format(java.util.Locale.US, "%.1f MB", bytes / mb)
        bytes >= kb -> String.format(java.util.Locale.US, "%.0f KB", bytes / kb)
        else -> "$bytes B"
    }
}
