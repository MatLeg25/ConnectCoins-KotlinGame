package com.example.connectcoins.ui

import android.util.Log
import com.example.connectcoins.data.Player

/**
 * Data class that represents the game UI state
 */
data class GameUiState(
    val currentPlayer: Player,
    val moves: Int = 0,
    val isGameOver: Boolean = false,
    val winner: Player? = null
) {
    fun printInfo() {
        Log.w("elox","------------------------------------------------------------------------")
        Log.w("elox",">>>>>>>>>>>>>>>>>>>>>>>>> ${currentPlayer.name} : ${currentPlayer.name}")
        Log.w("elox",">>>>>>>>>>>>>>>>>>>>>>>>> $moves")
        Log.w("elox",">>>>>>>>>>>>>>>>>>>>>>>>> $isGameOver : ${winner?.name}")
        Log.w("elox","------------------------------------------------------------------------")
    }
}
