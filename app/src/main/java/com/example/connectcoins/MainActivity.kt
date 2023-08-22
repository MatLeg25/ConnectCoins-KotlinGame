package com.example.connectcoins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectcoins.ui.theme.ConnectCoinsTheme

//todo move to cofnig class
val SIZE = 8
val RANGE = 0 until SIZE
val dataRow: Array<Cell> = RANGE.map { item ->
    Cell(item, "Cell$item", mutableStateOf(false))
}.toTypedArray()
val DATA: Array<Array<Cell>> = RANGE.map { item -> dataRow }.toTypedArray()

val DATA0: Array<Array<Cell>> = arrayOf(
    arrayOf(
        Cell(0,"0", mutableStateOf(false)),
        Cell(1,"1", mutableStateOf(false)),
        Cell(2,"2", mutableStateOf(false)),
        Cell(3,"3", mutableStateOf(false)),
    ),
    arrayOf(
        Cell(4,"4", mutableStateOf(false)),
        Cell(5,"5", mutableStateOf(false)),
        Cell(6,"6", mutableStateOf(false)),
        Cell(7,"7", mutableStateOf(false)),
    ),
    arrayOf(
        Cell(8,"8", mutableStateOf(false)),
        Cell(9,"9", mutableStateOf(false)),
        Cell(10,"10", mutableStateOf(false)),
        Cell(11,"11", mutableStateOf(false)),
    ),
    arrayOf(
        Cell(12,"12", mutableStateOf(false)),
        Cell(13,"13", mutableStateOf(false)),
        Cell(14,"14", mutableStateOf(false)),
        Cell(15,"15", mutableStateOf(false)),
    )
)

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

                    val dataHeader = RANGE.map { item ->
                        Cell(item, "Column $item", mutableStateOf(false))
                    }

                    TableScreen(SIZE, DATA0, dataHeader)
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
    fun changeState(column: Int) {
        onCellClick(index, column)
    }
}

@Composable
fun CellItem(item: Cell, column: Int) {
    val bgColor: Color by animateColorAsState(if (item.state.value) Color.Red else Color.Green) //todo check animation
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            //.aspectRatio(1f)
            .clip(RoundedCornerShape(25.dp))
            .background(bgColor)
            .clickable {
                item.changeState(column)
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
    data: Array<Array<Cell>> = DATA0,
    dataHeader: List<Cell> = RANGE.map { item ->
        Cell(item, "Cell$item", mutableStateOf(false))
    },
) {

    Row() {
        data.forEachIndexed { index, column ->
            SingleColumn(items = column, columnIdx = index)
        }
    }

}

@Composable
fun SingleColumn(items: Array<Cell>, columnIdx: Int) {
    Column {
        items.forEach {
            CellItem(item = it, columnIdx)
        }
    }
}

fun onCellClick(index: Int, column: Int) {
    Log.w("elox","Index: $index, column: $column")
    val cell = DATA0[column].findLast { !it.state.value }
    cell?.let {
        it.state.value = !it.state.value
    }
    displayState()
}

fun displayState() {
    var display = ""
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            val cell = DATA0[y][x]
            display += if (cell.state.value) "O" else "X"
        }
        display += "\n"
    }
    Log.e("elox", display)
    checkWin()
}

val WIN = 3
fun checkWin() {
   // checkVertical()
   // checkHorizontal()
//    checkDiagonal2b()
//    Log.e("elox","~~~~~~~~~~")
//    checkDiagonal2FromRIght()
//    Log.e("elox","~~~~~~~~~~")
    checkDiagonalLeftTop()

}


fun checkVertical() {
    var result = 0
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            val cell = DATA0[y][x]
            result = if (cell.state.value) result + 1 else 0
            if (result == WIN) Log.e("elox", ">>>WIN horizontal<<<")
        }
        result = 0
    }
}

fun checkHorizontal() {
    var result = 0
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            val cell = DATA0[x][y]
            result = if (cell.state.value) result + 1 else 0
            if (result == WIN) Log.e("elox", ">>>WIN vertical <<<")
        }
        result = 0
    }
}

fun checkDiagonal() {
    var result = 0
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            if (x==y) {
                val cell = DATA0[x][y]
                result = if (cell.state.value) result + 1 else 0
            }
            if (result == WIN) Log.e("elox", ">>>WIN DIAGONAL <<<")
        }
    }
}

fun checkDiagonal2() {
    var result = 0
    val ES = 8
    for (x in 0 .. ES) {
        for (y in 0 .. ES - x) {
            val point = Pair(x+y,y)
          //  val cell = DATA0[point.first][point.second]
            Log.e("elox","Checked point: ${point.first},${point.second}")
       //     result = if (cell.state.value) result + 1 else 0
            if (result == WIN) Log.e("elox", ">>>WIN DIAGONAL <<<")
        }
        Log.e("elox","===========================")
    }
}

fun checkDiagonalLBandRB() {
    var result = 0
    val ES = 8
    for (x in 0 .. ES) {
        for (y in 0 .. ES - x) {
            val pointLB = Pair(x+y,y)
            val pointRT = Pair(y,x+y)
            //  val cell = DATA0[point.first][point.second]
//            Log.e("elox","Checked point: ${pointLB.first},${pointLB.second}")
//            Log.w("elox","Checked point: ${pointRT.first},${pointRT.second}")
            //     result = if (cell.state.value) result + 1 else 0
            if (result == WIN) Log.e("elox", ">>>WIN DIAGONAL <<<")
        }
        Log.e("elox","===========================")
    }
}


fun checkDiagonalRightBottom() {
    var result = 0
    val ES = 8
    for (x in 0 until ES+1) {
        for (y in 0 until ES-x+1) {
//            val pointRB = Pair(x+y,ES-y)
            val pointLT = Pair(ES-x,x+y)
            //  val cell = DATA0[point.first][point.second]
            Log.e("elox","Checked point: ${pointLT.first},${pointLT.second}")
//            Log.w("elox","Checked point: ${pointRB.first},${pointRB.second}")
            //     result = if (cell.state.value) result + 1 else 0
            if (result == WIN) Log.e("elox", ">>>WIN DIAGONAL <<<")
        }
        Log.e("elox","===========================")
    }
}

fun checkDiagonalLeftTop() {
    val ES = 8
    for (x in 0 until ES+1) {
        for (y in x until ES+1) {
            val pointLT = Pair(ES-y,y-x)
            Log.e("elox","Checked point: ${pointLT.first},${pointLT.second}")

        }
        Log.e("elox","===========================")
    }
}


