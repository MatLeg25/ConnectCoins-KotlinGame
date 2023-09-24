package com.example.connectcoins.ui.screens.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.R
import com.example.connectcoins.data.models.Cell
import com.example.connectcoins.ui.GameViewModel
import com.example.connectcoins.ui.screens.CellItem
import com.example.connectcoins.utils.Utils
import kotlinx.coroutines.launch

@Preview
@Composable
fun SelectWinPointsSection(viewModel: GameViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                )) {
            HorizontalDivider(thickness = 1.dp)
            Text(
                modifier = Modifier.shadow(11.dp),
                text = stringResource(id = R.string.number_of_points_to_win),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        SelectWinPoints(viewModel)
    }
}

@Composable
fun SelectWinPoints(
    viewModel: GameViewModel,
) {

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 0)
    val coroutineScope = rememberCoroutineScope()
    val settingsState = viewModel.settings.collectAsState()
    val range = Utils.MIN_GAME_BOARD_SIZE - 1  .. settingsState.value.gameBoardSize + 1
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
            },
            enabled = ( listState.firstVisibleItemIndex != 0)
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
                    Log.w("elox","CooOOo : $nextIndex, ${settingsState.value.gameBoardSize}")
                }
            },
            enabled = (listState.firstVisibleItemIndex != settingsState.value.gameBoardSize - 2 - 2) //todo bug when user change from bigger to small size
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