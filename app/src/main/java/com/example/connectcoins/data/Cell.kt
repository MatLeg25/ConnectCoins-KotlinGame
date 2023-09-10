package com.example.connectcoins.data

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class Cell(
    val index: Int,
    val text: String,
    val cords: Pair<Int, Int>,
    var playerId: String? = null,
    var isWin: Boolean = false
)

data class Player(
    var id: String = "",
    var name: String,
    var color: Color
) {
   init {
       if (id.isEmpty()) id = UUID.randomUUID().toString()
   }
}
