package br.com.camilacunha.focalis.presentation.display

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView

/**
 * Mantém a tela ligada via View.keepScreenOn.
 *
 * O LocalView do Compose é a ComposeView raiz da Activity. Setar
 * keepScreenOn nela propaga o FLAG_KEEP_SCREEN_ON pra Window inteira.
 *
 * O DisposableEffect com `enabled` como chave:
 *  - quando enabled vira true → seta o flag
 *  - quando enabled vira false → remove o flag
 *  - quando o Composable sai da composição → onDispose remove o flag
 *    (evita vazar "tela sempre ligada" pro resto do app)
 */
@Composable
actual fun KeepScreenOn(enabled: Boolean) {
    val view = LocalView.current

    DisposableEffect(enabled) {
        view.keepScreenOn = enabled
        onDispose {
            view.keepScreenOn = false
        }
    }
}