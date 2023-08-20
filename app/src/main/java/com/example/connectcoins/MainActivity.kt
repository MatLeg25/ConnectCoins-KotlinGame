package com.example.connectcoins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.ui.theme.ConnectCoinsTheme
import kotlin.random.Random

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

                    val SIZE = 5
                    val data = (1..SIZE).map { item ->
                        Cell(item, "Cell$item", mutableStateOf(false))
                    }
                    val dataHeader = (1..SIZE).map { item ->
                        Cell(item, "Column $item", mutableStateOf(false))
                    }


                    val x = Random.nextBoolean()

                    TableScreen(SIZE, data, dataHeader, x)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight),
        color = if(selected) Color.Red else Color.Transparent,
        onClick = onClick
    ) {
        Text(
            text = selected.toString(),
        )
    }
}

class Cell(
    val index: Int,
    val text: String,
    var state: MutableState<Boolean>
) {
    fun changeState() {
        Log.w("elox","Cell clicked! Text=$text, Index: $index")
        Log.w("elox","State0: $state")
        state.value = !state.value
        Log.w("elox","State1: $state")

    }
}

@Preview(showBackground = true)
@Composable
fun TableScreen(
    SIZE: Int = 5,
    data: List<Cell> = (1..SIZE).map { item ->
        Cell(item, "Cell$item", mutableStateOf(false))
    },
    dataHeader: List<Cell> = (1..SIZE).map { item ->
        Cell(item, "Cell$item", mutableStateOf(false))
    },
    state: Boolean = false
) {

    val x = remember { mutableStateOf(state) }
   // val data = remember { mutableStateOf(data22) }

    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(
        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(8.dp)) {
        // Here is the header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = dataHeader[0].text, weight = column2Weight, x.value) { onCellClick2(x) }
                TableCell(text = dataHeader[1].text, weight = column2Weight, x.value) { onCellClick2(x) }
                TableCell(text = dataHeader[2].text, weight = column2Weight, x.value) { onCellClick2(x) }
                TableCell(text = dataHeader[3].text, weight = column2Weight, x.value) { onCellClick2(x) }
            }
        }
        // Here are all the lines of your table.
        items(data) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = it.text, weight = column2Weight, it.state.value) { it.changeState() }
                TableCell(text = it.text, weight = column2Weight, it.state.value) { it.changeState() }
                TableCell(text = it.text, weight = column2Weight, it.state.value) { it.changeState() }
                TableCell(text = it.text, weight = column2Weight, it.state.value) { it.changeState() }
            }
        }
    }
}

fun onCellClick2(state: MutableState<Boolean>) {
    Log.w("elox","State0: ${state.value}")
    state.value = !state.value
    Log.w("elox","State1: ${state.value}")
}