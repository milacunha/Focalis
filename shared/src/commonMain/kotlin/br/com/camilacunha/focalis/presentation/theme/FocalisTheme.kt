package br.com.camilacunha.focalis.presentation.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource

@Composable
fun FocalisTheme(
    theme: AppTheme,
    content: @Composable () -> Unit,
) {
    val scheme = theme.colorScheme()
    val isLight = scheme.background.luminance() > 0.5f

    val centerAlpha = if (isLight) 0.35f else 0.55f
    val edgeAlpha = if (isLight) 0.1f else 0.3f

    MaterialTheme(
        colorScheme = scheme,
        typography = focalisTypography(),
        shapes = FocalisShapes,
    ) {

        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(theme.backgroundImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to scheme.background.copy(alpha = edgeAlpha),
                                0.5f to scheme.background.copy(alpha = centerAlpha),
                                1.0f to scheme.background.copy(alpha = edgeAlpha),
                            )
                        ),
                    ),
            )

            content()
        }
    }
}