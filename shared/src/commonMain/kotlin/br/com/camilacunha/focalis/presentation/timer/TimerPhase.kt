package br.com.camilacunha.focalis.presentation.timer

/**
 * Fase da UI derivada do TimerState do domínio.
 * A UI usa isso para decidir quais botões mostrar e seus labels.
 */
enum class TimerPhase {
    /** Parado, duração editável. Botão: "Iniciar". */
    Idle,

    /** Rodando. Botões: "Pausar" + "Reset". */
    Running,

    /** Pausado. Botões: "Retomar" + "Reset". */
    Paused,
}