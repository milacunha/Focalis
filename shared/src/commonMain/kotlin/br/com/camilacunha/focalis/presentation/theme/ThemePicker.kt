package br.com.camilacunha.focalis.presentation.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Seletor de tema expansível
 */
@Composable
fun ThemePicker(
    current: AppTheme,
    onSelect: (AppTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier.fillMaxSize()) {
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = { expanded = false },
                    ),
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppTheme.entries.forEachIndexed { index, theme ->
                if (theme == current) return@forEachIndexed

                val delay = (AppTheme.entries.lastIndex - index) * 40

                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(tween(200, delayMillis = delay)) +
                            slideInHorizontally(tween(200, delayMillis = delay)) { it / 2 },
                    exit = fadeOut(tween(150)) +
                            slideOutHorizontally(tween(150)) { it / 2 },
                ) {
                    ThemeDot(
                        theme = theme,
                        onClick = {
                            onSelect(theme)
                            expanded = false
                        },
                    )
                }
            }

            ThemeDot(
                theme = current,
                onClick = { expanded = !expanded },
                showBorder = true,
            )
        }
    }
}

@Composable
private fun ThemeDot(
    theme: AppTheme,
    onClick: () -> Unit,
    showBorder: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val color = theme.colorScheme().primary
    val borderColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (showBorder) Modifier.border(2.dp, borderColor, CircleShape)
                else Modifier
            )
            .clickable(onClick = onClick),
    )
}