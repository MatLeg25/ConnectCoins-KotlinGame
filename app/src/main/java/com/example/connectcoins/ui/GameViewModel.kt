package com.example.connectcoins.ui

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

    var gameboard: Array<Array<Cell>>
    private val totalMoves: Int
    private var validator: Validator

    val SIZE = 4
    val RANGE = 0 until SIZE

    // Game UI state
    private val _settings = MutableStateFlow(
        GameSettingsState(
            listOf(
                Player(name = "Użas", color = Color.Red), Player(name = "Koszmir", color = Color.Blue)
            ),
            4
        )
    )
    val settings: StateFlow<GameSettingsState> = _settings.asStateFlow()

    val players = mutableStateListOf<Player>().apply {
        addAll(_settings.value.players.toList())
    }

    private val _uiState = MutableStateFlow(GameUiState(_settings.value.players[0]))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    init {
        gameboard = generateGameBoard()
        totalMoves = gameboard.size * gameboard[0].size
        validator = Validator(gameboard)
    }

    fun resetGame() {
        gameboard = generateGameBoard()
        validator = Validator(gameboard)
        _uiState.value = GameUiState(_settings.value.players[0])
    }

    fun getPlayer(playerId: String): Player =_settings.value.players.firstOrNull { it.id == playerId }!!

    fun getPlayers() = _settings.value.players

    fun onColumnClick(columnIdx: Int, currentPlayerId: String) {
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

    private fun generateGameBoard(): Array<Array<Cell>> = RANGE.map { x->
        RANGE.map { y ->
            Cell(x, x.toString(), Pair(x, y))
        }.toTypedArray()
    }.toTypedArray()


    fun updateSetting(players: List<Player>) {
        _settings.update {
            it.copy(
                players = players,
            )
        }
    }

    fun updateSetting() {
        _settings.update {
            it.copy(
                players = players
            )
        }
    }

    fun changePlayerColor(player: Player) {
        val nextColor = getNextColor(player.color)
        val playerIndex = players.indexOf(player)
        players[playerIndex] = player.copy(color = nextColor)
    }

    fun changePlayerName(player: Player, name: String) {
        val playerIndex = players.indexOf(player)
        players[playerIndex] = player.copy(name = name)
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
