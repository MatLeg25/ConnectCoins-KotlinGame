package com.example.connectcoins.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.connectcoins.data.Cell
import com.example.connectcoins.data.Player
import com.example.connectcoins.utils.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(): ViewModel() {

    lateinit var gameboard: Array<Array<Cell>>
    private var totalMoves: Int = 0
    private lateinit var validator: Validator
    private var gameBoardSize = 3

    // Game UI state
    private val _settings = MutableStateFlow(
        GameSettingsState(
            getDefaultPlayers(),
            gameBoardSize
        )
    )
    val settings: StateFlow<GameSettingsState> = _settings.asStateFlow()

    val players = mutableStateListOf<Player>().apply {
        addAll(_settings.value.players.toList())
    }

    private val _uiState = MutableStateFlow(GameUiState(players.first()))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    init {
         resetGame()
    }

    private fun getDefaultPlayers(): List<Player> = listOf(
        Player(name = "Player1", color = Color.Red),
        Player(name = "Player2", color = Color.Blue)
    )


    fun resetGame() {
        Log.w("elox"," RESET GAME!!!")
        gameboard = generateGameBoard(gameBoardSize)
        totalMoves = gameboard.size * gameboard[0].size
        validator = Validator(gameboard)
        _uiState.update {
            it.copy(
                currentPlayer = _settings.value.players.first(),
                moves = 0,
                isGameOver = false,
                winner = null,
            )
        }
    }

    fun getPlayer(playerId: String): Player =_settings.value.players.firstOrNull { it.id == playerId }!!

    fun onColumnClick(columnIdx: Int, currentPlayerId: String) {
        //prevent ui changes if game is ended
        if (_uiState.value.isGameOver) return

        val cell = gameboard[columnIdx].findLast { it.playerId == null }
        cell?.let {
            it.playerId = currentPlayerId
            nextPlayer()
            isEndGame(currentPlayerId)
        }
    }

    private fun nextPlayer() {
        _uiState.update {  currentState ->
            val currentMoves = currentState.moves+1
            val currentPlayerIdx = (currentMoves % _settings.value.players.size)
            currentState.copy(
                moves = currentMoves,
                currentPlayer = _settings.value.players[currentPlayerIdx]
            )
        }
       // uiState.value.printInfo()
    }

    private fun isEndGame(currentPlayerId: String) {
        val isWinner = validator.checkWin(currentPlayerId) != null
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

    private fun generateGameBoard(size: Int): Array<Array<Cell>> = ( 0 until size).map { x->
        ( 0 until size).map { y ->
            Cell(x, x.toString(), Pair(x, y))
        }.toTypedArray()
    }.toTypedArray()

    fun changePlayerColor(player: Player) {
        val nextColor = getNextColor(player.color)
        val playerIndex = players.indexOf(player)
        players[playerIndex] = player.copy(color = nextColor)
    }

    fun changePlayerName(player: Player, name: String) {
        val playerIndex = players.indexOf(player)
        players[playerIndex] = player.copy(name = name)
    }

    fun setGameBoardSize(size: Int) {
        Log.e("elox","Set gameboard size == $gameBoardSize : $size")
        gameBoardSize = size
        Log.e("elox","Set gameboard size == $gameBoardSize : $size")
    }

    private fun getNextColor(currentColor: Color): Color {
        val colors = listOf(
            Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White, Color.Red, Color.Green,
            Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta,
        )
        val currentColorIndex = colors.indexOf(currentColor)
        val nextColorIndex = (currentColorIndex + 1).takeIf { it<colors.size } ?: 0
        return colors[nextColorIndex]
    }


}
