package br.com.camilacunha.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.camilacunha.theme.ElegantOrangeTheme
import org.jetbrains.compose.resources.painterResource
import pomodoro.composeapp.generated.resources.Res
import pomodoro.composeapp.generated.resources.icon_remove

@Composable
fun CyclePicker(initialCycles: Int) {
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