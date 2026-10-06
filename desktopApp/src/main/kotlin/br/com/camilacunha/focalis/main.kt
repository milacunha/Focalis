package br.com.camilacunha.focalis

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import br.com.camilacunha.focalis.di.appModule
import br.com.camilacunha.focalis.di.platformModule
import org.koin.core.context.startKoin

fun main() {
    startKoin { modules(appModule, platformModule) }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Focalis",
        ) {
            App()
        }
    }
}