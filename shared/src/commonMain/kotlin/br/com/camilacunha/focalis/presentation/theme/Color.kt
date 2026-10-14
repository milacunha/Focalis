package br.com.camilacunha.focalis.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Tema 1 — Claro · Terracota & Linho
private val LightTerracotta = lightColorScheme(
    background = Color(0xFFF5F0E8),
    onBackground = Color(0xFF3A322B),
    surface = Color(0xFFFDFAF4),
    onSurface = Color(0xFF3A322B),
    surfaceVariant = Color(0xFFEAE1D4),
    onSurfaceVariant = Color(0xFF6B5F54),
    primary = Color(0xFFB86B4F),
    onPrimary = Color(0xFFFFFFFF),
    outline = Color(0xFFC4B6A6),
    error = Color(0xFFA6463F),
    onError = Color(0xFFFFFFFF),
)

// Tema 2 — Claro · Verde Musgo & Névoa
private val LightGreen = lightColorScheme(
    background = Color(0xFFF0F2EC),
    onBackground = Color(0xFF2C3530),
    surface = Color(0xFFF7F9F3),
    onSurface = Color(0xFF2C3530),
    surfaceVariant = Color(0xFFE1E6DB),
    onSurfaceVariant = Color(0xFF56605A),
    primary = Color(0xFF4A6B52),
    onPrimary = Color(0xFFFFFFFF),
    outline = Color(0xFFB8C0B0),
    error = Color(0xFFA14B3F),
    onError = Color(0xFFFFFFFF),
)

// Tema 3 — Escuro · Azul Cachoeira & Meia-Noite
private val DarkBlue = darkColorScheme(
    background = Color(0xFF1A2028),
    onBackground = Color(0xFFD6DEE6),
    surface = Color(0xFF232B34),
    onSurface = Color(0xFFD6DEE6),
    surfaceVariant = Color(0xFF2E3844),
    onSurfaceVariant = Color(0xFF96A4B2),
    primary = Color(0xFF7EB6D4),
    onPrimary = Color(0xFF1A2028),
    outline = Color(0xFF3A4654),
    error = Color(0xFFE07A6B),
    onError = Color(0xFF1A2028),
)

// Tema 4 — Escuro · Cinza Vulcânico & Âmbar
private val DarkAmber = darkColorScheme(
    background = Color(0xFF1E1A16),
    onBackground = Color(0xFFE4DDD4),
    surface = Color(0xFF2A2620),
    onSurface = Color(0xFFE4DDD4),
    surfaceVariant = Color(0xFF3A342B),
    onSurfaceVariant = Color(0xFFA89C8E),
    primary = Color(0xFFD4A574),
    onPrimary = Color(0xFF1E1A16),
    outline = Color(0xFF4A4238),
    error = Color(0xFFD9705B),
    onError = Color(0xFF1E1A16),
)

internal fun AppTheme.colorScheme() = when (this) {
    AppTheme.LightTerracotta -> LightTerracotta
    AppTheme.LightGreen -> LightGreen
    AppTheme.DarkBlue -> DarkBlue
    AppTheme.DarkAmber -> DarkAmber
}