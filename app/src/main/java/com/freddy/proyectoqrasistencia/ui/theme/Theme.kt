package com.freddy.proyectoqrasistencia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Definir colores aesthetic azul y blanco
val PrimaryBlue = Color(0xFF0D47A1) // Azul oscuro
val PrimaryBlueLight = Color(0xFF42A5F5) // Azul claro
val PrimaryBlueDark = Color(0xFF1565C0) // Azul más oscuro
val PrimaryBlueSoft = Color(0xFFBBDEFB) // Azul pastel aesthetic
val PrimaryWhite = Color(0xFFFFFFFF) // Blanco puro

// 🖌 Esquema de colores oscuros con azul aesthetic
private val AppColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = PrimaryBlueLight,
    tertiary = PrimaryBlueSoft
)

@Composable
fun LoginAppUiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        content = content
    )
}
