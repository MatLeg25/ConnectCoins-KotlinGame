package com.example.connectcoins.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.connectcoins.data.Cell
import com.example.connectcoins.data.DATA
import com.example.connectcoins.data.Player
import com.example.connectcoins.utils.Validator

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

    private val validator = Validator()

    var data: Array<Array<Cell>>
    private val totalMoves: Int

    val SIZE = 3
    val RANGE = 0 until SIZE

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState(players[0]))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    init {
        data = generateGameBoard()
        totalMoves = data.size * data[0].size
    }


    fun resetGame() {
        _uiState.value = GameUiState(players[0])
    }




    fun getPlayer(playerId: String): Player? = players.firstOrNull { it.id == playerId }

    fun onColumnClick(columnIdx: Int, currentPlayerId: String) {
        val cell = data[columnIdx].findLast { it.playerId == null }
        cell?.let {
            it.playerId = currentPlayerId
            nextPlayer()
            isEndGame(currentPlayerId)
        }
    }

    private fun nextPlayer() {
        _uiState.update {  currentState ->
            val currentMoves = currentState.moves+1
            val currentPlayerIdx = (currentMoves % players.size)
            currentState.copy(
                moves = currentMoves,
                currentPlayer = players[currentPlayerIdx]
            )
        }
        uiState.value.printInfo()
    }

    private fun isEndGame(currentPlayerId: String) {
        val isWinner = validator.checkWin(currentPlayerId)
        if (isWinner) _uiState.update {  currentState ->
            currentState.copy(
                isGameOver = true,
                winner = getPlayer(currentPlayerId)
            )
        } else if (_uiState.value.moves == totalMoves) _uiState.update { currentState ->
            currentState.copy(
                isGameOver = true,
                winner = null
            )
        }
    }

    private fun generateGameBoard(): Array<Array<Cell>> = RANGE.map {
        RANGE.map {
            Cell(it, it.toString())
        }.toTypedArray()
    }.toTypedArray()

}
