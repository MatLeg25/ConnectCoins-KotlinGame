package com.example.connectcoins.utils

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectcoins.R
import com.example.connectcoins.ui.gameBoardHorizontalPadding
import com.example.connectcoins.ui.gameBoardInnerColumnPadding


object Utils {

    val COIN_COLORS = listOf(
        Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White, Color.Red, Color.Green,
        Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta,
    )

    val COIN_BRUSH_COLORS = listOf(
        listOf(Color(0xFFFF8403), Color(0xFFFF5722)),
        listOf(Color(0xFF03A9F4), Color(0xFF0629EC)),
        listOf(Color(0xFF9C27B0), Color(0xFFFF0051)),

        listOf(Color.Blue, Color.Black),
        listOf(Color.Gray, Color.DarkGray),
        listOf(Color.White, Color.LightGray),
        listOf(Color.Red, Color.Magenta),

        listOf(Color.Yellow, Color.Green),
        listOf(Color.Black, Color.Blue),
        listOf(Color.DarkGray, Color.Gray),
        listOf(Color.LightGray, Color.Black),
        listOf(Color.Magenta, Color.Red),
    )

    private val BACKGROUNDS = listOf(
        R.drawable.wallhaven_4ojpwl,
        R.drawable.wallhaven_nzp29v,
        R.drawable.wallhaven_w8ejlx,
        R.drawable.wallhaven_4gjd8e,
        R.drawable.wallhaven_4xplev,
        R.drawable.wallhaven_n6q7v7,
        R.drawable.wallhaven_4xepql,
        R.drawable.wallhaven_42d8dy,
        R.drawable.wallhaven_45gvl5,
        R.drawable.wallhaven_49vj7x,
        R.drawable.wallhaven_4gvv9q,
        R.drawable.wallhaven_6oo7mx,
        R.drawable.wallhaven_76le39,
        R.drawable.wallhaven_48572j,
        R.drawable.wallhaven_nmx3w1,
        R.drawable.wallhaven_nz5o7y,
        R.drawable.wallhaven_nze32y,
        R.drawable.wallhaven_qddqel,
        R.drawable.wallhaven_x1jgxv,
    )

    private var backgroundIndex: Int = BACKGROUNDS.indices.random()

    private fun getMinScreenDimension(configuration: Configuration): Dp {
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        return if (screenHeight > screenWidth) screenWidth else screenHeight
    }

    fun calculateCellSize(configuration: Configuration, gameBoardSize: Int): Dp {
        val screenSize = getMinScreenDimension(configuration)
        val paddingSize = (((gameBoardSize - 1) * gameBoardInnerColumnPadding) + gameBoardHorizontalPadding).dp
        return (screenSize / gameBoardSize) - paddingSize
    }

    @DrawableRes
    fun getNextBackground(): Int {
        val nextBackgroundIndex = backgroundIndex+1
        backgroundIndex = nextBackgroundIndex.takeIf { it in BACKGROUNDS.indices } ?: 0
        return BACKGROUNDS[backgroundIndex]
    }

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
}