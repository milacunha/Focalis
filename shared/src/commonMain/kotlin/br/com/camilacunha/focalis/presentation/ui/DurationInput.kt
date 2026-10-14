package br.com.camilacunha.focalis.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.duration_label
import org.jetbrains.compose.resources.stringResource

/**
 * Campo de duração em minutos. Só fica habilitado quando canEdit=true.
 * Valor vazio ou inválido é tratado pelo pai; aqui só reportamos o texto.
 */
@Composable
internal fun DurationInput(
    minutesText: String,
    canEdit: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = minutesText,
            onValueChange = { raw ->
                val digits = raw.filter { it.isDigit() }.take(2)
                onValueChange(digits)
            },
            enabled = canEdit,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = stringResource(Res.string.duration_label),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(120.dp),
        )
    }
}