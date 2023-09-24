package com.example.connectcoins.ui.state

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

