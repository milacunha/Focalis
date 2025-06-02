package br.com.camilacunha.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun AlertDialogSelector(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    title: String,
    text: @Composable() (() -> Unit)
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                title,
                style = MaterialTheme.typography.h6.copy(
                    letterSpacing = 1.sp,
                    color = ElegantOrangeTheme.PrimaryOrange
                )
            )
        },
        text = text,
        confirmButton = {
            TextButton(
                onClick = confirmButton,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = ElegantOrangeTheme.PrimaryOrange
                )
            ) {
                Text("CONFIRMAR")
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismissButton,
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