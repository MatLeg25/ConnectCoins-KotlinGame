package com.example.connectcoins.ui

import androidx.compose.ui.graphics.Color
import com.example.connectcoins.data.models.Player

/**
 * Data class that represents the game UI state
 */
data class GameUiState(
    val currentPlayer: Player,
    val moves: Int = 0,
    val isGameOver: Boolean = false,
    val winner: Player? = null
)

data class GameSettingsState(
    val players: List<Player>,
    val gameBoardSize: Int,
    val pointsToWin: Int,
    val gameBoardBackgroundColor: List<Color>,
)
