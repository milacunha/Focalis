package br.com.camilacunha.focalis.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.dialog_close
import br.com.camilacunha.focalis.resources.dialog_open_player
import br.com.camilacunha.focalis.resources.url_field_label
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UrlDialog(
    title: String,
    initialUrl: String,
    errorMessage: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Boolean,
) {
    var url by remember { mutableStateOf(initialUrl) }
    var hasError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                        hasError = false
                    },
                    label = {
                        Text(
                            text = stringResource(Res.string.url_field_label),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    },
                    singleLine = true,
                    isError = hasError,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    modifier = Modifier.fillMaxWidth(),
                )
                if (hasError) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (onConfirm(url)) onDismiss() else hasError = true
                },
            ) {
                Text(
                    text = stringResource(Res.string.dialog_open_player),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(Res.string.dialog_close),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
    )
}