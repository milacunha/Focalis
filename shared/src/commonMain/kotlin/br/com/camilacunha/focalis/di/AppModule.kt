package br.com.camilacunha.focalis.di

import br.com.camilacunha.focalis.domain.model.TimerConfig
import br.com.camilacunha.focalis.domain.timer.TimerEngine
import br.com.camilacunha.focalis.presentation.timer.TimerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { TimerConfig.Default }
    single { TimerEngine(timeSource = kotlin.time.TimeSource.Monotonic, config = get()) }

    viewModel { TimerViewModel(engine = get(), soundPlayer = get()) }
}