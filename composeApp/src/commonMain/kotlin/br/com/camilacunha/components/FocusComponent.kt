package br.com.camilacunha.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.camilacunha.FocusOptions
import br.com.camilacunha.theme.ElegantOrangeTheme

@Composable
fun FocusOptionsList(
    onOptionSelected: () -> Unit,
    focusList: List<FocusOptions>
) {
    var selectedMode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        focusList.forEach { option ->
            FocusOptionItem(
                option = option,
                isSelected = selectedMode == option.type,
                onSelected = {
                    selectedMode = option.type
                    onOptionSelected.invoke()
                }
            )
        }
    }
}

@Composable
private fun FocusOptionItem(
    option: FocusOptions,
    onSelected: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) ElegantOrangeTheme.PrimaryOrange.copy(alpha = 0.1f)
        else Color.Transparent
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = option.icon,
            contentDescription = option.type,
            tint = if (isSelected) ElegantOrangeTheme.PrimaryOrange
            else ElegantOrangeTheme.TextSecondary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = option.type,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            color = if (isSelected) ElegantOrangeTheme.PrimaryOrange
            else ElegantOrangeTheme.TextPrimary
        )
    }
}