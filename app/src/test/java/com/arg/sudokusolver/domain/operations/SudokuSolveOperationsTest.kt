package com.arg.sudokusolver.domain.operations

import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus.*
import com.arg.sudokusolver.domain.repository.SudokuRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SudokuSolveOperationsTest{
    @Test
    fun solveSodukuSavesSolutionToRepository() = runBlocking {
        val sudokuSolver = mock<SudokuSolver> {
            on { solve(any()) } doReturn flow { emit(Solution(listOf(listOf<Int>()))) }
        }
        val sudokuRepository = mock<SudokuRepository>()
        val sudokuSolveOperations = SudokuSolveOperations(sudokuSolver, sudokuRepository, Dispatchers.Default)

        sudokuSolveOperations.solveSudoku(SudokuModel(1, "", listOf(listOf<Int>())))
            .collect()

        verify(sudokuRepository, times(1)).update(1, "", listOf(listOf<Int>()))
    }
}