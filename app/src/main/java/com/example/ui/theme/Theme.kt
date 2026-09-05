package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
  primary = Primary,
  onPrimary = OnPrimary,
  primaryContainer = PrimaryContainer,
  onPrimaryContainer = OnPrimaryContainer,
  inversePrimary = InversePrimary,
  secondary = Secondary,
  onSecondary = OnSecondary,
  secondaryContainer = SecondaryContainer,
  onSecondaryContainer = OnSecondaryContainer,
  tertiary = Tertiary,
  onTertiary = OnTertiary,
  tertiaryContainer = TertiaryContainer,
  onTertiaryContainer = OnTertiaryContainer,
  background = Surface,
  onBackground = OnSurface,
  surface = Surface,
  onSurface = OnSurface,
  surfaceVariant = SurfaceVariant,
  onSurfaceVariant = OnSurfaceVariant,
  surfaceContainerLowest = SurfaceContainerLowest,
  surfaceContainerLow = SurfaceContainerLow,
  surfaceContainer = SurfaceContainer,
  surfaceContainerHigh = SurfaceContainerHigh,
  surfaceContainerHighest = SurfaceContainerHighest,
  inverseSurface = InverseSurface,
  inverseOnSurface = InverseOnSurface,
  outline = Outline,
  outlineVariant = OutlineVariant,
  error = Error,
  onError = OnError,
  errorContainer = ErrorContainer,
  onErrorContainer = OnErrorContainer
)

private val DarkColorScheme = darkColorScheme(
  primary = PrimaryFixedDim,
  onPrimary = OnPrimaryFixed,
  primaryContainer = PrimaryContainer,
  onPrimaryContainer = OnPrimaryContainer,
  secondary = SecondaryFixedDim,
  onSecondary = OnSecondaryFixed,
  secondaryContainer = SecondaryContainer,
  onSecondaryContainer = OnSecondaryContainer,
  tertiary = TertiaryFixedDim,
  onTertiary = OnTertiaryFixed,
  tertiaryContainer = TertiaryContainer,
  onTertiaryContainer = OnTertiaryContainer,
  background = InverseSurface,
  onBackground = InverseOnSurface,
  surface = InverseSurface,
  onSurface = InverseOnSurface,
  outline = Outline,
  outlineVariant = OutlineVariant
)

@Composable
fun XiuVocaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

// Keep alias for compatibility
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  XiuVocaTheme(darkTheme = darkTheme, content = content)
}

