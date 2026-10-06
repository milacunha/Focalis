package br.com.camilacunha.focalis.presentation.timer

/**
 * Fase da UI derivada do TimerState do domínio.
 * A UI usa isso para decidir quais botões mostrar e seus labels.
 */
enum class TimerPhase {
    Idle,
    Running,
    Paused
}

/**
 * Regra de produto: manter a tela ligada durante execução E durante a pausa
 */
internal fun TimerPhase.shouldKeepScreenOn(): Boolean = when (this) {
    TimerPhase.Idle -> false
    TimerPhase.Running -> true
    TimerPhase.Paused -> true
}