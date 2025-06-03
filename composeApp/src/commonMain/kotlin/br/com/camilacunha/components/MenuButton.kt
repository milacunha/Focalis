package br.com.camilacunha.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun MenuButton(text: String, icon: ImageVector, click: () -> Unit) {
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