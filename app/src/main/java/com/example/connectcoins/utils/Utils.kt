package com.example.connectcoins.utils

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.ui.screens.gameBoardHorizontalPadding
import com.example.connectcoins.ui.screens.gameBoardInnerColumnPadding


object Utils {

    val MAX_PLAYER_NAME_LENGTH = 30
    val GAME_BOARD_DEFAULT_SIZE = 4
    val DEFAULT_POINTS_TO_WIN = 3
    val MIN_GAME_BOARD_SIZE = 3
    val MAX_GAME_BOARD_SIZE = 10
    val GAME_BOARD_SIZE_RANGE = MIN_GAME_BOARD_SIZE .. MAX_GAME_BOARD_SIZE
    val GAME_BOARD_BACKGROUND_COLOR = listOf(Color.Transparent, Color.DarkGray)

    val COIN_BRUSH_COLORS = listOf(
        listOf(Color(0xFF0019FF), Color(0xFF000000)),
        listOf(Color(0xFFDA00FF), Color(0xFF000000)),
        listOf(Color(0xFFFD0000), Color(0xFF000000)),
        listOf(Color(0xFFFF9800), Color(0xFF000000)),
        listOf(Color(0xFF00FF0A), Color(0xFF000000)),

        listOf(Color(0xFF0019FF), Color(0xFF777777)),
        listOf(Color(0xFFDA00FF), Color(0xFF777777)),
        listOf(Color(0xFFFD0000), Color(0xFF777777)),
        listOf(Color(0xFFFF9800), Color(0xFF777777)),
        listOf(Color(0xFF00FF0A), Color(0xFF777777)),

        listOf(Color(0xFF0019FF), Color(0xFFFD0000)),
        listOf(Color(0xFFDA00FF), Color(0xFFFD0000)),
        listOf(Color(0xFFFF9800), Color(0xFFFD0000)),
        listOf(Color(0xFF00FF0A), Color(0xFFFD0000)),

        listOf(Color(0xFF0019FF), Color(0xFFDA00FF)),
        listOf(Color(0xFFFD0000), Color(0xFFDA00FF)),
        listOf(Color(0xFFFF9800), Color(0xFFDA00FF)),
        listOf(Color(0xFF00FF0A), Color(0xFFDA00FF)),
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
        R.drawable.wallhaven_76le39,
        R.drawable.wallhaven_48572j,
        R.drawable.wallhaven_nmx3w1,
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