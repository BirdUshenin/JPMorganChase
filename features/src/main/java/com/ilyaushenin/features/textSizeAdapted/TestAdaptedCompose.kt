package com.ilyaushenin.features.textSizeAdapted

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TestAdapted(text: String) {
    BasicText(
        text = text,
        style = TextStyle(
            color = Color.White
        ),
        autoSize = TextAutoSize.StepBased(
            minFontSize = 10.sp,
            maxFontSize = 60.sp,
            stepSize = 10.sp
        )
    )
}

@Preview
@Composable
fun TestAdaptedPreview() {
    TestAdapted(text = "Сбдбдыф бдсыф")
}