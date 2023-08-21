package com.example.connectcoins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
val SIZE = 3
val RANGE = 0..SIZE


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


//                    val data = RANGE.map { item ->
//                        Cell(item, "Cell$item", mutableStateOf(false))
//                    }
                    val dataHeader = RANGE.map { item ->
                        Cell(item, "Column $item", false)
                    }

//todo bulid array
                    val row: Array<Cell> = RANGE.map {
                           Cell(it, "Column $it", false)
                        }.toTypedArray()

                    val arrayData : Array<Array<Cell>> = RANGE.map {
                        row
                    }.toTypedArray()


                    var c = 0
                    for (x in RANGE) {
                        for (y in RANGE) {
                            Log.w("elox",">>>[${x}[${y}]=$c")
                            arrayData[x][y] = Cell(c++, "Cell $c", false)
                        }
                    }

                    var display = ""
                    for (x in RANGE) {
                        for (y in RANGE) {
                            display+="${arrayData[x][y].index} |"
                        }
                        display+="\n"
                    }
                    Log.e("elox","DISPLAY: \n $display")

                    val x = Random.nextBoolean()

                    TableScreen(SIZE, arrayData, dataHeader, x)
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
            text = text.toString(),
        )
    }
}

class Cell(
    val index: Int,
    val text: String,
    //var state: MutableState<Boolean>
    var state: Boolean
) {
    fun changeState() {
        Log.w("elox","Cell clicked! Text=$text, Index: $index")
        Log.w("elox","State0: $state")
        state = !state
        Log.w("elox","State1: $state")

    }
}

@Preview(showBackground = true)
@Composable
fun TableScreen(
    SIZE: Int = 5,
    data: Array<Array<Cell>> = arrayOf(
        arrayOf(Cell(1, "Cell", false)),
        arrayOf(Cell(1, "Cell", false)),
    ),
    dataHeader: List<Cell> = RANGE.map { item ->
        Cell(item, "Cell$item", false)
    },
    state: Boolean = false
) {

    val x = remember { mutableStateOf(state) }
   // val data = remember { mutableStateOf(data22) }

    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    val state = rememberLazyGridState(
        initialFirstVisibleItemIndex = 99
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(SIZE), //Adaptive 100dp
        state = state,
        content = {
                  items(100){ i ->
                      Box(
                          modifier = Modifier
                              .padding(8.dp)
                              .aspectRatio(1f)
                              .clip(RoundedCornerShape(5.dp))
                              .background(Color.Green),
                          contentAlignment = Alignment.Center
                      ) {
                          Text(text = "Item $i")
                      }

                  }
        },
    )

}

fun onCellClick2(state: MutableState<Boolean>) {
    Log.w("elox","State0: ${state.value}")
    state.value = !state.value
    Log.w("elox","State1: ${state.value}")
}