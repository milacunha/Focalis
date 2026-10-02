package br.com.camilacunha.focalis.domain.model

/** Eventos finais one-shot emitidos pelo engine para a UI reagir. */
sealed interface TimerEvent {
    data object Finished : TimerEvent
    data object ResetByInactivity : TimerEvent
    data object ResetByUser : TimerEvent
}