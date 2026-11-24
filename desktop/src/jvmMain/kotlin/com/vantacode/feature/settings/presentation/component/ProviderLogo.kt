package com.vantacode.feature.settings.presentation.component
import com.vantacode.core.platform.DesktopContext

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vantacode.core.ui.LogoVectors
import com.vantacode.core.theme.Brand
import com.vantacode.feature.settings.domain.model.AIProviderConfig
import compose.icons.FeatherIcons
import compose.icons.feathericons.Cloud
import compose.icons.feathericons.Cpu
import com.vantacode.core.ui.stringResource
import com.vantacode.core.ui.Strings

/**
 * 提供商最终命中的品牌 key：优先按提供商名称识别品牌名，未命中再按协议类型兜底。
 * 与 [modelBrandKey] 的匹配优先级一致；返回 null 表示无任何 logo 可显示。
 */
fun providerBrandKey(provider: AIProviderConfig?): String? {
    if (provider == null) return null
    val nameKey = modelBrandKey(provider.name)
    if (brandLogo(nameKey) != null) return nameKey
    return when (provider.type) {
        com.vantacode.feature.settings.domain.model.ProviderType.OPENAI -> "openai"
        com.vantacode.feature.settings.domain.model.ProviderType.ANTHROPIC -> "anthropic"
        com.vantacode.feature.settings.domain.model.ProviderType.GEMINI -> "google"
    }
}

/**
 * 根据提供商的品牌 key（名称识别优先，协议类型兜底）匹配对应的品牌 logo drawable 资源。
 */
fun providerLogo(provider: AIProviderConfig?): androidx.compose.ui.graphics.vector.ImageVector? =
    providerBrandKey(provider)?.let { brandLogo(it) }

/**
 * 根据模型名称推断所属品牌分类 key（小写英文标识）。
 * 与 providerLogoRes / modelLogoRes 匹配优先级一致，确保 "zhipu" 不会先命中 "glm" 等。
 * 返回的品牌 key 可用于分组和 logo 查找。
 */
fun modelBrandKey(modelName: String): String {
    val target = modelName.lowercase()
    return when {
        target.contains("doubao") || target.contains("豆包") -> "doubao"
        // minimax 必须排在 grok 之前：minimaxai 等名称含 "xai"，否则会被 grok 规则误匹配
        target.contains("minimax") || target.contains("abab") -> "minimax"
        target.contains("moonshot") || target.contains("kimi") -> "moonshot"
        target.contains("zhipu") || target.contains("智谱") || target.contains("bigmodel") || target.contains("glm") -> "zhipu"
        // alibaba / 通义千问：qwen 归 alibaba 品牌（阿里云通义），优先级在 gemini 之前以防误匹配。
        target.contains("qwen") || target.contains("通义") || target.contains("alibaba") || target.contains("aliyun") || target.contains("dashscope") -> "alibaba"
        target.contains("deepseek") || target.contains("deep-seek") -> "deepseek"
        target.contains("grok") || target.contains("xai") -> "grok"
        target.contains("groq") -> "groq"
        target.contains("claude") || target.contains("anthropic") -> "anthropic"
        // google / gemini：gemma、gemini 都归 google 品牌。
        target.contains("google") || target.contains("gemini") || target.contains("gemma") -> "google"
        target.contains("hunyuan") || target.contains("混元") || target.contains("tencent") || target.contains("hy") -> "hunyuan"
        target.contains("openrouter") -> "openrouter"
        target.contains("perplexity") -> "perplexity"
        target.contains("siliconflow") || target.contains("硅基") -> "siliconflow"
        // ollama 必须在 meta 之前：ollama 名称含 "llama"，否则会被 meta 规则误匹配
        target.contains("ollama") -> "ollama"
        target.contains("meta") || target.contains("llama") -> "meta"
        target.contains("mistral") -> "mistral"
        target.contains("gpt") || target.contains("o1") || target.contains("o3") || target.contains("o4") || target.contains("openai") || target.contains("chatgpt") || target.contains("dall-e") -> "openai"
        else -> "other"
    }
}

