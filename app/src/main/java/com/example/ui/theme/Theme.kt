package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val CalisthenixDarkColorScheme = darkColorScheme(
  primary = AmberGold,
  onPrimary = Color(0xFF1A1200),
  primaryContainer = Color(0xFF452B00),
  onPrimaryContainer = AmberGoldLight,
  secondary = CyanAccent,
  onSecondary = Color(0xFF003549),
  secondaryContainer = Color(0xFF004D68),
  onSecondaryContainer = Color(0xFFBEE9FF),
  tertiary = CoralFlame,
  onTertiary = Color.White,
  background = DarkBackground,
  onBackground = TextPrimary,
  surface = DarkSurface,
  onSurface = TextPrimary,
  surfaceVariant = DarkSurfaceElevated,
  onSurfaceVariant = TextSecondary,
  outline = DarkOutline,
)

private val CalisthenixLightColorScheme = lightColorScheme(
  primary = AmberGoldDark,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFFEF3C7),
  onPrimaryContainer = Color(0xFF78350F),
  secondary = Color(0xFF0284C7),
  onSecondary = Color.White,
  tertiary = CoralFlame,
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightSurface,
  onSurface = LightTextPrimary,
  surfaceVariant = LightSurfaceElevated,
  onSurfaceVariant = LightTextSecondary,
  outline = LightOutline,
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Calisthenix defaults to high-energy dark athletic mode
  dynamicColor: Boolean = false, // Keep tailored brand athletic colors consistent
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) CalisthenixDarkColorScheme else CalisthenixLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
