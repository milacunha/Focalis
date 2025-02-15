package br.com.camilacunha

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val timerScreen = timer.value
        var text by remember { mutableStateOf("") }

        Column {
            TextField(
                text,
                onValueChange = { newText ->
                    text = newText
                }
            )
            Text(text = timerScreen)
            Button(onClick = {
                timeInSeconds.value = text.toInt()
                start()
            }) {
                Text("Começar")
            }
            Button(onClick = { stop() }) {
                Text("Parar")
            }
        }
    }
}