package br.com.camilacunha.focalis.presentation.display

import androidx.compose.runtime.Composable

/**
 * Enquanto [enabled] for true, mantém a tela do device ligada.
 * Quando enabled vira false (ou o Composable sai da composição),
 * restaura o comportamento normal do sistema.
 *
 * Cada plataforma implementa com a API nativa:
 *  - Android: FLAG_KEEP_SCREEN_ON na Window
 *  - iOS: UIApplication.idleTimerDisabled
 *  - Desktop: SetThreadExecutionState (Windows)
 */
@Composable
expect fun KeepScreenOn(enabled: Boolean)