package br.com.camilacunha.focalis.domain.timer

import br.com.camilacunha.focalis.domain.model.TimerConfig
import br.com.camilacunha.focalis.domain.model.TimerEvent
import br.com.camilacunha.focalis.domain.model.TimerState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

/**
 * Motor de timer puro
 *
 * Regra de ouro: NUNCA decrementa contador. Sempre calcula `remaining`
 * a partir do relógio (TimeMark), evitando drift acumulado.
 *
 * O `tick()` é chamado periodicamente pela camada de apresentação
 * apenas para avaliar transições automáticas (fim natural / timeout de pausa).
 * A UI também lê `remaining()` a cada tick para redesenhar.
 */
class TimerEngine(
    private val timeSource: TimeSource = TimeSource.Monotonic,
    private val config: TimerConfig = TimerConfig.Default,
) {
    private var _state: TimerState = TimerState.Idle(config.defaultDuration)
    val state: TimerState get() = _state

    private val _events = MutableSharedFlow<TimerEvent>(extraBufferCapacity = 8)
    val events: SharedFlow<TimerEvent> = _events.asSharedFlow()

    fun setDuration(minutes: Int): Boolean {
        if (_state !is TimerState.Idle) return false

        val duration = minutes.minutes
        if (duration < config.minDuration || duration > config.maxDuration) return false

        _state = TimerState.Idle(duration)
        return true
    }

    fun start() {
        val s = _state
        if (s !is TimerState.Idle) return

        _state = TimerState.Running(
            duration = s.duration,
            endTime = timeSource.markNow() + s.duration,
        )
    }

    fun pause() {
        val s = _state
        if (s !is TimerState.Running) return

        val remaining = (-s.endTime.elapsedNow()).coerceAtLeast(Duration.ZERO)

        _state = TimerState.Paused(
            duration = s.duration,
            remaining = remaining,
            pausedAt = timeSource.markNow(),
        )
    }

    fun resume() {
        val s = _state
        if (s !is TimerState.Paused) return

        if (s.pausedAt.elapsedNow() >= config.pauseTimeout) {
            _state = TimerState.Idle(s.duration)
            _events.tryEmit(TimerEvent.ResetByInactivity)
            return
        }

        _state = TimerState.Running(
            duration = s.duration,
            endTime = timeSource.markNow() + s.remaining,
        )
    }

    fun reset() {
        val s = _state
        if (s is TimerState.Idle) return

        _state = TimerState.Idle(s.duration)
        _events.tryEmit(TimerEvent.ResetByUser)
    }

    fun tick() {
        when (val s = _state) {
            is TimerState.Idle -> Unit

            is TimerState.Running -> {
                if (s.endTime.hasPassedNow()) {
                    _state = TimerState.Idle(s.duration)
                    _events.tryEmit(TimerEvent.Finished)
                }
            }

            is TimerState.Paused -> {
                if (s.pausedAt.elapsedNow() >= config.pauseTimeout) {
                    _state = TimerState.Idle(s.duration)
                    _events.tryEmit(TimerEvent.ResetByInactivity)
                }
            }
        }
    }

    fun remaining(): Duration = when (val s = _state) {
        is TimerState.Idle -> s.duration
        is TimerState.Running -> {
            (-s.endTime.elapsedNow()).coerceAtLeast(Duration.ZERO)
        }
        is TimerState.Paused -> s.remaining
    }
}