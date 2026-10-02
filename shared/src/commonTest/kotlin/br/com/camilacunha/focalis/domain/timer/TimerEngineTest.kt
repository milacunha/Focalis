package br.com.camilacunha.focalis.domain.timer

import br.com.camilacunha.focalis.domain.model.TimerConfig
import br.com.camilacunha.focalis.domain.model.TimerEvent
import br.com.camilacunha.focalis.domain.model.TimerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TestTimeSource

class TimerEngineTest {

    private fun engine(
        source: TestTimeSource = TestTimeSource(),
        config: TimerConfig = TimerConfig.Default,
    ) = TimerEngine(timeSource = source, config = config)

    @Test
    fun startsIdleWithDefaultDuration() {
        val e = engine()
        val state = e.state
        assertIs<TimerState.Idle>(state)
        assertEquals(15.minutes, state.duration)
    }

    @Test
    fun setDurationOnlyWorksWhenIdle() {
        val e = engine()
        assertTrue(e.setDuration(30))
        assertEquals(30.minutes, e.state.duration)

        e.start()
        assertFalse(e.setDuration(10))
        assertEquals(30.minutes, e.state.duration)
    }

    @Test
    fun setDurationRejectsOutOfRange() {
        val e = engine()
        assertFalse(e.setDuration(0))
        assertFalse(e.setDuration(61))
        assertTrue(e.setDuration(1))
        assertTrue(e.setDuration(60))
    }

    @Test
    fun startMovesToRunningAndRemainingUsesClock() {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(10)
        e.start()
        assertIs<TimerState.Running>(e.state)
        assertEquals(10.minutes, e.remaining())

        src += 3.minutes
        assertEquals(7.minutes, e.remaining())
    }

    @Test
    fun pauseFreezesRemainingAndResumeContinuesWithoutDrift() {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(10)
        e.start()

        src += 4.minutes
        e.pause()
        assertEquals(6.minutes, e.remaining())

        src += 30.seconds
        assertEquals(6.minutes, e.remaining())

        e.resume()
        src += 1.minutes
        assertEquals(5.minutes, e.remaining())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tickTransitionsRunningToIdleWithFinishedEvent() = runTest {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(5)
        e.start()

        val events = mutableListOf<TimerEvent>()
        val job = launch { e.events.take(1).toList(events) }
        runCurrent()

        src += 5.minutes
        e.tick()
        job.join()

        assertIs<TimerState.Idle>(e.state)
        assertEquals(5.minutes, e.state.duration)
        assertEquals(listOf<TimerEvent>(TimerEvent.Finished), events)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tickEmitsResetByInactivityAfterThreeMinutesPaused() = runTest {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(10)
        e.start()
        src += 2.minutes
        e.pause()

        val events = mutableListOf<TimerEvent>()
        val job = launch { e.events.take(1).toList(events) }
        runCurrent()

        src += 3.minutes
        e.tick()
        job.join()

        assertIs<TimerState.Idle>(e.state)
        assertEquals(10.minutes, e.state.duration)
        assertEquals(listOf<TimerEvent>(TimerEvent.ResetByInactivity), events)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resumeAfterPauseTimeoutResetsDefensively() = runTest {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(10)
        e.start()
        src += 2.minutes
        e.pause()

        val events = mutableListOf<TimerEvent>()
        val job = launch { e.events.take(1).toList(events) }
        runCurrent()

        src += 3.minutes
        e.resume()
        job.join()

        assertIs<TimerState.Idle>(e.state)
        assertEquals(10.minutes, e.state.duration)
        assertEquals(listOf<TimerEvent>(TimerEvent.ResetByInactivity), events)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetEmitsResetByUser() = runTest {
        val src = TestTimeSource()
        val e = engine(src)
        e.setDuration(10)
        e.start()
        src += 1.minutes

        val events = mutableListOf<TimerEvent>()
        val job = launch { e.events.take(1).toList(events) }
        runCurrent()

        e.reset()
        job.join()

        assertIs<TimerState.Idle>(e.state)
        assertEquals(listOf<TimerEvent>(TimerEvent.ResetByUser), events)
    }
}