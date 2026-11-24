package com.vantacode.feature.agreement.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.vantacode.core.ui.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import com.vantacode.core.ui.R
import com.vantacode.core.theme.semanticColors
import com.vantacode.core.ui.Strings

@Composable
fun AgreementDialog(onAccept: () -> Unit, onDecline: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val termsUrl = Strings.agreement_terms_url
    val privacyUrl = Strings.agreement_privacy_url
    val linkStyles = TextLinkStyles(
        style = SpanStyle(
            color = MaterialTheme.semanticColors.info,
            textDecoration = TextDecoration.Underline
        )
    )
    val notice = buildAnnotatedString {
        append(Strings.agreement_notice_prefix)
        withLink(
            LinkAnnotation.Clickable("terms", linkStyles) {
                runCatching { uriHandler.openUri(termsUrl) }
            }
        ) {
            append(Strings.agreement_terms_link)
        }
        append(Strings.agreement_notice_conjunction)
        withLink(
            LinkAnnotation.Clickable("privacy", linkStyles) {
                runCatching { uriHandler.openUri(privacyUrl) }
            }
        ) {
            append(Strings.agreement_privacy_link)
        }
        append(Strings.agreement_notice_suffix)
    }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(Strings.agreement_title) },
        text = {
            Text(
                text = notice,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(onClick = onAccept) {
                Text(Strings.agreement_accept)
            }
        },
        dismissButton = {
            TextButton(onClick = onDecline) {
                Text(Strings.agreement_decline)
            }
        }
    )
}
