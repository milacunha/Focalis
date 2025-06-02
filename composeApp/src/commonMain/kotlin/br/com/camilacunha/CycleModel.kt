package br.com.camilacunha

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Call
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

data class CycleModel(
    val activeTime: String = "25",
    val restTime: String = "5",
    val cycles: String = "2",
    val date: String,
    val type: FocusOptions
)

class TimerState {
    var selectedMode by mutableStateOf("")
}

enum class FocusOptions(val type: String, val icon: ImageVector) {
    WORK("Trabalho", Icons.Outlined.Call),
    HOBBY("Lazer", Icons.Outlined.Call),
    STUDY("Estudo", Icons.Outlined.Call),
    CLEANING("Faxina", Icons.Outlined.Call),
    SELF_CARE("Self-Care", Icons.Outlined.Call),
    OTHERS("Outros", Icons.Outlined.Call),
}

enum class MenuOptions(val label: String, val icon: ImageVector, val click: () -> Unit) {
    SETTINGS(
        label = "Ajustes",
        icon = Icons.Default.Settings,
        click = { println("clicked settings button") }),
    HISTORY(
        label = "Histórico",
        icon = Icons.Default.Home,
        click = { println("clicked settings button") })
}