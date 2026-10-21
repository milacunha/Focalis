package br.com.camilacunha.focalis.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import br.com.camilacunha.focalis.presentation.ui.PlayerArea

@Composable
fun PlayerContainer(
    activePlayer: ActivePlayer?,
    playerVisible: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = activePlayer != null && playerVisible,
        modifier = modifier,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(800, easing = FastOutSlowInEasing)),
    ) {
        if (activePlayer != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                PlayerArea(
                    embedUrl = activePlayer.embedUrl,
                    externalUrl = activePlayer.externalUrl,
                    onClose = onClose,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}