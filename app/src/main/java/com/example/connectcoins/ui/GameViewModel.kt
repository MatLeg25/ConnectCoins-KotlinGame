package com.example.connectcoins.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.connectcoins.data.models.Cell
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.utils.Utils
import com.example.connectcoins.utils.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(): ViewModel() {

    lateinit var gameboard: Array<Array<Cell>>
    private var totalMoves: Int = 0
    private lateinit var validator: Validator

    // Game UI state
    private val _settings = MutableStateFlow(
        GameSettingsState(
            getDefaultPlayers(),
            Utils.GAME_BOARD_DEFAULT_SIZE,
            Utils.DEFAULT_POINTS_TO_WIN,
            Utils.GAME_BOARD_BACKGROUND_COLOR
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
        Player(name = "Player1", color = Utils.COIN_BRUSH_COLORS[0]),
        Player(name = "Player2", color = Utils.COIN_BRUSH_COLORS[1])
    )


    fun resetGame() {
        Log.w("elox"," RESET GAME!!!")
        gameboard = generateGameBoard(_settings.value.gameBoardSize)
        totalMoves = gameboard.size * gameboard[0].size
        validator = Validator(gameboard, _settings.value.pointsToWin)
        _uiState.update {
            it.copy(
                currentPlayer = _settings.value.players.first(),
                moves = 0,
                isGameOver = false,
                winner = null,
            )
        }
    }

    fun getPlayer(playerId: String): Player = _settings.value.players.firstOrNull { it.id == playerId }!!

    fun onColumnClick(columnIdx: Int, currentPlayerId: String) {
        //prevents ui changes if game is ended
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
        if (playerIndex >=0) players[playerIndex] = player.copy(name = name)
    }

    fun setGameBoardSize(size: Int) {
        _settings.update {
            it.copy(
                gameBoardSize = size
            )
        }
    }

    fun setPointsToWin(points: Int) {
        Log.w("elox","UPDATE GAME BOARD SIZE: $points")

        _settings.update {
            it.copy(
                pointsToWin = points
            )
        }
    }

    fun setConfig() {
        resetGame()
        _settings.update { settings ->
            settings.copy(
                players = players,
            )
        }
        _uiState.update {  currentState ->
            val currentMoves = 0
            val currentPlayerIdx = 0
            currentState.copy(
                moves = currentMoves,
                currentPlayer = _settings.value.players[currentPlayerIdx]
            )
        }
    }

    fun getPointsToWinRange(): IntRange = Utils.MIN_GAME_BOARD_SIZE-1 .. settings.value.gameBoardSize


    private fun getNextColor(currentColor: List<Color>): List<Color> {
        val usedColors = players.map { it.color }.toMutableSet()
        usedColors.add(_settings.value.gameBoardBackgroundColor)
        val currentColorIndex = Utils.COIN_BRUSH_COLORS.indexOf(currentColor)
        val nextIndex = currentColorIndex + 1
        val nextColorIndex = nextIndex.takeIf { it < Utils.COIN_BRUSH_COLORS.size } ?: 0
        val nextColor = Utils.COIN_BRUSH_COLORS[nextColorIndex]
        return if (nextColor in usedColors) getNextColor(nextColor) else nextColor
    }


}
