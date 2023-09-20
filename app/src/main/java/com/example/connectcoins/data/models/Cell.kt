package com.example.connectcoins.data.models

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class Cell(
    val index: Int,
    val text: String,
    val cords: Pair<Int, Int>,
    var playerId: String? = null,
    var isWin: Boolean = false
)
