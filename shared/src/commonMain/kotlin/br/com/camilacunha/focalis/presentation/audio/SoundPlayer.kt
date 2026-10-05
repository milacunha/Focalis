package br.com.camilacunha.focalis.presentation.audio

/**
 * Contrato de reprodução de som, agnóstico de plataforma.
 * Implementações reais: AndroidSoundPlayer, IosSoundPlayer, JvmSoundPlayer.
 */
interface SoundPlayer {
    fun play()
}