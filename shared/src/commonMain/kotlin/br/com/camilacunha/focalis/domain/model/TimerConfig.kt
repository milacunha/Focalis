package br.com.camilacunha.focalis.domain.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * Parâmetros configuráveis do motor de timer.
 */
data class TimerConfig(
    val defaultDuration: Duration = 15.minutes,
    val minDuration: Duration = 1.minutes, //TODO("quando pronto, trocar para 5min")
    val maxDuration: Duration = 60.minutes,
    val pauseTimeout: Duration = 3.minutes,
) {
    companion object {
        val Default = TimerConfig()
    }
}