package com.example.connectcoins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectcoins.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    navController: NavController,
) {

// A surface container using the 'background' color from the theme
    Surface(
        //modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopBar(navController)

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val gameUiState by gameViewModel.uiState.collectAsState()

                GameBoardScreen(gameUiState, gameViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                CurrentPlayerLabel(gameUiState)

                if (gameUiState.isGameOver) {
                    Button(onClick = {
                        gameViewModel.resetGame()
                    }) {
                        Text(text = context.getString(R.string.restart_game))
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

@Composable
fun CurrentPlayerLabel(gameUiState: GameUiState) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = gameBoardHorizontalPadding.dp)
                .clip(CutCornerShape(gameBoardHorizontalPadding.dp))
                .background(gameUiState.currentPlayer.color)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = gameBoardHorizontalPadding.dp, vertical = 20.dp),
                text = context.getString(R.string.current_player_X, gameUiState.currentPlayer.name),
                style = typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}