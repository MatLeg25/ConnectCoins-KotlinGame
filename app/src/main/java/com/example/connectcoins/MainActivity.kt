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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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

            var showSheet by remember { mutableStateOf(false) }

            if (showSheet) {
                BottomSheet() {
                    showSheet = false
                }
            }

            ConnectCoinsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val dataHeader = RANGE.map { item ->
                        Cell(item, "Column $item", mutableStateOf(false))
                    }

                    Column() {

                        TableScreen(SIZE, DATA0, dataHeader)

                        Button(
                            modifier = Modifier
                                .height(50.dp),
                            onClick = {
                                showSheet = true
                            }) {
                            Text(text = "Show BottomSheet")
                        }
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Click outside the bottom sheet to hide it",
            style = MaterialTheme.typography.bodyMedium
        )
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

    val isWinner = checkWin()
    if (isWinner) {

    }
}

val WIN = 3

fun checkWin(): Boolean {
    return if (checkHorizontal()) {
        Log.w("elox",">>>WIN by checkHorizontal")
        true
    }
        else if (checkVertical()) {
        Log.w("elox",">>>WIN by checkVertical")
            true
    }
    else false
    //todo update

//        else if (checkDiagonalLeftBottomAndRightTop()) {
//        Log.w("elox",">>>WIN by checkDiagonalLeftBottomAndRightTop")
//            true
//    }
//        else if (checkDiagonalRightBottom()) {
//        Log.w("elox",">>>WIN by checkDiagonalRightBottom")
//            true
//    }
//        else {
//        Log.w("elox",">>>WIN by checkDiagonalLeftTop")
//            checkDiagonalLeftTop()
//    }
}


fun checkHorizontal(): Boolean {
    var result = 0
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            val cell = DATA0[y][x]
            result = if (cell.state.value) result + 1 else 0
            if (result == WIN) return true
        }
        result = 0
    }
    return false
}

fun checkVertical(): Boolean {
    var result = 0
    for (x in DATA0.indices) {
        for (y in DATA0[0].indices) {
            val cell = DATA0[x][y]
            result = if (cell.state.value) result + 1 else 0
            if (result == WIN) return true
        }
        result = 0
    }
    return false
}

fun checkDiagonalLeftBottomAndRightTop():Boolean {
    var result1 = 0
    var result2 = 0
    for (x in 0 .. SIZE) {
        for (y in 0 .. SIZE - x) {
            val pointLB = Pair(x+y,y)
            val pointRT = Pair(y,x+y)
              val cell1 = DATA0[pointLB.first][pointLB.second]
            result1 = if (cell1.state.value) result1 + 1 else 0
            if (result1 == WIN) return true

            val cell2 = DATA0[pointRT.first][pointRT.second]
            result2 = if (cell2.state.value) result2 + 1 else 0
            if (result2 == WIN) return true
        }
    }
    return false
}


fun checkDiagonalRightBottom(): Boolean {
    var result = 0
    for (x in 0 until SIZE+1) {
        for (y in 0 until SIZE-x+1) {
            val pointRB = Pair(x+y,SIZE-y)
            val cell = DATA0[pointRB.first][pointRB.second]
             result = if (cell.state.value) result + 1 else 0
            if (result == WIN) return true
        }
    }
    return false
}

fun checkDiagonalLeftTop(): Boolean {
    var result = 0
    for (x in 0 until SIZE+1) {
        for (y in x until SIZE+1) {
            val pointLT = Pair(SIZE-y,y-x)
            val cell = DATA0[pointLT.first][pointLT.second]
            result = if (cell.state.value) result + 1 else 0
            if (result == WIN) return true
        }
    }
    return false
}


