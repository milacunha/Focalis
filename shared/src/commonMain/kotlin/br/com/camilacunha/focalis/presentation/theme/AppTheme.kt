package br.com.camilacunha.focalis.presentation.theme

import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.bg_amber
import br.com.camilacunha.focalis.resources.bg_blue
import br.com.camilacunha.focalis.resources.bg_green
import br.com.camilacunha.focalis.resources.bg_terracotta
import org.jetbrains.compose.resources.DrawableResource

enum class AppTheme(val label: String) {
    LightTerracotta("Terracota"),
    LightGreen("Musgo"),
    DarkBlue("Cachoeira"),
    DarkAmber("Fogueira"),
}

internal val AppTheme.backgroundImage: DrawableResource
    get() = when (this) {
        AppTheme.LightTerracotta -> Res.drawable.bg_terracotta
        AppTheme.LightGreen -> Res.drawable.bg_green
        AppTheme.DarkBlue -> Res.drawable.bg_blue
        AppTheme.DarkAmber -> Res.drawable.bg_amber
    }