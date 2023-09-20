package com.example.connectcoins.utils

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.ui.gameBoardHorizontalPadding
import com.example.connectcoins.ui.gameBoardInnerColumnPadding
import kotlin.random.Random

object Utils {

    val COIN_COLORS = listOf(
        Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White, Color.Red, Color.Green,
        Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta,
    )

    private val BACKGROUNDS = listOf(
        R.drawable.wallhaven_j5px5q,
        R.drawable.wallhaven_4ojpwl,
        R.drawable.wallhaven_nzp29v,
        R.drawable.wallhaven_qdjrl7,
        R.drawable.wallhaven_w8ejlx,
        R.drawable.wallhaven_4gjd8e,
        R.drawable.wallhaven_4xplev,
        R.drawable.wallhaven_n6q7v7,
        R.drawable.wallhaven_4ddrwj,
        R.drawable.wallhaven_4l5wxy,
        R.drawable.wallhaven_4xepql,
        R.drawable.wallhaven_ym731x,
        R.drawable.wallhaven_2kp1wg,
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
}