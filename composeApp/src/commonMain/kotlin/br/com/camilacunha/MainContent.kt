package br.com.camilacunha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import br.com.camilacunha.components.ControlButton
import br.com.camilacunha.components.MenuButton
import br.com.camilacunha.components.ModeSelector
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun MainContent(
    menuOptions: List<MenuOptions>,
    timeInScreen: String,
    controlText: String,
    controlClick: () -> Unit,
    focusOptions: List<FocusOptions>
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
                menuOptions.forEach { item ->
                    MenuButton(text = item.label, icon = item.icon, click = item.click)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            ModeSelector(focusOptions)

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                contentAlignment = Alignment.Center
            ) {
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

            ControlButton(controlText, controlClick)
        }
    }
}