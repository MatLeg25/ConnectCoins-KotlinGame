package com.example.connectcoins.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(
    private val players: List<Player> =
        listOf(
            Player("Player1", Color.Red), Player("Player2", Color.Blue)
        )
): ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState(players[0]))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    fun resetGame() {
        _uiState.value = GameUiState(players[0])
    }


    fun nextPlayer() {
        _uiState.update {  currentState ->
            val currentMoves = currentState.moves+1
            val currentPlayerIdx = (currentMoves % players.size)
            currentState.copy(
                moves = currentMoves,
                currentPlayerIdx = currentPlayerIdx,
                currentPlayer = players[currentPlayerIdx]
            )
        }
        uiState.value.printInfo()
    }

}


class Cell(
    val index: Int,
    var text: String,
    var state: Color = Color.Green
)

class Player(
    val name: String,
    var color: Color
)

