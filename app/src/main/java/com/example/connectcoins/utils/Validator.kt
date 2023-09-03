package com.example.connectcoins.utils

import android.util.Log
import com.example.connectcoins.data.DATA

class Validator {

    val WIN = 4

    fun checkWin(): Boolean {
return false
//        return if (checkHorizontal()) {
//            Log.w("elox",">>>WIN by checkHorizontal")
//            true
//        }
////        else if (checkVertical()) {
////            Log.w("elox",">>>WIN by checkVertical")
////            true
////        }
//        else false
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

//    fun checkHorizontal(): Boolean {
//        var result = 0
//        for (x in DATA.cells.indices) {
//            for (y in DATA.cells[0].indices) {
//                val cell = DATA.cells[y][x]
//                result = if (cell.state.value) result + 1 else 0
//                if (result == WIN) return true
//            }
//            result = 0
//        }
//        return false
//    }
//
//    fun checkVertical(): Boolean {
//        var result = 0
//        for (x in DATA.cells.indices) {
//            for (y in DATA.cells[0].indices) {
//                val cell = DATA.cells[x][y]
//                result = if (cell.state.value) result + 1 else 0
//                if (result == WIN) return true
//            }
//            result = 0
//        }
//        return false
//    }
//
//    fun checkDiagonalLeftBottomAndRightTop():Boolean {
//        var result1 = 0
//        var result2 = 0
//        for (x in 0 ..DATA.SIZE) {
//            for (y in 0 .. DATA.SIZE - x) {
//                val pointLB = Pair(x+y,y)
//                val pointRT = Pair(y,x+y)
//                val cell1 = DATA.cells[pointLB.first][pointLB.second]
//                result1 = if (cell1.state.value) result1 + 1 else 0
//                if (result1 == WIN) return true
//
//                val cell2 = DATA.cells[pointRT.first][pointRT.second]
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
//                val cell = DATA.cells[pointRB.first][pointRB.second]
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
//                val cell = DATA.cells[pointLT.first][pointLT.second]
//                result = if (cell.state.value) result + 1 else 0
//                if (result == WIN) return true
//            }
//        }
//        return false
//    }


}