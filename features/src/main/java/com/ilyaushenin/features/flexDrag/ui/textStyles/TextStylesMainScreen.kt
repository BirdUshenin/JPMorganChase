package com.ilyaushenin.features.flexDrag.ui.textStyles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun textStylesMainScreen() =
    if (isSystemInDarkTheme()) DarkThemeColors.textStyleUsually else LightThemeColors.textStyleUsually

private object DarkThemeColors {
    val textStyleUsually = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center
    )
}

private object LightThemeColors {
    val textStyleUsually = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center
    )
}