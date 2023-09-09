package com.example.connectcoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.connectcoins.data.Cell
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

            ConnectCoinsTheme {


//                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val gameViewModel: GameViewModel = viewModel()
                    val gameUiState by gameViewModel.uiState.collectAsState()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

//                        TopBar()

                        Navigation(gameViewModel)

                        TableScreen(
                            gameUiState,
                            gameViewModel,
                        )

                        Text(
                            modifier = Modifier.background(gameUiState.currentPlayer.color),
                            text = getString(R.string.current_player_X, gameUiState.currentPlayer.name))


                        if (gameUiState.isGameOver) {
                            Button(onClick = {
                                gameViewModel.resetGame()
                            }) {
                                Text(text = getString(R.string.restart_game))
                            }
                        }

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

@Preview(showBackground = true, widthDp = 500)
@Composable
fun TopBar(navController: NavController? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Magenta)
            .padding(horizontal = 20.dp)

    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColourText(
                text = stringResource(id = R.string.app_name)
            )
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navController!!.navigate(Screen.ConfigScreen.withArgs("Player name :]"))
                    },
                shape = CircleShape,
            ) {
                Image(
                    painterResource(R.drawable.ic_settings),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ColourText(text: String) {
    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.Yellow /*...*/)

    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    ),
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append(text)
            }
        }
    )

}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun TableScreen(
    gameUiState: GameUiState = GameUiState(Player("a",Color.Red)),
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
            val color = if (it.playerId != null) viewModel.getPlayer(it.playerId!!)!!.color else Color.Green
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
            .size(50.dp)
            //.aspectRatio(1f)
            .clip(RoundedCornerShape(25.dp))
            .background(cellColor),

        contentAlignment = Alignment.Center,
    ) {
        Text(text = item.cords.toString())
    }
}









