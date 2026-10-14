package br.com.camilacunha.focalis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.camilacunha.focalis.presentation.theme.FocalisTheme
import br.com.camilacunha.focalis.presentation.theme.ThemeViewModel
import br.com.camilacunha.focalis.presentation.ui.TimerScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

    val themeViewModel: ThemeViewModel = koinViewModel()
    val theme by themeViewModel.theme.collectAsStateWithLifecycle()

    FocalisTheme(theme = theme) {
        TimerScreen()
    }
}