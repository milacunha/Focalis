package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.presentation.player.ActivePlayer
import br.com.camilacunha.focalis.presentation.theme.AppTheme
import br.com.camilacunha.focalis.presentation.theme.ThemePicker
import br.com.camilacunha.focalis.presentation.timer.TimerPhase
import br.com.camilacunha.focalis.presentation.ui.components.MediaActionBar
import br.com.camilacunha.focalis.presentation.ui.components.PlayerContainer

enum class ActiveDialog { None, Youtube, Spotify }

@Composable
fun TimerScreenContent(
    phase: TimerPhase,
    formattedRemaining: String,
    canEditDuration: Boolean,
    minutesText: String,
    activePlayer: ActivePlayer?,
    playerVisible: Boolean,
    currentTheme: AppTheme,
    snackbarHostState: SnackbarHostState,
    currentDialog: ActiveDialog,
    onMinutesTextChange: (String) -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit,
    onHidePlayer: () -> Unit,
    onShowPlayer: () -> Unit,
    onSelectTheme: (AppTheme) -> Unit,
    onDialogChange: (ActiveDialog) -> Unit,
    onConfirmYoutube: (String) -> Boolean,
    onConfirmSpotify: (String) -> Boolean,
) {
    UrlDialogs(
        activeDialog = currentDialog,
        onDismiss = { onDialogChange(ActiveDialog.None) },
        onConfirmYoutube = onConfirmYoutube,
        onConfirmSpotify = onConfirmSpotify
    )

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
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
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

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
                            if (phase == TimerPhase.Idle) {
                                DurationInput(
                                    minutesText = minutesText,
                                    canEdit = canEditDuration,
                                    onValueChange = onMinutesTextChange,
                                )
                            }
                        }

                        TimerDisplay(
                            formatted = formattedRemaining,
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        TimerControls(
                            phase = phase,
                            onStart = onStart,
                            onPause = onPause,
                            onResume = onResume,
                            onReset = onReset,
                        )
                    }

                    PlayerContainer(
                        activePlayer = activePlayer,
                        playerVisible = playerVisible,
                        onClose = onHidePlayer,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.30f)
                    )
                }

                MediaActionBar(
                    activePlayer = activePlayer,
                    playerVisible = playerVisible,
                    onShowPlayer = onShowPlayer,
                    onOpenYoutube = { onDialogChange(ActiveDialog.Youtube) },
                    onOpenSpotify = { onDialogChange(ActiveDialog.Spotify) }
                )
            }

            ThemePicker(
                current = currentTheme,
                onSelect = onSelectTheme,
            )
        }
    }
}