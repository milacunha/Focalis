package br.com.camilacunha.focalis.presentation.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.camilacunha.focalis.domain.model.TimerEvent
import br.com.camilacunha.focalis.domain.model.TimerState
import br.com.camilacunha.focalis.domain.timer.TimerEngine
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.presentation.player.YoutubePlayer
import br.com.camilacunha.focalis.presentation.player.YoutubeUrlParser
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * Orquestra o TimerEngine e expõe estado/eventos prontos pra UI.
 *
 * Não contém regra de tempo — essa mora no engine. Aqui só:
 *  - repassa comandos do usuário pro engine
 *  - recalcula o UiState sob demanda - redesenha ui
 *  - roda um tick periódico que NÃO decide tempo - só avalia transições automáticas
 */
class TimerViewModel(
    private val engine: TimerEngine,
    private val soundPlayer: SoundPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(snapshot())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<TimerEvent>(extraBufferCapacity = 8)
    val events: SharedFlow<TimerEvent> = _events.asSharedFlow()

    private val _youtubePlayer = MutableStateFlow<YoutubePlayer?>(null)
    val youtubePlayer: StateFlow<YoutubePlayer?> = _youtubePlayer.asStateFlow()

    private var tickJob: Job? = null

    init {
        viewModelScope.launch {
            engine.events.collect { event ->

                if (event == TimerEvent.Finished || event == TimerEvent.ResetByInactivity) {
                    soundPlayer.play()
                }

                _events.emit(event)
            }
        }
    }

    fun setDuration(minutes: Int): Boolean {
        val ok = engine.setDuration(minutes)
        if (ok) refresh()
        return ok
    }

    fun start() {
        engine.start()
        refresh()
        ensureTicking()
    }

    fun pause() {
        engine.pause()
        refresh()
        ensureTicking()
    }

    fun resume() {
        engine.resume()
        refresh()
        ensureTicking()
    }

    fun reset() {
        engine.reset()
        refresh()
    }

    /**
     * Liga o tick se ainda não estiver ligado. Encerra sozinho quando o
     * engine volta pra Idle — sem trabalho em vazio.
     */
    private fun ensureTicking() {
        if (tickJob?.isActive == true) return
        tickJob = viewModelScope.launch {
            while (isActive) {
                engine.tick()
                refresh()
                if (engine.state is TimerState.Idle) {
                    tickJob = null
                    return@launch
                }
                delay(TICK_INTERVAL_MS.milliseconds)
            }
        }
    }

    private fun refresh() {
        _uiState.value = snapshot()
    }

    private fun snapshot(): TimerUiState =
        TimerUiState.from(state = engine.state, remaining = engine.remaining())

    fun openYoutube(rawInput: String): Boolean {
        val id = YoutubeUrlParser.extractId(rawInput) ?: return false
        _youtubePlayer.value = YoutubePlayer(
            embedUrl = YoutubeUrlParser.toEmbedUrl(id),
            externalUrl = YoutubeUrlParser.toWatchUrl(id),
        )
        return true
    }

    fun closeYoutube() {
        _youtubePlayer.value = null
    }

    private companion object {
        const val TICK_INTERVAL_MS = 250L
    }
}