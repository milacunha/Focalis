package br.com.camilacunha.focalis.presentation.timer

import androidx.lifecycle.viewModelScope
import br.com.camilacunha.focalis.domain.model.TimerConfig
import br.com.camilacunha.focalis.domain.timer.TimerEngine
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TestTimeSource

@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private var viewModel: TimerViewModel? = null

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        viewModel?.viewModelScope?.cancel()
        viewModel = null
        Dispatchers.resetMain()
    }

    private class FakeSoundPlayer : SoundPlayer {
        var playCount = 0
            private set
        override fun play() { playCount++ }
    }

    @Test
    fun playsSoundOnFinished() = runTest(testDispatcher) {
        val timeSource = TestTimeSource()
        val engine = TimerEngine(timeSource = timeSource, config = TimerConfig.Default)
        val sound = FakeSoundPlayer()
        val vm = TimerViewModel(engine = engine, soundPlayer = sound).also { viewModel = it }

        vm.setDuration(1)
        vm.start()
        timeSource += 1.minutes
        advanceTimeBy(300.milliseconds)

        assertEquals(1, sound.playCount)

        vm.viewModelScope.cancel()
    }

    @Test
    fun playsSoundOnInactivityTimeout() = runTest(testDispatcher) {
        val timeSource = TestTimeSource()
        val engine = TimerEngine(timeSource = timeSource, config = TimerConfig.Default)
        val sound = FakeSoundPlayer()
        val vm = TimerViewModel(engine = engine, soundPlayer = sound).also { viewModel = it }

        vm.setDuration(10)
        vm.start()
        timeSource += 2.minutes
        vm.pause()
        timeSource += 3.minutes
        advanceTimeBy(300.milliseconds)

        assertEquals(1, sound.playCount)

        vm.viewModelScope.cancel()
    }

    @Test
    fun doesNotPlaySoundOnUserReset() = runTest(testDispatcher) {
        val timeSource = TestTimeSource()
        val engine = TimerEngine(timeSource = timeSource, config = TimerConfig.Default)
        val sound = FakeSoundPlayer()
        val vm = TimerViewModel(engine = engine, soundPlayer = sound).also { viewModel = it }

        vm.setDuration(10)
        vm.start()
        vm.reset()
        advanceTimeBy(300.milliseconds)

        assertEquals(0, sound.playCount)

        vm.viewModelScope.cancel()
    }

    @Test
    fun doesNotPlaySoundOnNormalPauseResume() = runTest(testDispatcher) {
        val timeSource = TestTimeSource()
        val engine = TimerEngine(timeSource = timeSource, config = TimerConfig.Default)
        val sound = FakeSoundPlayer()
        val vm = TimerViewModel(engine = engine, soundPlayer = sound).also { viewModel = it }

        vm.setDuration(10)
        vm.start()
        timeSource += 1.minutes
        vm.pause()
        timeSource += 30.seconds
        vm.resume()
        advanceTimeBy(300.milliseconds)

        assertEquals(0, sound.playCount)

        vm.viewModelScope.cancel()
    }
}