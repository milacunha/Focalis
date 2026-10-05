package br.com.camilacunha.focalis.audio

import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.resources.Res
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.runBlocking
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.AVAudioSessionCategoryOptionMixWithOthers
import platform.AVFAudio.setActive
import platform.Foundation.NSData
import platform.Foundation.create

/**
 * Toca o som de fim de timer no iOS via AVAudioPlayer.
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal class IosSoundPlayer : SoundPlayer {

    private val player: AVAudioPlayer

    init {
        configureAudioSession()
        player = createPlayer()
    }

    private fun configureAudioSession() {
        val session = AVAudioSession.sharedInstance()
        session.setCategory(
            category = AVAudioSessionCategoryPlayback,
            withOptions = AVAudioSessionCategoryOptionMixWithOthers,
            error = null,
        )
        session.setActive(true, error = null)
    }

    private fun createPlayer(): AVAudioPlayer {
        val bytes: ByteArray = runBlocking { Res.readBytes("files/beep.wav") }
        val data: NSData = bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }

        return AVAudioPlayer(data = data, error = null).apply {
            prepareToPlay()
        }
    }

    override fun play() {
        if (player.playing) {
            player.currentTime = 0.0
        }
        player.play()
    }
}