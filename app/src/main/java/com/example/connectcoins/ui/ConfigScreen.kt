package com.example.connectcoins.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
import com.example.connectcoins.data.models.Cell
import com.example.connectcoins.utils.Utils
import kotlinx.coroutines.launch

@Preview(showBackground = true, widthDp = 300)
@Composable
fun ConfigScreen(
    name: String? = "name",
    changeBackground: () -> Unit = {},
    viewModel: GameViewModel = viewModel(),
    navController: NavController? = null,
) {

    val state = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .scrollable(state, Orientation.Vertical, true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = R.string.game_settings),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(50.dp))

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

        SectionDivider()

        SelectWinPointsSection(viewModel)

        SectionDivider()

        SetGameBoardSizeSection(viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = changeBackground
        ) {
            Text(text = stringResource(id = R.string.change_background))
        }

        Spacer(modifier = Modifier.height(30.dp))
        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(
            border = BorderStroke(5.dp, Color.Cyan),
            onClick = {
                viewModel.setConfig()
                navController!!.navigate(Screen.GameScreen.route)
            }) {
            Text(text = stringResource(id = R.string.start_game))
        }

    }

}

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


@Composable
fun SelectWinPointsSection(viewModel: GameViewModel) {
    Column {
        SectionHeader(R.string.number_of_points_to_win)
        SelectWinPoints(viewModel)
    }
}

@Composable
fun SelectWinPoints(
    viewModel: GameViewModel,
) {

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val coroutineScope = rememberCoroutineScope()
    val data: List<Cell> = run {
        val range = 1..viewModel.settings.collectAsState().value.gameBoardSize
        range.mapIndexed { index, r ->
            Cell(index, r.toString(), Pair(0,index))
        }.toMutableStateList()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button (
            modifier = Modifier.weight(0.2f),
            onClick = {
                coroutineScope.launch {
                    val currentIndex = listState.firstVisibleItemIndex
                    val nextIndex = if (currentIndex > 0) currentIndex-1 else currentIndex
                    listState.animateScrollToItem(index = nextIndex)
                    viewModel.setPointsToWin(nextIndex)
                }
            }
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.surfaceBright,
                modifier = Modifier
            )
        }

        LazyRow(
            modifier = Modifier
                .width(150.dp)
                .border(BorderStroke(2.dp, Color.Gray)),
            state = listState,
            userScrollEnabled = false,
        ) {
            itemsIndexed(items = data, itemContent = { index, dataItem ->
                val isMidCoin = (index == listState.firstVisibleItemIndex + 1)
                val colors = if (isMidCoin)  listOf(Color.Yellow, Color.Black) else listOf(Color.Gray, Color.Black)
                CellItem(
                    item = dataItem,
                    color = colors,
                    cellSize = 50.dp,
                    columnPadding = 0.dp,
                    displayedText = dataItem.text
                )
            })
        }

        Button (
            modifier = Modifier.weight(0.2f),
            onClick = {
                coroutineScope.launch {
                    val currentIndex = listState.firstVisibleItemIndex
                    val nextIndex = currentIndex+1
                    listState.animateScrollToItem(index = nextIndex)
                    viewModel.setPointsToWin(nextIndex)
                }
            }
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.surfaceBright,
                modifier = Modifier

            )
        }

    }

}

@Composable
fun SectionHeader(textResId: Int) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )) {
        Text(
            text = stringResource(id = textResId),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

}
@Composable
fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 20.dp),
        thickness = 1.dp
    )
}
