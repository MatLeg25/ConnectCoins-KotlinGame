package com.example.connectcoins.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.ui.state.GameUiState
import com.example.connectcoins.utils.Utils

@Preview
@Composable
fun StatsModal(
    time: Int = 0,
    value: String = "",
    setShowStats: (Boolean) -> Unit = {},
    gameUiState: GameUiState = GameUiState(Player(name = "x", color = Utils.COIN_BRUSH_COLORS[0]))
) {

    val isGameOver = gameUiState.isGameOver
    val gameStateText =
        if (isGameOver) stringResource(id = R.string.game_ended)
        else stringResource(id = R.string.game_in_progress)
    val winner = gameUiState.winner

    Dialog(onDismissRequest = { setShowStats(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.DarkGray
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.game_stats),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(20.dp))

                    Column() {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color.LightGray,
                            text = stringResource(R.string.game_state_X, gameStateText),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        if (isGameOver) {
                            Text(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = Color.LightGray,
                                text =
                                if (winner != null) stringResource(id = R.string.game_winner_X, winner.name)
                                else stringResource(id = R.string.game_no_winner),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color.LightGray,
                            text = stringResource(R.string.moves_X, gameUiState.moves),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = Color.LightGray,
                            text = stringResource(R.string.time_X, time),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
                                onClick = {
                                    setShowStats(false)
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = stringResource(id = R.string.close))
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}