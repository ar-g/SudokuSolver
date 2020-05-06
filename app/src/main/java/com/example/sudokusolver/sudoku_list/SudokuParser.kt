package com.example.sudokusolver.sudoku_list

import javax.inject.Inject

class SudokuParser @Inject constructor() {
    fun parse(sudoku: String): List<List<Int>> {
        return sudoku
            .splitToSequence("\n")
            .map { row -> row.trimStart() }
            .map { row ->
                row.split(" ")
                    .map { cell -> cell.replace("_", "0").toInt() }
            }
            .toList()
    }
}