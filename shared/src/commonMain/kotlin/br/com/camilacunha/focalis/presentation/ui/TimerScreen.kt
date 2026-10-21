package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.camilacunha.focalis.presentation.display.KeepScreenOn
import br.com.camilacunha.focalis.presentation.theme.ThemeViewModel
import br.com.camilacunha.focalis.presentation.timer.TimerViewModel
import br.com.camilacunha.focalis.presentation.timer.shouldKeepScreenOn
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

    val snackbarHostState = remember { SnackbarHostState() }
    var currentDialog by remember { mutableStateOf(ActiveDialog.None) }

    var minutesText by remember(state.durationMinutes, state.phase) {
        mutableStateOf(state.durationMinutes.toString())
    }

    KeepScreenOn(enabled = state.phase.shouldKeepScreenOn())

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            snackbarHostState.showSnackbar(event.toUserMessage())
        }
    }

    TimerScreenContent(
        phase = state.phase,
        formattedRemaining = state.formattedRemaining,
        canEditDuration = state.canEditDuration,
        minutesText = minutesText,
        activePlayer = activePlayer,
        playerVisible = playerVisible,
        currentTheme = theme,
        snackbarHostState = snackbarHostState,
        currentDialog = currentDialog,
        onMinutesTextChange = { newText ->
            minutesText = newText
            newText.toIntOrNull()?.let { viewModel.setDuration(it) }
        },
        onStart = viewModel::start,
        onPause = viewModel::pause,
        onResume = viewModel::resume,
        onReset = viewModel::reset,
        onHidePlayer = viewModel::hidePlayer,
        onShowPlayer = viewModel::showPlayer,
        onSelectTheme = themeViewModel::select,
        onDialogChange = { currentDialog = it },
        onConfirmYoutube = { url ->
            val success = viewModel.openYoutube(url)
            if (success) currentDialog = ActiveDialog.None
            success
        },
        onConfirmSpotify = { url ->
            val success = viewModel.openSpotify(url)
            if (success) currentDialog = ActiveDialog.None
            success
        }
    )
}