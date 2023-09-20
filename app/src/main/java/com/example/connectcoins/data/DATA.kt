package com.example.connectcoins.data

import com.example.connectcoins.data.models.Cell

object DATA {

    val SIZE = 8
    val RANGE = 0 until SIZE

    val cells: Array<Array<Cell>> = arrayOf(
        arrayOf(
            Cell(0,"0", Pair(0,0)),
            Cell(1,"1", Pair(0,1)),
            Cell(2,"2", Pair(0,2)),
            Cell(3,"3", Pair(0,3)),
        ),
        arrayOf(
            Cell(4,"4", Pair(1,0)),
            Cell(5,"5", Pair(1,1)),
            Cell(6,"6", Pair(1,2)),
            Cell(7,"7", Pair(1,3)),
        ),
        arrayOf(
            Cell(8,"8", Pair(2,0)),
            Cell(9,"9", Pair(2,1)),
            Cell(10,"10", Pair(2,2)),
            Cell(11,"11", Pair(2,3)),
        ),
        arrayOf(
            Cell(12,"12", Pair(3,0)),
            Cell(13,"13", Pair(3,1)),
            Cell(14,"14", Pair(3,2)),
            Cell(15,"15", Pair(3,3)),
        )
    )

}