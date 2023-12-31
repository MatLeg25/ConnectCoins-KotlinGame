package com.example.connectcoins.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectcoins.data.models.Cell
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.ui.state.GameUiState
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.utils.Utils

const val gameBoardHorizontalPadding = 10
const val gameBoardInnerColumnPadding = 1

@Preview(showBackground = true, widthDp = 500)
@Composable
fun GameBoardScreen(
    gameUiState: GameUiState = GameUiState(Player(name = "a", color = Utils.COIN_BRUSH_COLORS[0])),
    viewModel: GameViewModel = viewModel(),
) {

    val cellSize = Utils.calculateCellSize(LocalConfiguration.current, viewModel.settings.value.gameBoardSize)

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = gameBoardHorizontalPadding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        viewModel.gameboard.forEachIndexed { index, column ->
            SingleColumn(
                cellSize = cellSize,
                items = column,
                columnIdx = index,
                currentPlayerId = gameUiState.currentPlayer.id,
                viewModel = viewModel,
            )
        }
    }

}

@Composable
fun SingleColumn(
    cellSize: Dp,
    items: Array<Cell>,
    columnIdx: Int,
    currentPlayerId: String,
    viewModel: GameViewModel = viewModel(),
) {

    Column(
        modifier = Modifier
            .clickable {
                viewModel.onColumnClick(columnIdx, currentPlayerId)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${columnIdx + 1}",
            color = Color.LightGray,
        )
        items.forEach {
            val color = if (it.playerId != null) viewModel.getPlayer(it.playerId!!).color else viewModel.settings.value.gameBoardBackgroundColor
            CellItem(item = it, color, cellSize, gameBoardInnerColumnPadding.dp, "")
        }

    }
}

@Composable
fun CellItem(
    item: Cell,
    color: List<Color>,
    cellSize: Dp,
    columnPadding: Dp,
    displayedText: String = item.cords.toString()
) {

    val borderWidth = if (item.isWin) (cellSize/4) else 0.dp

    Box(
        modifier = Modifier
            .padding(columnPadding)
            .size(cellSize)
            .clip(RoundedCornerShape(cellSize))
            .border(
                width = borderWidth,
                brush = Brush.radialGradient(
                    colors = listOf(Color.Yellow, color[1])
                ),
                shape = RoundedCornerShape(cellSize)
            )
            .background(
                brush = Brush.radialGradient(
                    colors = color
                )
            ),

        contentAlignment = Alignment.Center,
    ) {
        Text(text = displayedText)
    }
}