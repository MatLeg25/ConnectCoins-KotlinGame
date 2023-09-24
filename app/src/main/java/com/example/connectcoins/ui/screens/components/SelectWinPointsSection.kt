package com.example.connectcoins.ui.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Cell
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.screens.CellItem
import com.example.connectcoins.utils.Utils
import kotlinx.coroutines.launch

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
    val range = Utils.MIN_GAME_BOARD_SIZE-1 ..viewModel.settings.collectAsState().value.gameBoardSize
    val data: List<Cell> = run {
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
                    viewModel.setPointsToWin(nextIndex + range.first + 1)
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
                    viewModel.setPointsToWin(nextIndex + range.first)
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