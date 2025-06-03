package br.com.camilacunha

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(450.dp, 550.dp),
        position = WindowPosition(100.dp, 100.dp)
    )

    Window(
        title = "Pomodoro",
        onCloseRequest = ::exitApplication,
        state = state
    ) {
        MainScreen()
    }
}