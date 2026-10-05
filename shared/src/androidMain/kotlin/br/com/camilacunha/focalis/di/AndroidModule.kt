package br.com.camilacunha.focalis.di

import br.com.camilacunha.focalis.audio.AndroidSoundPlayer
import br.com.camilacunha.focalis.presentation.audio.SoundPlayer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SoundPlayer> { AndroidSoundPlayer() }
}