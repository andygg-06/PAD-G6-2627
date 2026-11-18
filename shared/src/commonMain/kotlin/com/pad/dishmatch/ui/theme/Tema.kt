package com.pad.dishmatch.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Colores de la marca: turquesa y coral del logotipo.
// Los tonos oscuros son para botones y textos (contraste suficiente),
// los claros para fondos y acentos.

private val esquemaClaro = lightColorScheme(
    primary = Color(0xFF0F7A74),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB2F1EB),
    onPrimaryContainer = Color(0xFF00201E),
    inversePrimary = Color(0xFF5BD3CB),

    secondary = Color(0xFFC03A2B),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD4),
    onSecondaryContainer = Color(0xFF410002),

    tertiary = Color(0xFF4A6360),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFCCE8E4),
    onTertiaryContainer = Color(0xFF05201D),

    background = Color(0xFFFBF8F7),
    onBackground = Color(0xFF1C2B29),

    // Las tarjetas usan estos tonos: si no se definen, Material deja el lila por defecto
    surface = Color(0xFFFDFBFA),
    onSurface = Color(0xFF1C2B29),
    surfaceDim = Color(0xFFDEDAD8),
    surfaceBright = Color(0xFFFDFBFA),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8F4F2),
    surfaceContainer = Color(0xFFF2EEEC),
    surfaceContainerHigh = Color(0xFFECE8E5),
    surfaceContainerHighest = Color(0xFFE6E2DF),
    surfaceVariant = Color(0xFFE8E4E1),
    onSurfaceVariant = Color(0xFF4C4846),
    surfaceTint = Color(0xFF0F7A74),

    inverseSurface = Color(0xFF31302F),
    inverseOnSurface = Color(0xFFF4F0EE),

    outline = Color(0xFFBFB8B4),
    outlineVariant = Color(0xFFE0DAD6),
    scrim = Color(0xFF000000),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

private val esquemaOscuro = darkColorScheme(
    primary = Color(0xFF5BD3CB),
    onPrimary = Color(0xFF003734),
    primaryContainer = Color(0xFF00504C),
    onPrimaryContainer = Color(0xFFB2F1EB),
    inversePrimary = Color(0xFF0F7A74),

    secondary = Color(0xFFFF8E7E),
    onSecondary = Color(0xFF5F1300),
    secondaryContainer = Color(0xFF8A2316),
    onSecondaryContainer = Color(0xFFFFDAD4),

    tertiary = Color(0xFFB0CCC8),
    onTertiary = Color(0xFF1C3532),
    tertiaryContainer = Color(0xFF334B48),
    onTertiaryContainer = Color(0xFFCCE8E4),

    background = Color(0xFF101514),
    onBackground = Color(0xFFE2E4E3),

    surface = Color(0xFF121716),
    onSurface = Color(0xFFE2E4E3),
    surfaceDim = Color(0xFF121716),
    surfaceBright = Color(0xFF383D3C),
    surfaceContainerLowest = Color(0xFF0D1211),
    surfaceContainerLow = Color(0xFF1A1F1E),
    surfaceContainer = Color(0xFF1E2322),
    surfaceContainerHigh = Color(0xFF282E2D),
    surfaceContainerHighest = Color(0xFF333938),
    surfaceVariant = Color(0xFF3F4847),
    onSurfaceVariant = Color(0xFFBEC9C7),
    surfaceTint = Color(0xFF5BD3CB),

    inverseSurface = Color(0xFFE2E4E3),
    inverseOnSurface = Color(0xFF2F3130),

    outline = Color(0xFF889391),
    outlineVariant = Color(0xFF3F4847),
    scrim = Color(0xFF000000),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

// Esquinas mas redondeadas que las de Material por defecto,
// como en los bocetos del hito 1
private val formas = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun TemaDishMatch(oscuro: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (oscuro) esquemaOscuro else esquemaClaro,
        shapes = formas,
        content = content
    )
}
