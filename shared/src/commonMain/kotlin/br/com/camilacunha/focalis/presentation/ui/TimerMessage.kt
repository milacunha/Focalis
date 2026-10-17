package br.com.camilacunha.focalis.presentation.ui

import br.com.camilacunha.focalis.domain.model.TimerEvent

/**
 * Traduz um TimerEvent para uma mensagem mostrada ao usuário.
 * Fica isolado aqui pra quando houver n mensagens novas
 */
internal fun TimerEvent.toUserMessage(): String = when (this) {
    TimerEvent.Finished -> "tempo finalizado com sucesso"
    TimerEvent.ResetByInactivity -> "timer cancelado por inatividade"
    TimerEvent.ResetByUser -> "timer reiniciado"
}