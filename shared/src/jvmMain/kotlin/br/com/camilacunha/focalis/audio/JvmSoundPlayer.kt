package br.com.camilacunha.focalis.audio

import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.resources.Res
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

/**
 * Toca o som de fim de timer no Desktop via Clip.
 */
internal class JvmSoundPlayer : SoundPlayer {

    private val clip: Clip

    init {
        val bytes: ByteArray = runBlocking { Res.readBytes("files/beep.wav") }
        val audioStream = AudioSystem.getAudioInputStream(ByteArrayInputStream(bytes))
        clip = AudioSystem.getClip().apply {
            open(audioStream)
            audioStream.close()
        }
    }

    override fun play() {
        if (clip.isRunning) {
            clip.stop()
        }
        clip.framePosition = 0
        clip.start()
    }
}