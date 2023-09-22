package com.example.connectcoins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Player
import com.example.connectcoins.utils.Utils

@Preview
@Composable
fun CurrentPlayerLabel(
    gameUiState: GameUiState = GameUiState(
        Player(
            "a",
            Utils.COIN_BRUSH_COLORS[0],
        ),
    ),
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(horizontal = gameBoardHorizontalPadding.dp)
            .clip(CutCornerShape(gameBoardHorizontalPadding.dp))
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.Black, Color.DarkGray, Color.Black)
            ))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = gameBoardHorizontalPadding.dp, vertical = 10.dp),
        ) {
            Text(
                text = context.getString(R.string.current_player),
                style = typography.labelSmall,
                color = Color.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = gameUiState.currentPlayer.name,
                style = typography.bodyLarge,
                color = gameUiState.currentPlayer.color[0],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}