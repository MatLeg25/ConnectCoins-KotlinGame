package com.example.connectcoins.ui.state

import androidx.compose.ui.graphics.Color
import com.example.connectcoins.data.models.Player

data class GameSettingsState(
    val players: List<Player>,
    val gameBoardSize: Int,
    val pointsToWin: Int,
    val gameBoardBackgroundColor: List<Color>,
)
