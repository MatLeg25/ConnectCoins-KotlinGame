package com.example.connectcoins.utils

import android.util.Log
import com.example.connectcoins.data.models.Cell

class Validator(
    private val gameBoard: Array<Array<Cell>>,
    private val pointsToWin: Int
) {

    private val rowsRange = gameBoard.indices
    private val columnsRange = gameBoard[0].indices
    private val winCombination: MutableList<Pair<Int, Int>> = mutableListOf()
    private var playerId = ""

    private val condition: (cell: Cell) -> Boolean = { cell -> cell.playerId == playerId }

    fun checkWin(currentPlayerId: String): List<Pair<Int, Int>>? {
        playerId = currentPlayerId

        return if (checkHorizontal()) {
            Log.w("elox",">>>WIN by checkHorizontal")
            setWinCombination()
            winCombination
        }
        else if (checkVertical()) {
            Log.w("elox",">>>WIN by checkVertical")
            setWinCombination()
            winCombination
        }
        else if (checkDiagonalLtToRb()) {
            Log.w("elox",">>>WIN by checkDiagonalRightBottom")
            setWinCombination()
            winCombination
        }
        else if (checkDiagonalLbToRt()){
            Log.w("elox",">>>WIN by checkDiagonalLeftTop")
            setWinCombination()
            winCombination
        }  else null
    }

    private fun checkHorizontal(): Boolean {
        for (x in rowsRange) {
            winCombination.clear()
            for (y in columnsRange) {
                val cell = gameBoard[y][x]
                if (condition(cell)) winCombination.add(cell.cords)
                else winCombination.clear()
                if (winCombination.size == pointsToWin) return true
            }
        }
        return false
    }

    private fun checkVertical(): Boolean {
        for (x in rowsRange) {
            winCombination.clear()
            for (y in columnsRange) {
                val cell = gameBoard[x][y]
                if (condition(cell)) winCombination.add(cell.cords)
                else winCombination.clear()
                if (winCombination.size == pointsToWin) return true
            }
        }
        return false
    }

    private fun checkDiagonalLtToRb(): Boolean {
        val gameBoardT = transformMatrix()

        for (x in rowsRange) {
            for (y in columnsRange) {
                gameBoardT[x + rowsRange.last][y] = gameBoard[x][y]
            }
        }

        for (x in  0 until gameBoardT.size + 1) {
            winCombination.clear()
            for (y in  0 until gameBoardT.size + 1) {
                val cordX = x+y
                if (cordX < gameBoardT.size) {
                    val cell = gameBoardT[cordX][y]
                    if (condition(cell)) winCombination.add(cell.cords)
                    else winCombination.clear()
                    if (winCombination.size == pointsToWin) return true
                }
            }
        }

        return false
    }

    private fun checkDiagonalLbToRt(): Boolean {
        val gameBoardT = transformMatrix()

        for (x in rowsRange) {
            for (y in columnsRange) {
                gameBoardT[x][y] = gameBoard[x][y]
            }
        }

        for (x in gameBoardT.size - 1 downTo 0) {
            winCombination.clear()
            for (y in  0 until gameBoardT.size + 1) {
                val cordX = x-y
                if (cordX >= 0) {
                    val cell = gameBoardT[cordX][y]
                    if (condition(cell)) winCombination.add(cell.cords)
                    else winCombination.clear()
                    if (winCombination.size == pointsToWin) return true
                }
            }
        }

        return false
    }

    //allows to use single function to check all matrix diagonals
    private fun transformMatrix(): Array<Array<Cell>> {
        val rangeX = rowsRange.first .. (rowsRange.last * 2)
        val rangeY = columnsRange.first .. (columnsRange.last * 2)
        return rangeX.map { x->
            rangeY.map { y ->
                Cell(x, "O", Pair(x, y))
            }.toTypedArray()
        }.toTypedArray()
    }

    private fun setWinCombination() {
        Log.e("elox",">>>winCombination $winCombination")
        winCombination.forEach { cords ->
            gameBoard[cords.first][cords.second].isWin = true
        }
    }




}