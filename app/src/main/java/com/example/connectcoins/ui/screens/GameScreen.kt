package com.example.connectcoins.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectcoins.R
import com.example.connectcoins.ui.state.GameUiState
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.screens.components.StatsModal
import com.example.connectcoins.ui.screens.components.TopBar
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val gameUiState by gameViewModel.uiState.collectAsState()
    val showStats =  remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(0) }
    val isGameOver = !gameUiState.isGameOver

    LaunchedEffect(isGameOver) {
        while(isGameOver) {
            delay(1.seconds)
            time++
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBar(gameUiState, navController) { showStats.value = !showStats.value }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameBoardScreen(gameUiState, gameViewModel)

            Spacer(modifier = Modifier.height(20.dp))

            if (gameUiState.isGameOver) {
                showStats.value = true
                Button(
                    onClick = {
                        time = 0
                        gameViewModel.resetGame()
                    }
                ) {
                    Text(text = context.getString(R.string.restart_game))
                }
            }

            GameStatsModal(time, gameUiState, showStats)

        }
    }

}


@Composable
fun GameStatsModal(time: Int, gameUiState: GameUiState, showStats: MutableState<Boolean>) {

    if(showStats.value) {
        StatsModal(
            time = time,
            value = "",
            setShowStats = { showStats.value = it },
            gameUiState = gameUiState
        )
    }


}
