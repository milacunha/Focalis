package br.com.camilacunha.focalis.presentation.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TextButton(
            onClick = {
                runCatching { Desktop.getDesktop().browse(URI(externalUrl)) }
            },
        ) {
            Text("Abrir no navegador")
        }
    }
}