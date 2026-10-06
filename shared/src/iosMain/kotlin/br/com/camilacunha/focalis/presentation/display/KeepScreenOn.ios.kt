package br.com.camilacunha.focalis.presentation.display

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication

/**
 * Mantém a tela ligada no iOS via UIApplication.idleTimerDisabled.
 *
 * idleTimerDisabled é uma propriedade global do app — afeta a tela
 * inteira, não só a view atual. Apesar de "global", é o jeito oficial
 * e não há alternativa melhor
 */
@Composable
actual fun KeepScreenOn(enabled: Boolean) {
    DisposableEffect(enabled) {
        UIApplication.sharedApplication.idleTimerDisabled = enabled

        onDispose {
            UIApplication.sharedApplication.idleTimerDisabled = false
        }
    }
}