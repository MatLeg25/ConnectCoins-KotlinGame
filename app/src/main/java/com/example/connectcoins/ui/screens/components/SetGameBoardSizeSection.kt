package com.example.connectcoins.ui.screens.components


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.connectcoins.R
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetGameBoardSizeSection(
    viewModel: GameViewModel
) {

    val options = Utils.GAME_BOARD_SIZE_RANGE.toList()

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedOption by remember {
        mutableStateOf(
            optionTextFormatter(viewModel.gameboard.size, viewModel.gameboard.size)
        )
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
            modifier = Modifier.menuAnchor(),
            label = {
                Text(text = stringResource(id = R.string.game_board_size))
            }
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false}
        ) {
            options.forEach { option ->
                val displayedText = optionTextFormatter(option, option)
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

fun optionTextFormatter(rows: Int, columns: Int) = "$rows x $columns"

