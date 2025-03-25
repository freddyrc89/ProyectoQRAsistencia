package com.freddy.proyectoqrasistencia.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Esquema de colores para el modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = SenatiBluePrimary,
    secondary = SenatiBlueSecondary,
    tertiary = SenatiYellow,
    background = SenatiBlueDark,
    surface = SenatiBlueDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

// Esquema de colores para el modo claro
private val LightColorScheme = lightColorScheme(
    primary = SenatiBluePrimary,
    secondary = SenatiBlueSecondary,
    tertiary = SenatiYellow,
    background = SenatiGrayLight,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ProyectoQRAsistenciaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S, // Solo usar en Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asegúrate de definir Typography en otro archivo
        content = content
    )
}
