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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.data.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BottomModal(
    state: SheetValue = SheetValue.Expanded,
    gameUiState: GameUiState = GameUiState(Player("x", Color.Transparent))
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
    val gameStateText = if (isGameOver) "ENDED" else "IN PROGRESS"
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
                    text = "Game stats",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),

                    text = "Game state: $gameStateText",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(24.dp))
                if (isGameOver) {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = if (winner != null) "The winner is: ${winner.name}" else "The game has no winner, draw.",
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
                    Text(text = "Close")
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
                Text(text = "Show stats")
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