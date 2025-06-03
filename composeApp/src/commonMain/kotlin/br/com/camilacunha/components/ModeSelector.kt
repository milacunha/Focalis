package br.com.camilacunha.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.camilacunha.FocusOptions
import br.com.camilacunha.TimerState
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun ModeSelector(
    focusOptions: List<FocusOptions>
) {
    var showFocusDialog by remember { mutableStateOf(false) }
    var showCyclesDialog by remember { mutableStateOf(false) }
    val timerState = remember { TimerState() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FocusSelector(
            state = timerState,
            onShowDialog = { showFocusDialog = true }
        )

        CyclesSelector(
            state = timerState,
            onShowDialog = { showCyclesDialog = true }
        )
    }

    if (showFocusDialog) {
        AlertDialogSelector(
            onDismissRequest = { showFocusDialog = false },
            confirmButton = {
                showFocusDialog = false
                timerState.selectedMode = "Foco"
            },
            dismissButton = { showFocusDialog = false },
            title = "FOCO",
            text = {
                Column {
                    Text(
                        "Escolha o foco da sua sessão:",
                        style = MaterialTheme.typography.body2,
                        color = ElegantOrangeTheme.TextSecondary
                    )

                    Spacer(Modifier.height(16.dp))

                    FocusOptionsList(
                        focusList = focusOptions,
                        onOptionSelected = { }
                    )
                }
            }
        )
    }

    if (showCyclesDialog) {
        AlertDialogSelector(
            onDismissRequest = { showCyclesDialog = false },
            confirmButton = {
                showCyclesDialog = false
                timerState.selectedMode = "Ciclos"
            },
            dismissButton = { showCyclesDialog = false },
            title = "CICLOS",
            text = {
                Column {
                    Text(
                        "Configure sua sessão:",
                        style = MaterialTheme.typography.body2,
                        color = ElegantOrangeTheme.TextSecondary
                    )

                    Spacer(Modifier.height(16.dp))

                    CyclePicker(initialCycles = 3)
                }
            }
        )
    }
}

@Composable
fun FocusSelector(
    state: TimerState,
    onShowDialog: () -> Unit
) {
    val isSelected = state.selectedMode == "Foco"

    ModeChip(
        title = "Foco",
        isSelected = isSelected,
        onClick = {
            state.selectedMode = "Foco"
            onShowDialog()
        }
    )
}

@Composable
fun CyclesSelector(
    state: TimerState,
    onShowDialog: () -> Unit
) {
    val isSelected = state.selectedMode == "Ciclos"

    ModeChip(
        title = "Ciclos",
        isSelected = isSelected,
        onClick = {
            state.selectedMode = "Ciclos"
            onShowDialog()
        }
    )
}

@Composable
fun ModeChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor by animateColorAsState(
        when {
            isPressed -> ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.2f)
            isSelected -> ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.15f)
            else -> Color.Transparent
        },
        animationSpec = tween(200)
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) ElegantOrangeTheme.PrimaryOrange
                else ElegantOrangeTheme.TextSecondary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) ElegantOrangeTheme.PrimaryOrange
            else ElegantOrangeTheme.TextSecondary,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            letterSpacing = 0.5.sp
        )
    }
}
