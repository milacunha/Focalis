package br.com.camilacunha.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun ControlButton(
    controlText: String,
    controlClick: () -> Unit
) {
    Button(
        onClick = {
            controlClick.invoke()
        },
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