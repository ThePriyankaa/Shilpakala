package com.shilpakala.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ShilpaKalaColorScheme = lightColorScheme(
    primary            = MediumBrown,
    onPrimary          = IvoryWhite,
    primaryContainer   = ContainerGold,
    onPrimaryContainer = DarkBrown,

    secondary          = LuxuryGold,
    onSecondary        = DarkBrown,
    secondaryContainer = ContainerGold,
    onSecondaryContainer = DarkBrown,

    tertiary           = TerracottaAccent,
    onTertiary         = IvoryWhite,

    background         = BackgroundBeige,
    onBackground       = TextPrimary,

    surface            = SurfaceLight,
    onSurface          = TextPrimary,
    surfaceVariant     = ContainerBeige,
    onSurfaceVariant   = TextSecondary,

    outline            = DividerColor,
    outlineVariant     = GoldBorder,

    error              = TerracottaAccent,
    onError            = IvoryWhite,
)

@Composable
fun ShilpaKalaTheme(content: @Composable () -> Unit) {
    val colorScheme = ShilpaKalaColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BackgroundBeige.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = ShilpaKalaTypography,
        content     = content
    )
}
