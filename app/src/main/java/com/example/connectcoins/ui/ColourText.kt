package com.example.connectcoins.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ColourText(
    text: String,
    fontSize: TextUnit = 16.sp,
    ) {
    val gradientColors = listOf(Color.Red, Color.Magenta, Color.Blue, Color.Green, Color.Yellow)

    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    ),
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append(text)
            }
        },
        fontSize = fontSize,
    )

}