package com.denchic45.kiro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun AppTheme(
    typography: Typography,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
//    val colors = if (!useDarkTheme) {
//        LightColors
//    } else {
//        DarkColors
//    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
//            colorScheme = colors,
            typography = typography, content = content
        )
    }
}

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }