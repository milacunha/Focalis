package br.com.camilacunha.focalis.presentation.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Renderiza conteúdo web (vídeo YouTube, embed Spotify) dentro da tela.
 *
 * Cada plataforma implementa com sua tecnologia:
 *  - Android: AndroidView + WebView
 *  - iOS: UIKitView + WKWebView
 *  - Desktop: SwingPanel + JCEF (ou fallback via Desktop.browse)
 *
 * O parâmetro `url` já vem no formato pronto pra embed — quem chama
 * é responsável por montar (ex: "https://www.youtube.com/embed/VIDEO_ID").
 */
@Composable
expect fun WebPlayer(
    modifier: Modifier,
    embedUrl: String,
    externalUrl: String,
)