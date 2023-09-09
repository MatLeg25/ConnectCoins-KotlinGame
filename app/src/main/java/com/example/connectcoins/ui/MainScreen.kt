package com.example.connectcoins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import com.example.connectcoins.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(gameViewModel: GameViewModel) {

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

            Navigation(gameViewModel)

            val gameUiState by gameViewModel.uiState.collectAsState()

            TableScreen(
                gameUiState,
                gameViewModel,
            )

            Text(
                modifier = Modifier.background(gameUiState.currentPlayer.color),
                text = context.getString(R.string.current_player_X, gameUiState.currentPlayer.name))


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