/** 品牌 key → 用户可见的显示名称 */
fun brandDisplayName(context: DesktopContext, key: String): String = when (key) {
    "doubao" -> Strings.provider_brand_doubao
    "minimax" -> "MiniMax"
    "moonshot" -> "Moonshot"
    "zhipu" -> Strings.provider_brand_zhipu
    "alibaba" -> "Alibaba"
    "deepseek" -> "DeepSeek"
    "grok" -> "Grok"
    "groq" -> "Groq"
    "anthropic" -> "Anthropic"
    "google" -> "Google"
    "hunyuan" -> Strings.provider_brand_hunyuan
    "openrouter" -> "OpenRouter"
    "perplexity" -> "Perplexity"
    "siliconflow" -> Strings.provider_brand_siliconflow
    "ollama" -> "Ollama"
    "meta" -> "Meta"
    "mistral" -> "Mistral"
    "openai" -> "OpenAI"
    "other" -> Strings.common_other
    else -> key.replaceFirstChar { it.uppercase() }
}

/** 品牌 key → 对应 logo drawable 资源，无匹配时返回 null */
/** 品牌 key → 对应 logo 矢量图标，无匹配时返回 null */
fun brandLogo(key: String): androidx.compose.ui.graphics.vector.ImageVector? = when (key) {
    "doubao" -> LogoVectors.Doubao
    "minimax" -> LogoVectors.Minimax
    "moonshot" -> LogoVectors.Moonshot
    "zhipu" -> LogoVectors.Zhipu
    "alibaba" -> LogoVectors.Qwen
    "deepseek" -> LogoVectors.Deepseek
    "grok" -> LogoVectors.Grok
    "groq" -> LogoVectors.Groq
    "anthropic" -> LogoVectors.Anthropic
    "google" -> LogoVectors.Gemini
    "hunyuan" -> LogoVectors.Hunyuan
    "openrouter" -> LogoVectors.Openrouter
    "perplexity" -> LogoVectors.Perplexity
    "siliconflow" -> LogoVectors.Siliconflow
    "ollama" -> LogoVectors.Ollama
    "meta" -> LogoVectors.Meta
    "mistral" -> LogoVectors.Mistral
    "openai" -> LogoVectors.Openai
    else -> null
}

/** 是否给 logo 施加主题色 tint；保留原色的品牌（如 hunyuan/siliconflow 多色 logo）不在此列。 */
private fun shouldTintModelLogo(key: String): Boolean =
    key == "grok" || key == "groq" || key == "moonshot" || key == "openai" ||
        key == "openrouter" || key == "perplexity" ||
        key == "ollama" || key == "meta" || key == "mistral"

@Composable
private fun modelLogoTint(): Color {
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if (isDarkTheme) Color.White else Color.Black
}

/**
 * 渲染品牌 logo 图标；若未匹配或 provider 为 null，则显示默认 Cpu 图标。
 */
@Composable
fun ProviderLogoIcon(
    provider: AIProviderConfig?,
    modifier: Modifier = Modifier,
    size: Dp = 22.dp
) {
    val vector = providerLogo(provider)
    if (vector != null) {
        val brandKey = providerBrandKey(provider)
        Image(
            painter = rememberVectorPainter(vector),
            contentDescription = provider?.name ?: "AI Vendor",
            colorFilter = if (brandKey != null && shouldTintModelLogo(brandKey)) ColorFilter.tint(modelLogoTint()) else null,
            modifier = modifier.size(size)
        )
    } else {
        Icon(
            imageVector = FeatherIcons.Cloud,
            contentDescription = Strings.common_ai_providers,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.size(size)
        )
    }
}

/**
 * 根据模型名称渲染品牌 logo 图标；若未匹配则显示默认 Cpu 图标。
 */
@Composable
fun ModelLogoIcon(
    modelName: String,
    modifier: Modifier = Modifier,
    size: Dp = 22.dp
) {
    val key = modelBrandKey(modelName)
    val res = brandLogo(key)
    if (res != null) {
        Image(
            painter = rememberVectorPainter(res),
            contentDescription = modelName,
            colorFilter = if (shouldTintModelLogo(key)) ColorFilter.tint(modelLogoTint()) else null,
            modifier = modifier.size(size)
        )
    } else {
        Icon(
            imageVector = FeatherIcons.Cpu,
            contentDescription = Strings.provider_model_icon,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.size(size)
        )
    }
}
