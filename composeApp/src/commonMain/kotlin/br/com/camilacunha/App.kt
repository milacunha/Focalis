package br.com.camilacunha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.theme.orange
import br.com.camilacunha.theme.white
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val timerScreen = timer.value
        var text by remember { mutableStateOf("5") }

        Column(
            modifier = Modifier.fillMaxSize().background(color = white)
        ) {
            TextField(
                text,
                onValueChange = { newText ->
                    text = newText
                }
            )
            Text(text = timerScreen)

            if (timerIsRunning.value) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                    onClick = { stop() }) {
                    Icon(imageVector = Icons.Sharp.Lock, "stop")
                }
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                    onClick = {
                        timeInSeconds.value = text.toInt()
                        start()
                    }) {
                    Icon(imageVector = Icons.Filled.PlayArrow, "play")
                }
            }

        }
    }
}