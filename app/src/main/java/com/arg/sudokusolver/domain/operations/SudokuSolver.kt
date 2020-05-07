package com.arg.sudokusolver.domain.operations

import kotlinx.coroutines.flow.Flow

interface SudokuSolver {
    fun solve(board: List<List<Int>>): Flow<SudokuSolutionStatus>
}