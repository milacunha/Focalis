package br.com.camilacunha.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object ElegantOrangeTheme {
    // Paleta principal
    val PrimaryOrange = Color(0xFFE65100) // Laranja terroso
    val SecondaryOrange = Color(0xFFF57C00) // Laranja suavizado
    val LightOrange = Color(0xFFFFA726) // Toques dourados

    val TextPrimary = Color(0xFF263238)  // Quase preto com tom mineral
    val TextSecondary = Color(0xFF607D8B)  // Cinza azulado

    val BackgroundGradientStart = Color(0xFFFFF8F2)
    val BackgroundGradientEnd = Color(0xFFFFE0B2)
    val BackgroundGradient = Brush.verticalGradient(
        colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
    )

    val ButtonGradient = Brush.horizontalGradient(
        colors = listOf(SecondaryOrange, PrimaryOrange)
    )

    val TimerTypography = TextStyle(
        //fontFamily = FontFamily(
        //    //Font(R.font.montserrat_semibold)
        //),
        fontWeight = FontWeight.SemiBold,
        fontSize = 60.sp,
        letterSpacing = 0.5.sp
    )
}