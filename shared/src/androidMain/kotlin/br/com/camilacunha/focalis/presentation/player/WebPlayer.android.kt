package br.com.camilacunha.focalis.presentation.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Renderiza o conteúdo embedado via WebView do Android.
 *
 * Configurações essenciais:
 *  - javaScriptEnabled: o embed do YouTube é um iframe JS, sem isso não carrega.
 *  - domStorageEnabled: o player usa localStorage pra preferências (qualidade, etc).
 *  - WebViewClient vazio: mantém a navegação dentro do próprio WebView,
 *    em vez de abrir no navegador ou em uma nova Activity.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebPlayer(
    modifier: Modifier,
    embedUrl: String,
    externalUrl: String,
) {
    val lastLoadedUrl = remember { arrayOfNulls<String>(1) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                lastLoadedUrl[0] = embedUrl
                loadUrl(embedUrl, mapOf("Referer" to "https://br.com.camilacunha.focalis"))
            }
        },
        update = { webView ->
            if (lastLoadedUrl[0] != embedUrl) {
                lastLoadedUrl[0] = embedUrl
                webView.loadUrl(embedUrl, mapOf("Referer" to "https://br.com.camilacunha.focalis"))
            }
        },
    )
}