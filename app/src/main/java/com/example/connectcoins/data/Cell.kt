package com.example.connectcoins.data

import androidx.compose.ui.graphics.Color
import java.util.UUID

class Cell(
    val index: Int,
    val text: String,
    var playerId: String? = null
)

class Player(
    val name: String,
    val color: Color
) {
    val id: String = UUID.randomUUID().toString()
}
