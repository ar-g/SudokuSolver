package com.example.sudokusolver.domain.operations

import com.example.sudokusolver.domain.model.SudokuModel
import com.example.sudokusolver.domain.repository.SudokuRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SudokuSolveOperations @Inject constructor(
    private val sudokuSolver: SudokuSolver,
    private val sudokuRepository: SudokuRepository
){
    fun solveSudoku(model: SudokuModel, animate: Boolean = true): Flow<SudokuSolutionStatus> {
        return sudokuSolver.solve(model.board)
            .onEach { status ->
                if (status is SudokuSolutionStatus.Solution) {
                    sudokuRepository.update(model.id, model.name, status.board)
                }
                if (animate) delay(30)
            }
    }
}