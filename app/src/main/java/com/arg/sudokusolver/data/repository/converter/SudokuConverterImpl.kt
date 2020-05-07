package com.arg.sudokusolver.data.repository.converter

import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import javax.inject.Inject

class SudokuConverterImpl @Inject constructor() : SudokuConverter {
    override fun fromString(sudoku: String): List<List<Int>> {
        return sudoku
            .splitToSequence("\n")
            .map { row -> row.trimStart() }
            .map { row ->
                row.split(" ")
                    .map { cell -> cell.replace("_", "0").toInt() }
            }
            .toList()
    }

    override fun toString(board: List<List<Int>>): String {
        return board.joinToString(separator = "\n") { row ->
            row.joinToString(separator = " ")
        }
    }
}