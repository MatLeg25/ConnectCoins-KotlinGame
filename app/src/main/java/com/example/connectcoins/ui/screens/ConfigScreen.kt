package com.example.connectcoins.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.connectcoins.R
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.navigation.Screen
import com.example.connectcoins.ui.screens.components.SelectWinPointsSection
import com.example.connectcoins.ui.screens.components.SetGameBoardSizeSection
import com.example.connectcoins.utils.Utils

@Preview(showBackground = true, widthDp = 300)
@Composable
fun ConfigScreen(
    name: String? = "name",
    changeBackground: () -> Unit = {},
    viewModel: GameViewModel = viewModel(),
    navController: NavController? = null,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = R.string.game_settings),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(30.dp))

        viewModel.players.forEachIndexed { index, player ->
            Row {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = player.color
                            )
                        )
                        .clickable {
                            viewModel.changePlayerColor(player)
                        }
                )

                TextField(
                    value = player.name,
                    onValueChange = {
                        if (it.length <= Utils.MAX_PLAYER_NAME_LENGTH) viewModel.changePlayerName(player, it)
                    },
                    label = {
                        Text(stringResource(id = R.string.player_X, index + 1))
                    },
                    singleLine = true
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        SelectWinPointsSection(viewModel)

        Spacer(modifier = Modifier.height(20.dp))

        SetGameBoardSizeSection(viewModel)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = changeBackground
        ) {
            Text(text = stringResource(id = R.string.change_background))
        }

        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(
            border = BorderStroke(5.dp, Color.Cyan),
            onClick = {
                viewModel.setConfig()
                navController!!.navigate(Screen.GameScreen.route)
            }) {
            Text(text = stringResource(id = R.string.start_game))
        }

        Spacer(modifier = Modifier.height(20.dp))

    }

}