package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.timer.TimerPhase
import br.com.camilacunha.focalis.presentation.ui.components.GlitchText
import br.com.camilacunha.focalis.presentation.ui.components.horizontalClip
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
    val exitDuration = 1000
    val enterDelay  = 1000
    val enterDuration = 1000

    val idleVisible    = phase == TimerPhase.Idle
    val activeVisible = phase != TimerPhase.Idle

    fun specFor(visible: Boolean, dur: Int) = tween<Float>(
        durationMillis = dur,
        delayMillis = if (visible) enterDelay else 0,
        easing = FastOutSlowInEasing
    )

    val idleProgress by animateFloatAsState(
        targetValue = if (idleVisible) 1f else 0f,
        animationSpec = specFor(idleVisible, if (idleVisible) enterDuration else exitDuration),
        label = "idleProgress"
    )

    val activeProgress by animateFloatAsState(
        targetValue = if (activeVisible) 1f else 0f,
        animationSpec = specFor(activeVisible, if (activeVisible) enterDuration else exitDuration),
        label = "activeProgress"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // ---------- IDLE ----------
        Box(
            modifier = Modifier.horizontalClip(idleProgress)
        ) {
            StartButton(onStart)
        }

        // ---------- ACTIVE ----------
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.horizontalClip(activeProgress)
        ) {
            val isPaused = phase == TimerPhase.Paused

            var currentLabel by remember { mutableStateOf(Res.string.timer_pause) }
            LaunchedEffect(phase) {
                if (phase == TimerPhase.Running) currentLabel = Res.string.timer_pause
                if (phase == TimerPhase.Paused)  currentLabel = Res.string.timer_resume
            }

            Button(
                onClick = if (isPaused) onResume else onPause,
                modifier = Modifier.width(110.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    GlitchText(
                        text = stringResource(currentLabel),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            ResetButton(onReset)
        }
    }
}

@Composable
private fun StartButton(onStart: () -> Unit) {
    Button(onClick = onStart) {
        Text(
            text = stringResource(Res.string.timer_start),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun ResetButton(onReset: () -> Unit) {
    Button(
        onClick = onReset,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = stringResource(Res.string.timer_reset),
            style = MaterialTheme.typography.titleSmall
        )
    }
}