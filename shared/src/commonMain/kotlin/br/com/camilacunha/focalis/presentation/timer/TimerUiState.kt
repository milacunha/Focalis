package br.com.camilacunha.focalis.presentation.timer

import br.com.camilacunha.focalis.domain.model.TimerState
import kotlin.time.Duration

/**
 * Estado imutável que a UI observa.
 * Não contém referência ao engine nem lógica de tempo — só dados prontos pra desenhar.
 */
data class TimerUiState(
    val phase: TimerPhase,
    val remaining: Duration,
    val duration: Duration,
) {
    val formattedRemaining: String
        get() {
            val total = remaining.inWholeSeconds.coerceAtLeast(0)
            val m = (total / 60).toString().padStart(2, '0')
            val s = (total % 60).toString().padStart(2, '0')
            return "$m:$s"
        }

    val durationMinutes: Int get() = duration.inWholeMinutes.toInt()

    val canEditDuration: Boolean get() = phase == TimerPhase.Idle

    companion object {
        fun from(state: TimerState, remaining: Duration): TimerUiState {
            val phase = when (state) {
                is TimerState.Idle -> TimerPhase.Idle
                is TimerState.Running -> TimerPhase.Running
                is TimerState.Paused -> TimerPhase.Paused
            }
            return TimerUiState(
                phase = phase,
                remaining = remaining,
                duration = state.duration,
            )
        }
    }
}