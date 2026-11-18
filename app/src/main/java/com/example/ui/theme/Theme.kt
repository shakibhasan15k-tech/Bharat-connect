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

private val LightColorScheme =
  lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldLight,
    onPrimaryContainer = EmeraldPrimary,
    secondary = EmeraldSecondary,
    onSecondary = Color.White,
    tertiary = GoldAccent,
    background = Color(0xFFF8F9FA),
    surface = Color(0xFFFFFFFF),
    onBackground = TextDark,
    onSurface = TextDark,
  )

private val DarkColorScheme =
  darkColorScheme(
    primary = EmeraldSecondary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF00331A),
    onPrimaryContainer = Color(0xFFC8E6C9),
    secondary = GoldAccent,
    onSecondary = Color.Black,
    tertiary = GoldAccent,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = TextLight,
    onSurface = TextLight,
  )

private val AmoledColorScheme =
  darkColorScheme(
    primary = EmeraldSecondary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF002211),
    onPrimaryContainer = Color(0xFFC8E6C9),
    secondary = GoldAccent,
    onSecondary = Color.Black,
    tertiary = GoldAccent,
    background = AmoledBlack,
    surface = AmoledSurface,
    onBackground = Color.White,
    onSurface = Color.White,
  )

@Composable
fun BharatConnectTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  amoledMode: Boolean = false,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      amoledMode -> AmoledColorScheme
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  BharatConnectTheme(darkTheme = darkTheme, amoledMode = false, dynamicColor = dynamicColor, content = content)
}
