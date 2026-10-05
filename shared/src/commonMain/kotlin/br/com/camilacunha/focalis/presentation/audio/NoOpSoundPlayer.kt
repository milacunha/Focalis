package br.com.camilacunha.focalis.presentation.audio

/**
 * Implementação vazia. Temporária, vai ser substituída
 * pelas versões de plataforma. Também útil em ambientes sem áudio.
 */
internal class NoOpSoundPlayer : SoundPlayer {
    override fun play() = Unit
}