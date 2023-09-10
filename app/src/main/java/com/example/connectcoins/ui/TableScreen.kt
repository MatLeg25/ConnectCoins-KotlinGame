package com.example.connectcoins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectcoins.data.Cell
import com.example.connectcoins.data.Player


@Preview(showBackground = true, widthDp = 500)
@Composable
fun TableScreen(
    gameUiState: GameUiState = GameUiState(Player(name = "a", color = Color.Red)),
    viewModel: GameViewModel = viewModel(),
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        viewModel.gameboard.forEachIndexed { index, column ->
            SingleColumn(
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
        Text(text = "${columnIdx + 1}")
        items.forEach {
            val color = if (it.playerId != null) viewModel.getPlayer(it.playerId!!).color else Color.Green
            CellItem(item = it, color)
        }

    }
}

@Composable
fun CellItem(item: Cell, color: Color) {
    val winColor = Color.Yellow

    val cellColor = if (item.isWin) winColor else color

    Box(
        modifier = Modifier
            .padding(8.dp)
//            .size()
            .clip(RoundedCornerShape(25.dp))
            .background(cellColor),

        contentAlignment = Alignment.Center,
    ) {
        Text(text = item.cords.toString())
    }
}
