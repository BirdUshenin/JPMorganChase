package com.ilyaushenin.jpmorganchase.core.extention

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.customBrush(
    width: Float,
    durationMillis: Int = 2000,
): Modifier = composed {

    val targetValue = width * 2
    val colorDark = Color(0xFFCDD8DC)
    val colorLightDark = Color(0xFF9EA2A9)

    val shimmerColors = listOf(
        colorDark,
        colorLightDark,
        colorDark
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 10f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    background(
        Brush.horizontalGradient(
            shimmerColors,
            endX = translateAnim.value
        )
    )
}