package br.com.camilacunha.focalis.presentation.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.WebKit.WKAudiovisualMediaTypeNone
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

/**
 * Renderiza o player do YouTube via WKWebView.
 *
 * Estratégia: carrega um HTML com iframe, ao invés de carregar a URL
 * de embed direto. O `loadHTMLString` aceita um `baseURL` que vira o
 * `Origin`/`Referer` das requisições — é o mesmo truque necessário pra
 * satisfazer a política atual do YouTube (que rejeita embeds sem origem).
 *
 * Configurações essenciais:
 *  - allowsInlineMediaPlayback: vídeo toca inline, sem entrar em fullscreen.
 *  - mediaTypesRequiringUserActionForPlayback = None: sem exigir gesto pra
 *    iniciar reprodução (o iframe já cuida disso via JS).
 */
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebPlayer(
    modifier: Modifier,
    embedUrl: String,
    externalUrl: String,
) {
    val webView = remember {
        val config = WKWebViewConfiguration().apply {
            allowsInlineMediaPlayback = true
            mediaTypesRequiringUserActionForPlayback = WKAudiovisualMediaTypeNone
        }
        WKWebView(
            frame = CGRectMake(0.0, 0.0, 0.0, 0.0),
            configuration = config,
        )
    }

    LaunchedEffect(embedUrl) {
        val html = """
            <html>
            <body style="margin:0;padding:0;background-color:black;">
            <iframe
                width="100%"
                height="100%"
                src="$embedUrl?playsinline=1"
                frameborder="0"
                allow="autoplay; encrypted-media; picture-in-picture"
                allowfullscreen>
            </iframe>
            </body>
            </html>
        """.trimIndent()

        webView.loadHTMLString(
            string = html,
            baseURL = NSURL.URLWithString("https://br.com.camilacunha.focalis"),
        )
    }

    UIKitView(
        factory = { webView },
        modifier = modifier,
    )
}