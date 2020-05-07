package com.arg.sudokusolver.domain.operations

import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SudokuSolverImplTest {
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

    val sudokuSolver = SudokuSolverImpl()

    @Test
    fun validateCell() {
        //row
        assertFalse(
            sudokuSolver.validateCell(
                guess = 5, curI = 0, curJ = 1, board = board
            )
        )
        //column
        assertFalse(
            sudokuSolver.validateCell(
                guess = 2, curI = 0, curJ = 1, board = board
            )
        )
        //subarray
        assertFalse(
            sudokuSolver.validateCell(
                guess = 3, curI = 0, curJ = 1, board = board
            )
        )
        //valid
        assertTrue(
            sudokuSolver.validateCell(
                guess = 9, curI = 0, curJ = 1, board = board
            )
        )
    }

    @Test
    fun solve() = runBlocking {
        val progressAfterFirstGuess = Progress(
            listOf(
                listOf(5, 6, 2, 0, 0, 0, 0, 1, 0),
                listOf(0, 8, 7, 0, 9, 0, 3, 0, 6),
                listOf(0, 0, 3, 6, 7, 2, 0, 0, 0),
                listOf(0, 0, 0, 0, 1, 0, 9, 0, 0),
                listOf(3, 4, 1, 0, 0, 0, 2, 0, 0),
                listOf(0, 0, 0, 8, 0, 0, 0, 5, 0),
                listOf(0, 0, 0, 7, 0, 0, 0, 6, 8),
                listOf(8, 0, 0, 1, 0, 9, 0, 0, 3),
                listOf(0, 2, 0, 0, 0, 3, 5, 0, 9)
            )
        )
        val solution = Solution(
            listOf(
                listOf(5, 6, 9, 3, 4, 8, 7, 1, 2),
                listOf(2, 8, 7, 5, 9, 1, 3, 4, 6),
                listOf(4, 1, 3, 6, 7, 2, 8, 9, 5),
                listOf(6, 5, 8, 2, 1, 7, 9, 3, 4),
                listOf(3, 4, 1, 9, 5, 6, 2, 8, 7),
                listOf(7, 9, 2, 8, 3, 4, 6, 5, 1),
                listOf(9, 3, 4, 7, 2, 5, 1, 6, 8),
                listOf(8, 7, 5, 1, 6, 9, 4, 2, 3),
                listOf(1, 2, 6, 4, 8, 3, 5, 7, 9)
            )
        )
        val list = mutableListOf<SudokuSolutionStatus>()

        sudokuSolver.solve(board)
            .collect { list.add(it) }

        assertEquals(list[1], progressAfterFirstGuess)
        assertEquals(list.last(), solution)
    }
}