package br.com.camilacunha.focalis.presentation.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect

fun Modifier.horizontalClip(progress: Float): Modifier = this.drawWithContent {
    if (progress <= 0f) return@drawWithContent
    val inset = size.width / 2f * (1f - progress)
    clipRect(
        left = inset,
        top = 0f,
        right = size.width - inset,
        bottom = size.height
    ) {
        this@drawWithContent.drawContent()
    }
}