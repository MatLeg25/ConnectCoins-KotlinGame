package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectcoins.data.Cell
import com.example.connectcoins.data.DATA
import com.example.connectcoins.data.Player
import com.example.connectcoins.ui.BottomModal
import com.example.connectcoins.ui.GameUiState
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.theme.ConnectCoinsTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: GameViewModel = viewModel()

            ConnectCoinsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val dataHeader = DATA.RANGE.map { item ->
                        Cell(item, "Column $item")
                    }

                    val gameViewModel: GameViewModel = viewModel()
                    val gameUiState by gameViewModel.uiState.collectAsState()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TableScreen(
                            dataHeader,
                            gameUiState,
                            viewModel,
                        )

                        Text(
                            modifier = Modifier.background(gameUiState.currentPlayer.color),
                            text = "Current player: ${gameUiState.currentPlayer.name} , MOVES: ${gameUiState.moves} \n ${gameUiState.isGameOver}")

                        BottomModal(
                            gameUiState = gameUiState,
                            state = if (gameUiState.isGameOver) SheetValue.Expanded else SheetValue.Hidden
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TableScreen(
    dataHeader: List<Cell> = DATA.RANGE.map { item ->
        Cell(item, "Cell$item")
    },
    gameUiState: GameUiState = GameUiState(Player("a",Color.Red)),
    viewModel: GameViewModel = viewModel(),

    ) {

    Row() {
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
            val color = if (it.playerId != null) viewModel.getPlayer(it.playerId!!)!!.color else Color.Green
            CellItem(item = it, color)
        }

    }
}

@Composable
fun CellItem(item: Cell, color: Color) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            //.aspectRatio(1f)
            .clip(RoundedCornerShape(25.dp))
            .background(color),

        contentAlignment = Alignment.Center,
    ) {
        Text(text = item.text)
    }
}









