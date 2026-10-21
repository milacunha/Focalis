package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.runtime.Composable
import br.com.camilacunha.focalis.presentation.player.DEFAULT_SPOTIFY_URL
import br.com.camilacunha.focalis.presentation.player.DEFAULT_YOUTUBE_URL
import br.com.camilacunha.focalis.presentation.ui.components.UrlDialog
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.error_invalid_spotify_url
import br.com.camilacunha.focalis.resources.error_invalid_youtube_url
import br.com.camilacunha.focalis.resources.spotify_dialog_title
import br.com.camilacunha.focalis.resources.youtube_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun UrlDialogs(
    activeDialog: ActiveDialog,
    onDismiss: () -> Unit,
    onConfirmYoutube: (String) -> Boolean,
    onConfirmSpotify: (String) -> Boolean
) {
    when (activeDialog) {
        ActiveDialog.Youtube -> {
            UrlDialog(
                title = stringResource(Res.string.youtube_dialog_title),
                initialUrl = DEFAULT_YOUTUBE_URL,
                errorMessage = stringResource(Res.string.error_invalid_youtube_url),
                onDismiss = onDismiss,
                onConfirm = onConfirmYoutube,
            )
        }
        ActiveDialog.Spotify -> {
            UrlDialog(
                title = stringResource(Res.string.spotify_dialog_title),
                initialUrl = DEFAULT_SPOTIFY_URL,
                errorMessage = stringResource(Res.string.error_invalid_spotify_url),
                onDismiss = onDismiss,
                onConfirm = onConfirmSpotify,
            )
        }
        ActiveDialog.None -> Unit
    }
}