package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.timer.TimerPhase
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.timer_pause
import br.com.camilacunha.focalis.resources.timer_reset
import br.com.camilacunha.focalis.resources.timer_resume
import br.com.camilacunha.focalis.resources.timer_start
import org.jetbrains.compose.resources.stringResource

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
                Button(onClick = onStart) {
                    Text(
                        text = stringResource(Res.string.timer_start),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            TimerPhase.Running -> {
                Button(onClick = onPause) {
                    Text(
                        text = stringResource(Res.string.timer_pause),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                OutlinedButton(onClick = onReset) {
                    Text(
                        text = stringResource(Res.string.timer_reset),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            TimerPhase.Paused -> {
                Button(onClick = onResume) {
                    Text(
                        text = stringResource(Res.string.timer_resume),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                OutlinedButton(onClick = onReset) {
                    Text(
                        text = stringResource(Res.string.timer_reset),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}