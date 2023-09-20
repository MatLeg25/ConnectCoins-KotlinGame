package com.example.connectcoins.utils

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connectcoins.ui.gameBoardHorizontalPadding
import com.example.connectcoins.ui.gameBoardInnerColumnPadding

object Utils {

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
}