package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AmberGold,
    onPrimary = ObsidianBlack,
    primaryContainer = AmberGoldContainer,
    onPrimaryContainer = AmberGoldLight,
    secondary = SandalwoodCream,
    onSecondary = ObsidianBlack,
    secondaryContainer = ObsidianCard,
    onSecondaryContainer = SandalwoodCream,
    tertiary = RoseOudLight,
    onTertiary = Color.White,
    tertiaryContainer = RoseOudContainer,
    onTertiaryContainer = RoseOudLight,
    background = ObsidianBlack,
    onBackground = SandalwoodCream,
    surface = ObsidianSurface,
    onSurface = SandalwoodCream,
    surfaceVariant = ObsidianCard,
    onSurfaceVariant = SandalwoodMuted,
    outline = ObsidianBorder
)

private val LightColorScheme = lightColorScheme(
    primary = AmberGoldDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBF1DE),
    onPrimaryContainer = AmberGoldDark,
    secondary = Color(0xFF382F26),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEFE8DC),
    onSecondaryContainer = Color(0xFF282018),
    tertiary = RoseOud,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFBECEB),
    onTertiaryContainer = RoseOud,
    background = Color(0xFFFAF7F2),
    onBackground = Color(0xFF1E1A16),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E1A16),
    surfaceVariant = Color(0xFFF4ECE1),
    onSurfaceVariant = Color(0xFF5D5449),
    outline = Color(0xFFD6CABD)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Luxury dark theme is default signature for Attar Atelier
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
