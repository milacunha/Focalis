package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.camilacunha.focalis.presentation.display.KeepScreenOn
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import br.com.camilacunha.focalis.presentation.theme.ThemeViewModel
import br.com.camilacunha.focalis.presentation.timer.TimerPhase
import br.com.camilacunha.focalis.presentation.timer.TimerViewModel
import br.com.camilacunha.focalis.presentation.timer.shouldKeepScreenOn
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.change_theme
import br.com.camilacunha.focalis.resources.open_spotify
import br.com.camilacunha.focalis.resources.open_youtube
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

    KeepScreenOn(enabled = state.phase.shouldKeepScreenOn())

    val snackbarHostState = remember { SnackbarHostState() }
    var showYoutubeDialog by remember { mutableStateOf(false) }
    var showSpotifyDialog by remember { mutableStateOf(false) }

    var minutesText by remember(state.durationMinutes, state.phase) {
        mutableStateOf(state.durationMinutes.toString())
    }

    if (showYoutubeDialog) {
        YoutubeUrlDialog(
            onDismiss = { showYoutubeDialog = false },
            onConfirm = viewModel::openYoutube,
        )
    }
    if (showSpotifyDialog) {
        SpotifyUrlDialog(
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = { themeViewModel.cycle() }) {
                    Text(
                        text = stringResource(Res.string.change_theme),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

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
                        modifier = Modifier.padding(vertical = 16.dp),
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
    }
}