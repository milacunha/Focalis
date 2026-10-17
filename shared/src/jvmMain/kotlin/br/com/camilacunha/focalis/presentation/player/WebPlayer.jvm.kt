package br.com.camilacunha.focalis.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.web_player_open
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.net.URI

/**
 * No Desktop, não embeddamos o player.
 * Mostra um botão pra abrir a URL no navegador padrão do sistema.
 */
@Composable
actual fun WebPlayer(
    modifier: Modifier,
    embedUrl: String,
    externalUrl: String,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        TextButton(
            onClick = {
                runCatching { Desktop.getDesktop().browse(URI(externalUrl)) }
            },
        ) {
            Text(
                text = stringResource(Res.string.web_player_open),
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}