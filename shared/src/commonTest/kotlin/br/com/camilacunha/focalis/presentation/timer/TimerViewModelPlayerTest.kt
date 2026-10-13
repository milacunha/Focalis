package br.com.camilacunha.focalis.presentation.timer

import br.com.camilacunha.focalis.domain.model.TimerConfig
import br.com.camilacunha.focalis.domain.timer.TimerEngine
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.TestTimeSource

@OptIn(ExperimentalCoroutinesApi::class)
class TimerViewModelPlayerTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() { Dispatchers.setMain(testDispatcher) }

    @AfterTest
    fun tearDown() { Dispatchers.resetMain() }

    private class FakeSoundPlayer : SoundPlayer {
        override fun play() = Unit
    }

    private fun newViewModel(): TimerViewModel {
        val timeSource = TestTimeSource()
        val engine = TimerEngine(timeSource = timeSource, config = TimerConfig.Default)
        return TimerViewModel(engine = engine, soundPlayer = FakeSoundPlayer())
    }

    private val youtubeUrl = "https://youtu.be/dQw4w9WgXcQ"
    private val spotifyUrl = "https://open.spotify.com/track/4cOdK2wGLETKBW3PvgPWqT"

    // ---------- YouTube ----------

    @Test
    fun opensYoutubeWithValidUrl() = runTest(testDispatcher) {
        val vm = newViewModel()
        assertTrue(vm.openYoutube(youtubeUrl))
        assertIs<ActivePlayer.Youtube>(vm.activePlayer.value)
    }

    @Test
    fun rejectsInvalidYoutubeUrl() = runTest(testDispatcher) {
        val vm = newViewModel()
        assertFalse(vm.openYoutube("https://example.com"))
        assertNull(vm.activePlayer.value)
    }

    // ---------- Spotify ----------

    @Test
    fun opensSpotifyWithValidUrl() = runTest(testDispatcher) {
        val vm = newViewModel()
        assertTrue(vm.openSpotify(spotifyUrl))
        assertIs<ActivePlayer.Spotify>(vm.activePlayer.value)
    }

    @Test
    fun rejectsInvalidSpotifyUrl() = runTest(testDispatcher) {
        val vm = newViewModel()
        assertFalse(vm.openSpotify("https://example.com"))
        assertNull(vm.activePlayer.value)
    }

    // ---------- Um player por vez ----------

    @Test
    fun openingSpotifyClosesYoutube() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.openYoutube(youtubeUrl)
        assertIs<ActivePlayer.Youtube>(vm.activePlayer.value)

        vm.openSpotify(spotifyUrl)
        assertIs<ActivePlayer.Spotify>(vm.activePlayer.value)
    }

    @Test
    fun openingYoutubeClosesSpotify() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.openSpotify(spotifyUrl)
        assertIs<ActivePlayer.Spotify>(vm.activePlayer.value)

        vm.openYoutube(youtubeUrl)
        assertIs<ActivePlayer.Youtube>(vm.activePlayer.value)
    }

    // ---------- Fechar ----------

    @Test
    fun closePlayerClears() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.openYoutube(youtubeUrl)
        vm.closePlayer()
        assertNull(vm.activePlayer.value)
    }

    @Test
    fun closeOnEmptyIsNoOp() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.closePlayer()
        assertNull(vm.activePlayer.value)
    }

    // ---------- URLs embutidas ----------

    @Test
    fun youtubeEmbedAndWatchUrlsAreDifferent() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.openYoutube(youtubeUrl)
        val player = vm.activePlayer.value
        assertIs<ActivePlayer.Youtube>(player)
        assertTrue(player.embedUrl.contains("/embed/"))
        assertTrue(player.externalUrl.contains("/watch?v="))
        assertEquals("dQw4w9WgXcQ", player.embedUrl.substringAfterLast("/"))
    }

    @Test
    fun spotifyEmbedAndWatchUrlsAreDifferent() = runTest(testDispatcher) {
        val vm = newViewModel()
        vm.openSpotify(spotifyUrl)
        val player = vm.activePlayer.value
        assertIs<ActivePlayer.Spotify>(player)
        assertTrue(player.embedUrl.contains("/embed/"))
        assertTrue(player.embedUrl.contains("/track/"))
        assertTrue(player.externalUrl.contains("/track/"))
        assertFalse(player.externalUrl.contains("/embed/"))
    }
}