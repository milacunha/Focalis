package br.com.camilacunha.focalis.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.camilacunha.focalis.resources.Res
import br.com.camilacunha.focalis.resources.nunito_variable
import br.com.camilacunha.focalis.resources.sono_variable
import org.jetbrains.compose.resources.Font

@Composable
internal fun focalisTypography(): Typography {

    /* Fonte para números */
    val sono = FontFamily(
        Font(Res.font.sono_variable, FontWeight.Light),
        Font(Res.font.sono_variable, FontWeight.Normal),
        Font(Res.font.sono_variable, FontWeight.Medium),
    )

    /*Fonte para texto*/
    val nunito = FontFamily(
        Font(Res.font.nunito_variable, FontWeight.Normal),
        Font(Res.font.nunito_variable, FontWeight.Medium),
        Font(Res.font.nunito_variable, FontWeight.SemiBold),
    )

    return Typography(

        // ---------- Sono ----------
        displayLarge = TextStyle(
            fontFamily = sono,
            fontWeight = FontWeight.Light,
            fontSize = 96.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = sono,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = sono,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.sp,
        ),

        // ---------- Nunito ----------
        headlineSmall = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = nunito,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        ),
    )
}