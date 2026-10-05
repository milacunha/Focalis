package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.timer.TimerPhase

/**
 * Botões da tela, variando conforme a fase.
 * - Idle: [Iniciar]
 * - Running: [Pausar] [Reset]
 * - Paused: [Retomar] [Reset]
 */
@Composable
internal fun TimerControls(
    phase: TimerPhase,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        when (phase) {
            TimerPhase.Idle -> {
                Button(onClick = onStart) { Text("Iniciar") }
            }
            TimerPhase.Running -> {
                Button(onClick = onPause) { Text("Pausar") }
                OutlinedButton(onClick = onReset) { Text("Reset") }
            }
            TimerPhase.Paused -> {
                Button(onClick = onResume) { Text("Retomar") }
                OutlinedButton(onClick = onReset) { Text("Reset") }
            }
        }
    }
}