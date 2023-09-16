package com.example.connectcoins.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.connectcoins.R

@Preview(showBackground = true, widthDp = 500)
@Composable
fun ConfigScreen(
    name: String? = "name",
    viewModel: GameViewModel = viewModel(),
    navController: NavController? = null,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.game_settings))
            Spacer(modifier = Modifier.height(50.dp))
            viewModel.players.forEachIndexed { index, player ->
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(player.color)
                            .clickable {
                                viewModel.changePlayerColor(player)
                            }
                    ) {
                        Text(stringResource(id = R.string.player_X, index + 1))
                    }
                    TextField(value = player.name, onValueChange = {
                        viewModel.changePlayerName(player, it)
                    }, label = {
                        Text(stringResource(id = R.string.player_X, index + 1))
                    })

                }
                Spacer(modifier = Modifier.height(30.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            DropdownMenu(viewModel)

            Spacer(modifier = Modifier.height(30.dp))
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    navController!!.navigate(Screen.MainScreen.route)
                }) {
                Text(text = stringResource(id = R.string.start_game))
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    viewModel: GameViewModel
) {

    val options = (3..10).toList()

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedOption by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it}
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false}
        ) {
            options.forEach { option ->
                val displayedText = "$option x $option"
                DropdownMenuItem(
                    text = {
                        Text(displayedText)
                    },
                    onClick = {
                        selectedOption = displayedText
                        viewModel.setGameBoardSize(option)
                        isExpanded = false
                    }
                )
            }
        }
    }

}

