package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import br.com.camilacunha.focalis.presentation.timer.TimerViewModel
import br.com.camilacunha.focalis.presentation.timer.shouldKeepScreenOn
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val activePlayer by viewModel.activePlayer.collectAsStateWithLifecycle()
    val playerOpen = activePlayer != null

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(if (playerOpen) 0.4f else 1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                DurationInput(
                    minutesText = minutesText,
                    canEdit = state.canEditDuration,
                    onValueChange = { newText ->
                        minutesText = newText
                        newText.toIntOrNull()?.let { viewModel.setDuration(it) }
                    },
                )

                TimerDisplay(
                    formatted = state.formattedRemaining,
                    modifier = Modifier.padding(vertical = 32.dp),
                )

                TimerControls(
                    phase = state.phase,
                    onStart = viewModel::start,
                    onPause = viewModel::pause,
                    onResume = viewModel::resume,
                    onReset = viewModel::reset,
                )
            }

            if (activePlayer != null) {
                val yt = activePlayer!!
                PlayerArea(
                    embedUrl = yt.embedUrl,
                    externalUrl = yt.externalUrl,
                    onClose = viewModel::closePlayer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = { showYoutubeDialog = true }) {
                    Text("▶", style = MaterialTheme.typography.titleLarge)
                }
                TextButton(onClick = { showSpotifyDialog = true }) {
                    Text("♫", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}