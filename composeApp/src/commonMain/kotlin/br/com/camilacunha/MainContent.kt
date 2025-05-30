package br.com.camilacunha

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import br.com.camilacunha.theme.ElegantOrangeTheme
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.icon_remove

@Composable
fun MainContent(
    timeInScreen: String,
    controlButton: () -> Unit,
    settingsButton: () -> Unit,
    historyButton: () -> Unit,
    focusButton: () -> Unit,
    cycleButton: () -> Unit,
    controlText: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ElegantOrangeTheme.BackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextIconButton("Ajustes", Icons.Default.Settings, settingsButton)
                TextIconButton("Histórico", Icons.Default.Home, historyButton)
            }

            Spacer(modifier = Modifier.height(32.dp))

            ElegantModeSelector(
                focusButton, cycleButton
            )

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                contentAlignment = Alignment.Center,
                //modifier = Modifier.size(280.dp)
            ) {
                // Imagem decorativa de fundo
                /*
                Image(
                    painter = painterResource(resource = Res.drawable.IMG_0292), // Use seu próprio recurso aqui
                    contentDescription = "Decoração circular",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.width(500.dp)
                )
                 */

                Text(
                    text = timeInScreen,
                    style = ElegantOrangeTheme.TimerTypography.copy(
                        shadow = Shadow(
                            color = ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.1f),
                            offset = Offset(0f, 8f),
                            blurRadius = 12f
                        )
                    ),
                    color = ElegantOrangeTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ElegantButton(controlButton = controlButton, controlText = controlText)
        }
    }
}

@Composable
fun ElegantButton(
    controlButton: () -> Unit,
    controlText: String
) {
    Button(
        onClick = { controlButton.invoke() },
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(12.dp),
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 2.dp,
            brush = ElegantOrangeTheme.ButtonGradient
        )
    )
    {
        Box(
            modifier = Modifier
                .background(ElegantOrangeTheme.ButtonGradient)
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = controlText.uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.button,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun ElegantModeChip(
    title: String,
    clickButton: () -> Unit,
    isSelected: Boolean
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
                onClick = clickButton
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

@Composable
fun ElegantModeSelector(focusButton: () -> Unit, cycleButton: () -> Unit) {
    var selectedMode by remember { mutableStateOf("") }
    var showFocusDialog by remember { mutableStateOf(false) }
    var showCyclesDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ElegantModeChip(
            title = "Foco",
            isSelected = selectedMode == "Foco",
            clickButton = {
                selectedMode = "Foco"
                focusButton.invoke()
                showFocusDialog = true
            }
        )
        ElegantModeChip(
            title = "Ciclos",
            isSelected = selectedMode == "Ciclos",
            clickButton = {
                selectedMode = "Ciclos"
                cycleButton.invoke()
                showCyclesDialog = true
            }
        )
    }

    if (showFocusDialog) {
        AlertDialog(
            onDismissRequest = { showFocusDialog = false },
            title = {
                Text(
                    "FOCO",
                    style = MaterialTheme.typography.h6.copy(
                        letterSpacing = 1.sp,
                        color = ElegantOrangeTheme.PrimaryOrange
                    )
                )
            },
            text = {
                Column {
                    Text(
                        "Configure sua sessão:",
                        style = MaterialTheme.typography.body2,
                        color = ElegantOrangeTheme.TextSecondary
                    )

                    Spacer(Modifier.height(16.dp))

                    VintageTimePicker(
                        initialCycles = 25,
                        onTimeSelected = { mins -> /* Atualiza timer */ }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showFocusDialog = false
                        selectedMode = "Foco"
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = ElegantOrangeTheme.PrimaryOrange
                    )
                ) {
                    Text("CONFIRMAR")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showFocusDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = ElegantOrangeTheme.TextSecondary
                    )
                ) {
                    Text("CANCELAR")
                }
            },
            shape = RoundedCornerShape(12.dp),
            backgroundColor = ElegantOrangeTheme.BackgroundGradientStart,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = ElegantOrangeTheme.BackgroundGradientStart,
                    shape = RoundedCornerShape(12.dp)
                )
        )
    }

    if (showCyclesDialog) {
        AlertDialog(
            onDismissRequest = { showCyclesDialog = false },
            title = {
                Text(
                    "CICLOS",
                    style = MaterialTheme.typography.h6.copy(
                        letterSpacing = 1.sp,
                        color = ElegantOrangeTheme.PrimaryOrange
                    )
                )
            },
            text = {
                Column {
                    Text(
                        "Configure sua sessão:",
                        style = MaterialTheme.typography.body2,
                        color = ElegantOrangeTheme.TextSecondary
                    )

                    Spacer(Modifier.height(16.dp))

                    VintageTimePicker(
                        initialCycles = 3,
                        onTimeSelected = { cycles -> /* Atualiza timer */ }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCyclesDialog = false
                        selectedMode = "Ciclos"
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = ElegantOrangeTheme.PrimaryOrange
                    )
                ) {
                    Text("CONFIRMAR")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCyclesDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = ElegantOrangeTheme.TextSecondary
                    )
                ) {
                    Text("CANCELAR")
                }
            },
            shape = RoundedCornerShape(12.dp),
            backgroundColor = ElegantOrangeTheme.BackgroundGradientStart,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = ElegantOrangeTheme.BackgroundGradientStart,
                    shape = RoundedCornerShape(12.dp)
                )
        )
    }
}

@Composable
fun VintageTimePicker(
    initialCycles: Int,
    onTimeSelected: (Int) -> Unit
) {
    var cycles by remember { mutableStateOf(initialCycles) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        IconButton(
            onClick = { if (cycles > 2) cycles-- },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.icon_remove),
                contentDescription = "Reduzir",
                tint = ElegantOrangeTheme.PrimaryOrange
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(80.dp)
                .height(48.dp)
                .background(
                    color = ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = "$cycles",
                style = MaterialTheme.typography.h6.copy(
                    color = ElegantOrangeTheme.PrimaryOrange
                )
            )
        }

        IconButton(
            onClick = { if (cycles < 6) cycles++ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar",
                tint = ElegantOrangeTheme.PrimaryOrange
            )
        }
    }
}

@Composable
fun TextIconButton(text: String, icon: ImageVector, click: () -> Unit) {
    TextButton(
        onClick = { click.invoke() },
        colors = ButtonDefaults.textButtonColors(
            contentColor = ElegantOrangeTheme.TextSecondary
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text,
                style = MaterialTheme.typography.caption,
                letterSpacing = 0.8.sp
            )
        }
    }
}