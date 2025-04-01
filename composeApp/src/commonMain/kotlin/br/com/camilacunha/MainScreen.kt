package br.com.camilacunha

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import br.com.camilacunha.theme.orange
import br.com.camilacunha.theme.white

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        val timerScreen = timer.value
        val text by remember { mutableStateOf("5") }
        val textMeasurer = rememberTextMeasurer()

        Column(
            modifier = Modifier.fillMaxSize().background(color = white).padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(100.dp), onDraw = {
                val textResult = textMeasurer.measure(timerScreen)

                drawCircle(
                    color = Color.Blue, center = Offset(
                        x = center.x,
                        y = center.y
                    )
                )
                drawCircle(color = Color.Red, radius = 90f)
                drawText(
                    textResult, topLeft = Offset(
                        x = center.x - textResult.size.width / 2,
                        y = center.y - textResult.size.height / 2,
                    )
                )
            })

            if (timerIsRunning.value) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = orange),
                    onClick = { stop() }) {
                    Icon(imageVector = Icons.Sharp.Close, "stop")
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