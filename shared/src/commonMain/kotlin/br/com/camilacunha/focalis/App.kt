package br.com.camilacunha.focalis

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.camilacunha.focalis.presentation.ui.TimerScreen
import br.com.camilacunha.focalis.resources.Res

@Composable
fun App() {
    LaunchedEffect(Unit) {
        val bytes = Res.readBytes("files/beep.wav")
        println("Focalis: wav lido, ${bytes.size} bytes")
    }

    MaterialTheme {
        TimerScreen()
    }
}