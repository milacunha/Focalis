package br.com.camilacunha.focalis.domain.model

import kotlin.time.Duration
import kotlin.time.TimeMark

/**
 * Estados possíveis do timer. Sem estado "Finished"
 * O estado final é transitório: volta para Idle e emite evento.
 */
sealed interface TimerState {
    val duration: Duration

    data class Idle(override val duration: Duration) : TimerState

    data class Running(
        override val duration: Duration,
        val endTime: TimeMark,
    ) : TimerState

    data class Paused(
        override val duration: Duration,
        val remaining: Duration,
        val pausedAt: TimeMark,
    ) : TimerState
}