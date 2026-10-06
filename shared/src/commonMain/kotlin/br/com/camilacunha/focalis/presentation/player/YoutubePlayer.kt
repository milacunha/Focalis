package br.com.camilacunha.focalis.presentation.player

/**
 * Estado do player do YouTube aberto.
 *
 * Guarda as duas formas de URL que o app precisa:
 *  - embedUrl: usada por Android (WebView) e iOS (WKWebView) pra renderizar
 *  - externalUrl: usada pelo Desktop pra abrir no navegador padrão
 *
 * Ambas derivam do mesmo ID, então mudam sempre juntas — modeladas
 * num único objeto pra não ter 2 states desalinhados.
 */
data class YoutubePlayer(
    val embedUrl: String,
    val externalUrl: String,
)