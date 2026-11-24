package com.vantacode.core.security

import com.vantacode.core.platform.PlatformPaths
import com.vantacode.core.util.FileLogger
import java.io.File
import java.security.GeneralSecurityException
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 敏感数据的对称加密：数据用 AES-256-GCM 加密。用于私钥文件与数据库里的密码/API Key 等字段。
 *
 * 桌面端没有 Android Keystore，密钥改为落在 `~/.vantacode/secret.key`（首次访问时随机生成，
 * 仅当前用户可读写）。安全性弱于 TEE 里的不可导出密钥，但同机其它用户/进程读不到该文件；
 * 要更强可换用 OS keychain（Windows DPAPI / macOS Keychain），当前未接入。
 *
 * 明文兼容：解密入口对非密文输入原样返回，便于旧明文数据的平滑迁移。
 */
object KeystoreCipher {
    private const val TAG = "KeystoreCipher"
    private const val KEY_FILE = "secret.key"
    private const val KEY_ALGORITHM = "AES"
    private const val KEY_SIZE_BITS = 256
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val GCM_TAG_BITS = 128
    private const val IV_LEN = 12

    /** 字符串密文前缀；不含此前缀的值按旧明文处理。 */
    private const val STRING_PREFIX = "AICODEENC1:"

    /** 字节密文文件头；用于区分加密文件与旧明文文件。 */
    private val BYTE_MAGIC = "AICODEENC1\u0000".toByteArray(Charsets.US_ASCII)

    private val lock = Any()

    /** 密钥文件路径：~/.vantacode/secret.key */
    private fun keyFile(): File = File(PlatformPaths.filesDir, KEY_FILE)

    private fun secretKey(): SecretKey = synchronized(lock) {
        val file = keyFile()
        val raw = if (file.exists()) {
            file.readBytes()
        } else {
            val generated = KeyGenerator.getInstance(KEY_ALGORITHM).apply { init(KEY_SIZE_BITS) }.generateKey().encoded
            runCatching {
                file.parentFile?.mkdirs()
                file.writeBytes(generated)
                // 仅当前用户可读写
                file.setReadable(false, false)
                file.setWritable(false, false)
                file.setReadable(true, true)
                file.setWritable(true, true)
            }.onFailure { FileLogger.w(TAG, "写入密钥文件失败", it) }
            generated
        }
        SecretKeySpec(raw, KEY_ALGORITHM)
    }

    fun encryptBytes(plain: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey())
        val iv = cipher.iv
        val ciphertext = cipher.doFinal(plain)
        return BYTE_MAGIC + iv + ciphertext
    }

    fun isEncryptedBytes(bytes: ByteArray): Boolean =
        bytes.size >= BYTE_MAGIC.size + IV_LEN && BYTE_MAGIC.indices.all { bytes[it] == BYTE_MAGIC[it] }

    fun decryptBytes(encrypted: ByteArray): ByteArray {
        require(isEncryptedBytes(encrypted)) { "不是加密数据" }
        val iv = encrypted.copyOfRange(BYTE_MAGIC.size, BYTE_MAGIC.size + IV_LEN)
        val ciphertext = encrypted.copyOfRange(BYTE_MAGIC.size + IV_LEN, encrypted.size)
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey(), GCMParameterSpec(GCM_TAG_BITS, iv))
            cipher.doFinal(ciphertext)
        } catch (e: GeneralSecurityException) {
            // 密文与当前 Keystore 密钥不匹配（密钥丢失后按新密钥重建）或数据损坏。
            // 返回空值而非抛出，避免单个坏字段把整条读取 Flow 带崩。
            FileLogger.w(TAG, "解密失败：密文与当前 Keystore 密钥不匹配或已损坏", e)
            ByteArray(0)
        }
    }

    fun isEncryptedString(value: String?): Boolean = value != null && value.startsWith(STRING_PREFIX)

    /** 加密字符串；空串原样返回，避免把「未填写」变成非空密文。 */
    fun encryptString(plain: String): String {
        if (plain.isEmpty()) return plain
        return STRING_PREFIX + Base64.getEncoder().encodeToString(encryptBytes(plain.toByteArray(Charsets.UTF_8)))
    }

    /** 解密字符串；非密文（旧明文）原样返回。 */
    fun decryptString(value: String): String {
        if (!isEncryptedString(value)) return value
        return try {
            val body = Base64.getDecoder().decode(value.removePrefix(STRING_PREFIX))
            String(decryptBytes(body), Charsets.UTF_8)
        } catch (e: IllegalArgumentException) {
            FileLogger.w(TAG, "解密失败：密文格式非法", e)
            ""
        }
    }
}
