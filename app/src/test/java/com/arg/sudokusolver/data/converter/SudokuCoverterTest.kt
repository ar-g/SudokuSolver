package com.arg.sudokusolver.data.converter

import org.junit.Assert.*
import org.junit.Test

//todo coroutine dispatchers and viewmodel rules
internal class SudokuCoverterTest {
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
    val sudoku = """5 _ _ _ _ _ _ 1 _
               _ 8 7 _ 9 _ 3 _ 6
               _ _ 3 6 7 2 _ _ _
               _ _ _ _ 1 _ 9 _ _
               3 4 1 _ _ _ 2 _ _
               _ _ _ 8 _ _ _ 5 _
               _ _ _ 7 _ _ _ 6 8
               8 _ _ 1 _ 9 _ _ 3
               _ 2 _ _ _ 3 5 _ 9"""

    @Test
    fun fromString() {
        val sudokuCoverter = SudokuCoverter()
        assertEquals(board, sudokuCoverter.fromString(sudoku))
    }

    @Test
    fun testToString() {

    }
}