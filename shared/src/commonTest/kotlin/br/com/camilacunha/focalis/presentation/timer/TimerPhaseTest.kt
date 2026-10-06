package br.com.camilacunha.focalis.presentation.timer

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TimerPhaseTest {

    @Test
    fun idleDoesNotKeepScreenOn() {
        assertFalse(TimerPhase.Idle.shouldKeepScreenOn())
    }

    @Test
    fun runningKeepsScreenOn() {
        assertTrue(TimerPhase.Running.shouldKeepScreenOn())
    }

    @Test
    fun pausedKeepsScreenOn() {
        assertTrue(TimerPhase.Paused.shouldKeepScreenOn())
    }
}