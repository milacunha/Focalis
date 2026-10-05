package br.com.camilacunha.focalis.audio

import android.media.AudioAttributes
import android.media.SoundPool
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import br.com.camilacunha.focalis.resources.Res

/**
 * Toca o som de fim de timer via SoundPool.
 *
 * SoundPool é ótimo pra sons curtos e repetidos: carrega uma vez na memória
 * e toca com latência mínima. O carregamento é assíncrono — só sabemos que
 * está pronto quando o OnLoadCompleteListener dispara.
 */
internal class AndroidSoundPlayer : SoundPlayer {

    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private var soundId: Int = 0
    private var loaded: Boolean = false
    private var pendingPlay: Boolean = false

    init {
        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                loaded = true
                if (pendingPlay) {
                    pendingPlay = false
                    playLoaded()
                }
            }
        }
        loadSound()
    }

    private fun loadSound() {
        val bytes = kotlinx.coroutines.runBlocking {
            Res.readBytes("files/beep.wav")
        }
        val cacheFile = java.io.File.createTempFile("beep", ".wav")

        cacheFile.deleteOnExit()
        cacheFile.writeBytes(bytes)

        soundId = soundPool.load(cacheFile.absolutePath, 1)
    }

    override fun play() {
        if (loaded) {
            playLoaded()
        } else {
            pendingPlay = true
        }
    }

    private fun playLoaded() {
        soundPool.play(
            soundId,
            1f,
            1f,
            1,
            0,
            1f,
        )
    }
}