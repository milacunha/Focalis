package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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

/**
 * Tela única. Consome TimerViewModel e compõe os subcomponentes.
 *
 * Regra de ouro do layout: timer sempre visível; botões sempre acessíveis.
 * Espaço reservado pra player (YouTube/Spotify) no futuro: o `Spacer` no meio
 * já antecipa isso sem implementar nada ainda.
 */
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    KeepScreenOn(enabled = state.phase.shouldKeepScreenOn())

    val snackbarHostState = remember { SnackbarHostState() }

    var minutesText by remember(state.durationMinutes, state.phase) {
        mutableStateOf(state.durationMinutes.toString())
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
                .padding(innerPadding)
                .padding(24.dp),
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
    }
}