package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.player.WebPlayer
import br.com.camilacunha.focalis.presentation.theme.FocalisShapes

@Composable
internal fun PlayerArea(
    embedUrl: String,
    externalUrl: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clipToBounds()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 42.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    FocalisShapes.extraSmall
                ),
        ) {
            WebPlayer(
                embedUrl = embedUrl,
                externalUrl = externalUrl,
                modifier = Modifier.fillMaxSize(),
            )
        }

        TextButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopEnd),
        ) {
            Text(
                text = "✕",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}