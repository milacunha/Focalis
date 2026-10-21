package br.com.camilacunha.focalis.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import br.com.camilacunha.focalis.presentation.theme.blueGlitch
import br.com.camilacunha.focalis.presentation.theme.redGlitch
import kotlin.random.Random

@Composable
fun GlitchText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current,
    glitchDurationMs: Long = 500
) {
    var displayedText by remember { mutableStateOf(text) }
    var glitching by remember { mutableStateOf(false) }
    var isFirst by remember { mutableStateOf(true) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var rgbShift by remember { mutableStateOf(0f) }
    var mainAlpha by remember { mutableStateOf(1f) }
    var rgbAlpha by remember { mutableStateOf(0f) }
    var sliceIndex by remember { mutableStateOf(0) }
    var sliceOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(text) {
        if (isFirst) { isFirst = false; return@LaunchedEffect }
        if (text == displayedText) return@LaunchedEffect

        glitching = true
        try {
            val start = withFrameNanos { it }
            var switched = false

            while (true) {
                val now = withFrameNanos { it }
                val elapsed = (now - start) / 1_000_000f
                if (elapsed >= glitchDurationMs) break

                val t = elapsed / glitchDurationMs
                val intensity = 1f - t * 0.3f

                offsetX   = (Random.nextFloat() - 0.5f) * 14f * intensity
                offsetY   = (Random.nextFloat() - 0.5f) * 2f  * intensity
                rgbShift  = Random.nextFloat() * 6f * intensity
                mainAlpha = if (Random.nextFloat() > 0.3f) 1f else Random.nextFloat() * 0.5f
                rgbAlpha  = 0.85f * intensity
                sliceIndex  = Random.nextInt(4)
                sliceOffset = (Random.nextFloat() - 0.5f) * 16f * intensity

                if (!switched && t >= 0.5f) {
                    displayedText = text
                    switched = true
                }
            }
        } finally {
            displayedText = text
            glitching = false
            offsetX = 0f; offsetY = 0f; rgbShift = 0f
            mainAlpha = 1f; rgbAlpha = 0f; sliceOffset = 0f
        }
    }

    Box(modifier) {
        if (glitching && rgbAlpha > 0.01f) {
            // red glitch
            Text(
                text = displayedText,
                style = style,
                color = redGlitch,
                maxLines = 1,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = offsetX - rgbShift
                        translationY = offsetY
                        alpha = rgbAlpha
                    }
                    .glitchSlice(glitching, sliceOffset, sliceIndex)
            )
            // blue glitch
            Text(
                text = displayedText,
                style = style,
                color = blueGlitch,
                maxLines = 1,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = offsetX + rgbShift
                        translationY = offsetY
                        alpha = rgbAlpha
                    }
                    .glitchSlice(glitching, sliceOffset, sliceIndex)
            )
        }

        Text(
            text = displayedText,
            style = style,
            color = color,
            maxLines = 1,
            modifier = Modifier
                .graphicsLayer {
                    translationX = offsetX
                    translationY = offsetY
                    alpha = mainAlpha
                }
                .glitchSlice(glitching, sliceOffset, sliceIndex)
        )
    }
}

private fun Modifier.glitchSlice(
    enabled: Boolean,
    sliceOffset: Float,
    sliceIndex: Int,
    sliceCount: Int = 4
): Modifier = drawWithContent {
    if (!enabled) {
        drawContent()
        return@drawWithContent
    }
    val bandH = size.height / sliceCount
    for (i in 0 until sliceCount) {
        val dx = if (i == sliceIndex) sliceOffset else 0f
        clipRect(0f, i * bandH, size.width, (i + 1) * bandH) {
            translate(left = dx, top = 0f) {
                this@drawWithContent.drawContent()
            }
        }
    }
}