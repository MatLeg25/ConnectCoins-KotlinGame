package com.example.connectcoins.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BottomModal(
    state: SheetValue = SheetValue.Expanded,
    gameUiState: GameUiState = GameUiState(Player(name = "x", color = Utils.COIN_BRUSH_COLORS[0]))
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = state,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    val isGameOver = gameUiState.isGameOver
    val gameStateText =
        if (isGameOver) stringResource(id = R.string.game_ended)
        else stringResource(id = R.string.game_in_progress)
    val winner = gameUiState.winner

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                    text = stringResource(id = R.string.game_stats),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),

                    text = stringResource(R.string.game_state_X, gameStateText),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(24.dp))
                if (isGameOver) {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text =
                            if (winner != null) stringResource(id = R.string.game_winner_X, winner.name)
                            else stringResource(id = R.string.game_no_winner),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                } else {
                    //todo plsyers queue, time of game etc.
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 20.dp),
                    onClick = {
                        setSheetState(scope, sheetState, false)
                    }
                ) {
                    Text(text = stringResource(id = R.string.close))
                }
            }

        }
    },
    sheetContentColor = Color.Green,
        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                //toggle state
                setSheetState(scope, sheetState, !sheetState.isVisible)
            }) {
                Text(text = stringResource(id = R.string.show_stats))
            }
        }
    }

    if (state == SheetValue.Expanded) setSheetState(scope, sheetState, true)
}

@OptIn(ExperimentalMaterial3Api::class)
fun setSheetState(scope: CoroutineScope, currentState: SheetState, isExpanded: Boolean) {
    scope.launch {
        if (isExpanded) currentState.expand() else currentState.hide()
    }
}