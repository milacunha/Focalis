package br.com.camilacunha.focalis.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FocalisTheme(
    theme: AppTheme,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = theme.colorScheme(),
        typography = focalisTypography(),
        shapes = FocalisShapes,
        content = content,
    )
}