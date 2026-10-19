package br.com.camilacunha.focalis.presentation.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Guarda o tema atual. Sem persistência — fechar o app volta pro default.
*/
class ThemeViewModel : ViewModel() {

    private val _theme = MutableStateFlow(AppTheme.LightTerracotta)
    val theme: StateFlow<AppTheme> = _theme.asStateFlow()

    fun select(theme: AppTheme) {
        _theme.value = theme
    }
}