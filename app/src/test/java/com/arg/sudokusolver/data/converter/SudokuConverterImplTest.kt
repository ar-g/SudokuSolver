package com.arg.sudokusolver.data.converter

import com.arg.sudokusolver.data.repository.converter.SudokuConverterImpl
import org.junit.Assert.*
import org.junit.Test

internal class SudokuConverterImplTest {
    val board = listOf(
        listOf(5, 0, 0, 0, 0, 0, 0, 1, 0),
        listOf(0, 8, 7, 0, 9, 0, 3, 0, 6),
        listOf(0, 0, 3, 6, 7, 2, 0, 0, 0),
        listOf(0, 0, 0, 0, 1, 0, 9, 0, 0),
        listOf(3, 4, 1, 0, 0, 0, 2, 0, 0),
        listOf(0, 0, 0, 8, 0, 0, 0, 5, 0),
        listOf(0, 0, 0, 7, 0, 0, 0, 6, 8),
        listOf(8, 0, 0, 1, 0, 9, 0, 0, 3),
        listOf(0, 2, 0, 0, 0, 3, 5, 0, 9)
    )
    val sudoku =
"""5 0 0 0 0 0 0 1 0
0 8 7 0 9 0 3 0 6
0 0 3 6 7 2 0 0 0
0 0 0 0 1 0 9 0 0
3 4 1 0 0 0 2 0 0
0 0 0 8 0 0 0 5 0
0 0 0 7 0 0 0 6 8
8 0 0 1 0 9 0 0 3
0 2 0 0 0 3 5 0 9"""

    val sudokuCoverter = SudokuConverterImpl()

    @Test
    fun convertsStringToBoard() {
        assertEquals(board, sudokuCoverter.fromString(sudoku))
    }

    @Test
    fun convertsBoardToString() {
        assertEquals(sudoku, sudokuCoverter.toString(board))
    }
}