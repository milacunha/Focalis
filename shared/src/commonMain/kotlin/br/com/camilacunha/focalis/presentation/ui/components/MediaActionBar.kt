package br.com.camilacunha.focalis.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.spotify_dialog_title
import br.com.camilacunha.focalis.resources.youtube_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaActionBar(
    activePlayer: ActivePlayer?,
    playerVisible: Boolean,
    onShowPlayer: () -> Unit,
    onOpenYoutube: () -> Unit,
    onOpenSpotify: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
            contentColor = MaterialTheme.colorScheme.primary
        )

        TextButton(
            onClick = {
                if (activePlayer is ActivePlayer.Youtube && !playerVisible) onShowPlayer()
                else onOpenYoutube()
            },
            colors = buttonColors
        ) {
            TextIcon(stringResource(Res.string.youtube_dialog_title))
        }

        Spacer(modifier = Modifier.width(4.dp))

        TextButton(
            onClick = {
                if (activePlayer is ActivePlayer.Spotify && !playerVisible) onShowPlayer()
                else onOpenSpotify()
            },
            colors = buttonColors
        ) {
            TextIcon(stringResource(Res.string.spotify_dialog_title))
        }
    }
}