package br.com.camilacunha

import androidx.compose.runtime.Composable

@Composable
fun MainScreen() {
    val shouldStopTimer = timerIsRunning.value
    val timerScreen = showTimeInScreen.value

    val controlText = if (shouldStopTimer) {
        "Parar"
    } else {
        "Iniciar"
    }

    MainContent(
        timeInScreen = timerScreen,
        controlClick = {
            if (shouldStopTimer) {
                stop()
            } else {
                start()
            }
        },
        controlText = controlText,
        menuOptions = MenuOptions.entries,
        focusOptions = FocusOptions.entries
    )
}