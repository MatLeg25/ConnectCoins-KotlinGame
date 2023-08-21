package com.example.connectcoins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.ui.theme.ConnectCoinsTheme
import kotlin.random.Random

//todo move to cofnig class
val SIZE = 24
val RANGE = 0 until SIZE


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ConnectCoinsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val data: List<Cell> = RANGE.map { item ->
                        Cell(item, "Cell$item", mutableStateOf(false))
                    }

                    val dataHeader = RANGE.map { item ->
                        Cell(item, "Column $item", mutableStateOf(false))
                    }

                    val x = Random.nextBoolean()

                    TableScreen(SIZE, data, dataHeader, x)
                }
            }
        }
    }
}

class Cell(
    val index: Int,
    var text: String,
    var state: MutableState<Boolean>
) {
    fun changeState() {
        state.value = !state.value
    }
}

@Composable
fun CellItem(item: Cell) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(5.dp))
            .background(if (item.state.value) Color.Red else Color.Green)
            .clickable {
                item.changeState()
            },

        contentAlignment = Alignment.Center,
    ) {
        Text(text = item.text)
    }
}

@Preview(showBackground = true)
@Composable
fun TableScreen(
    SIZE: Int = 5,
    data: List<Cell> = RANGE.map { item ->
        Cell(item, "Cell$item", mutableStateOf(false))
    },
    dataHeader: List<Cell> = RANGE.map { item ->
        Cell(item, "Cell$item", mutableStateOf(false))
    },
    state: Boolean = false
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(4), //Adaptive 100dp
        //state = state,
        content = {
            items(data){ item ->
                CellItem(item = item)
            }
        },
    )

}
