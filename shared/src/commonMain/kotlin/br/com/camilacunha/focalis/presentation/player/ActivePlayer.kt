package br.com.camilacunha.focalis.presentation.player

/**
 * Estado do player aberto na tela única.
 *
 * Regra de produto: apenas um player ativo por vez. Modelado como sealed
 * pra tornar isso impossível de representar de outra forma — não há
 * como ter YouTube e Spotify simultaneamente.
 *
 * Cada subtipo carrega as duas URLs que plataformas diferentes usam:
 *  - embedUrl: Android (WebView), iOS (WKWebView)
 *  - externalUrl: Desktop (abre no navegador padrão)
 */
sealed interface ActivePlayer {
    val embedUrl: String
    val externalUrl: String

    data class Youtube(
        override val embedUrl: String,
        override val externalUrl: String,
    ) : ActivePlayer

    data class Spotify(
        override val embedUrl: String,
        override val externalUrl: String,
    ) : ActivePlayer
}