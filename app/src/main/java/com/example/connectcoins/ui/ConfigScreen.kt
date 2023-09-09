package com.example.connectcoins.ui

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true, widthDp = 500)
@Composable
fun ConfigScreen(
    name: String? = "name",
    viewModel: GameViewModel = viewModel(),
) {
    val colors = listOf(
        Color(0xFF70C70F),
        Color.White,
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val context = LocalContext.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Game settings: ")
            Spacer(modifier = Modifier.height(50.dp))
            viewModel.getPlayers().forEachIndexed { index, player ->
                var playerName by remember {
                    mutableStateOf(player.name)
                }
                var playerColor by remember {
                    mutableStateOf(colors[index])
                }
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(playerColor)
                            .clickable {
                                playerColor = getNextColor(playerColor)
                            }
                    ) {
                        Text(text = "Player ${index + 1}: ")
                    }
                    TextField(value = playerName, onValueChange = {
                        playerName = it
                    }, label = {
                        Text("Player ${index + 1}")
                    })

                }
                Spacer(modifier = Modifier.height(30.dp))
            }

            Button(onClick = { Toast.makeText(context, "Jeszcze nie działa :|", Toast.LENGTH_LONG).show() }) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.height(30.dp))

            DropdownMenu()
            
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu() {

    val options = listOf<String>(
        "4x4", "5x5", "6x6"
    )

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
                DropdownMenuItem(
                    text = {
                        Text(option)
                    },
                    onClick = {
                        selectedOption = option
                        isExpanded = false
                    })
            }
            }
    }

}

fun getNextColor(color: Color): Color {
    val colors = listOf(
        Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White, Color.Red, Color.Green,
        Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta,
    )
    val currentColorIndex = colors.indexOf(color)
    val nextColorIndex = (currentColorIndex + 1).takeIf { it<colors.size } ?: 0
    return colors[nextColorIndex]
}