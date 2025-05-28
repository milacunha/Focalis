package br.com.camilacunha

import androidx.compose.runtime.Composable

@Composable
fun MainScreen() {
    val shouldStopTimer = timerIsRunning.value
    val timerScreen = showTimeInScreen.value

    MainContent(
        timeInScreen = timerScreen,
        controlButton = {
            if (shouldStopTimer) {
                stop()
            } else {
                start()
            }
        },
        settingsButton = { println("clicked settings button") },
        historyButton = { println("clicked history button") },
        focusButton = { println("clicked focus button") },
        cycleButton = { println("clicked cycle button") }
    )
}