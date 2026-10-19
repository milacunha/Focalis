package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.camilacunha.focalis.presentation.display.KeepScreenOn
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import br.com.camilacunha.focalis.presentation.player.DEFAULT_SPOTIFY_URL
import br.com.camilacunha.focalis.presentation.player.DEFAULT_YOUTUBE_URL
import br.com.camilacunha.focalis.presentation.theme.ThemePicker
import br.com.camilacunha.focalis.presentation.theme.ThemeViewModel
import br.com.camilacunha.focalis.presentation.timer.TimerPhase
import br.com.camilacunha.focalis.presentation.timer.TimerViewModel
import br.com.camilacunha.focalis.presentation.timer.shouldKeepScreenOn
import br.com.camilacunha.focalis.presentation.ui.components.TextIcon
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.change_theme
import br.com.camilacunha.focalis.resources.error_invalid_spotify_url
import br.com.camilacunha.focalis.resources.error_invalid_youtube_url
import br.com.camilacunha.focalis.resources.open_spotify
import br.com.camilacunha.focalis.resources.open_youtube
import br.com.camilacunha.focalis.resources.spotify_dialog_title
import br.com.camilacunha.focalis.resources.youtube_dialog_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = koinViewModel(),
    themeViewModel: ThemeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val activePlayer by viewModel.activePlayer.collectAsStateWithLifecycle()
    val playerVisible by viewModel.playerVisible.collectAsStateWithLifecycle()
    val theme by themeViewModel.theme.collectAsStateWithLifecycle()

    KeepScreenOn(enabled = state.phase.shouldKeepScreenOn())

    val snackbarHostState = remember { SnackbarHostState() }
    var showYoutubeDialog by remember { mutableStateOf(false) }
    var showSpotifyDialog by remember { mutableStateOf(false) }

    var minutesText by remember(state.durationMinutes, state.phase) {
        mutableStateOf(state.durationMinutes.toString())
    }

    if (showYoutubeDialog) {
        UrlDialog(
            title = stringResource(Res.string.youtube_dialog_title),
            initialUrl = DEFAULT_YOUTUBE_URL,
            errorMessage = stringResource(Res.string.error_invalid_youtube_url),
            onDismiss = { showYoutubeDialog = false },
            onConfirm = viewModel::openYoutube,
        )
    }
    if (showSpotifyDialog) {
        UrlDialog(
            title = stringResource(Res.string.spotify_dialog_title),
            initialUrl = DEFAULT_SPOTIFY_URL,
            errorMessage = stringResource(Res.string.error_invalid_spotify_url),
            onDismiss = { showSpotifyDialog = false },
            onConfirm = viewModel::openSpotify,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            snackbarHostState.showSnackbar(event.toUserMessage())
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)
        { data ->
            Snackbar(
                modifier = Modifier.padding(bottom = 12.dp),
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.visuals.message,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
       }
                       },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(56.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier.height(80.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (state.phase == TimerPhase.Idle) {
                            DurationInput(
                                minutesText = minutesText,
                                canEdit = state.canEditDuration,
                                onValueChange = { newText ->
                                    minutesText = newText
                                    newText.toIntOrNull()?.let { viewModel.setDuration(it) }
                                },
                            )
                        }
                    }

                    TimerDisplay(
                        formatted = state.formattedRemaining,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )

                    TimerControls(
                        phase = state.phase,
                        onStart = viewModel::start,
                        onPause = viewModel::pause,
                        onResume = viewModel::resume,
                        onReset = viewModel::reset,
                    )
                }

                if (activePlayer != null && playerVisible) {
                    val p = activePlayer!!
                    PlayerArea(
                        embedUrl = p.embedUrl,
                        externalUrl = p.externalUrl,
                        onClose = viewModel::hidePlayer,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.30f),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = {
                        val current = activePlayer
                        if (current is ActivePlayer.Youtube && !playerVisible) {
                            viewModel.showPlayer()
                        } else {
                            showYoutubeDialog = true
                        }
                    },
                ) {
                    TextIcon(stringResource(Res.string.open_youtube))
                }
                TextButton(
                    onClick = {
                        val current = activePlayer
                        if (current is ActivePlayer.Spotify && !playerVisible) {
                            viewModel.showPlayer()
                        } else {
                            showSpotifyDialog = true
                        }
                    },
                ) {
                    TextIcon(stringResource(Res.string.open_spotify))
                }
            }
        }
            ThemePicker(
                current = theme,
                onSelect = themeViewModel::select,
            )
        }
    }
}