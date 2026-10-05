package br.com.camilacunha.focalis

import androidx.compose.ui.window.ComposeUIViewController
import br.com.camilacunha.focalis.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

private var koinStarted = false

fun MainViewController(): UIViewController {
    if (!koinStarted) {
        startKoin { modules(appModule) }
        koinStarted = true
    }
    return ComposeUIViewController { App() }
}