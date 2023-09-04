package com.example.connectcoins.utils

import android.util.Log
import com.example.connectcoins.data.Cell

class Validator(private val gameboard: Array<Array<Cell>>) {

    val WIN = 3

    private val rowsRange = gameboard.indices
    private val columnsRange = gameboard[0].indices
    private val winCombination: MutableList<Pair<Int, Int>> = mutableListOf()
    private var playerId = ""

    val condition: (cell: Cell) -> Boolean = { cell -> cell.playerId == playerId }

    fun checkWin(currentPlayerId: String): List<Pair<Int, Int>>? {
        playerId = currentPlayerId

        return if (checkHorizontal()) {
            Log.w("elox",">>>WIN by checkHorizontal \n WIN COMBINATION: $winCombination")
            winCombination.forEach { cords ->
                gameboard[cords.first][cords.second].isWin = true
            }
            winCombination
        }
        else if (checkVertical()) {
            Log.w("elox",">>>WIN by checkVertical \n WIN COMBINATION: $winCombination")
            winCombination.forEach { cords ->
                gameboard[cords.first][cords.second].isWin = true
            }
            winCombination
//        }
//        else if (checkDiagonalLeftBottomAndRightTop()) {
//            Log.w("elox",">>>WIN by checkDiagonalLeftBottomAndRightTop")
//            winCombination
//    }
//        else if (checkDiagonalRightBottom()) {
//            Log.w("elox",">>>WIN by checkDiagonalRightBottom")
//            winCombination
//        }
//        else if (checkDiagonalLeftTop()){
//            Log.w("elox",">>>WIN by checkDiagonalLeftTop")
//            winCombination
        }  else null
    }

    private fun checkHorizontal(): Boolean {
        var result = 0
        for (x in rowsRange) {
            for (y in columnsRange) {
                val cell = gameboard[y][x]
                result = if (condition(cell))  {
                    winCombination.add(Pair(y,x))
                    result + 1
                } else {
                    winCombination.clear()
                    0
                }
                if (result == WIN) return true
            }
            result = 0
        }
        return false
    }

    private fun checkVertical(): Boolean {
        var result = 0
        for (x in rowsRange) {
            for (y in columnsRange) {
                val cell = gameboard[x][y]
                result = if (condition(cell))  {
                    winCombination.add(Pair(x,y))
                    result + 1
                } else {
                    winCombination.clear()
                    0
                }
                if (result == WIN) return true
            }
            result = 0
        }
        return false
    }

//    fun checkDiagonalLeftBottomAndRightTop():Boolean {
//        var result1 = 0
//        var result2 = 0
//        for (x in 0 ..DATA.SIZE) {
//            for (y in 0 .. DATA.SIZE - x) {
//                val pointLB = Pair(x+y,y)
//                val pointRT = Pair(y,x+y)
//                val cell1 = gameboard[pointLB.first][pointLB.second]
//                result1 = if (cell1.state.value) result1 + 1 else 0
//                if (result1 == WIN) return true
//
//                val cell2 = gameboard[pointRT.first][pointRT.second]
//                result2 = if (cell2.state.value) result2 + 1 else 0
//                if (result2 == WIN) return true
//            }
//        }
//        return false
//    }
//
//
//    fun checkDiagonalRightBottom(): Boolean {
//        var result = 0
//        for (x in 0 until DATA.SIZE +1) {
//            for (y in 0 until DATA.SIZE -x+1) {
//                val pointRB = Pair(x+y, DATA.SIZE -y)
//                val cell = gameboard[pointRB.first][pointRB.second]
//                result = if (cell.state.value) result + 1 else 0
//                if (result == WIN) return true
//            }
//        }
//        return false
//    }
//
//    fun checkDiagonalLeftTop(): Boolean {
//        var result = 0
//        for (x in 0 until DATA.SIZE +1) {
//            for (y in x until DATA.SIZE +1) {
//                val pointLT = Pair(DATA.SIZE -y,y-x)
//                val cell = gameboard[pointLT.first][pointLT.second]
//                result = if (cell.state.value) result + 1 else 0
//                if (result == WIN) return true
//            }
//        }
//        return false
//    }


}