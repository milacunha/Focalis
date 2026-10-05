package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Mostra MM:SS grande no centro da tela.
 * Sem lógica — recebe string pronta.
 */
@Composable
internal fun TimerDisplay(
    formatted: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = formatted,
            fontSize = 96.sp,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.displayLarge,
        )
    }
}