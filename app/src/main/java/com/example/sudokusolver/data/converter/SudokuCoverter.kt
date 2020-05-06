package com.example.sudokusolver.data.converter

import javax.inject.Inject

class SudokuCoverter @Inject constructor() {
    fun fromString(sudoku: String): List<List<Int>> {
        return sudoku
            .splitToSequence("\n")
            .map { row -> row.trimStart() }
            .map { row ->
                row.split(" ")
                    .map { cell -> cell.replace("_", "0").toInt() }
            }
            .toList()
    }

    fun toString(board: List<List<Int>>): String {
        return board.joinToString(separator = "\n") {
                row -> row.joinToString(separator = " ")
        }
    }
}