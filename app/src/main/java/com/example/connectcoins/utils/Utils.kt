package com.example.connectcoins.utils

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.ui.gameBoardHorizontalPadding
import com.example.connectcoins.ui.gameBoardInnerColumnPadding

object Utils {

    val COIN_COLORS = listOf(
        Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White, Color.Red, Color.Green,
        Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta,
    )

    private val BACKGROUNDS = listOf(
        R.drawable.wallhaven_j5px5q,
        R.drawable.wallhaven_0p9xep,
        R.drawable.wallhaven_4ojpwl,
    )

    private var backgroundIndex:Int = 1

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