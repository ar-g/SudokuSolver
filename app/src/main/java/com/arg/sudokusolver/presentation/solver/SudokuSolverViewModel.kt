package com.arg.sudokusolver.presentation.solver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus
import com.arg.sudokusolver.domain.operations.SudokuSolveOperations
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SudokuSolverViewModel @Inject constructor(
    private val sudokuSolveOperations: SudokuSolveOperations
) : ViewModel() {

    val boardLiveData: MutableLiveData<SudokuSolutionStatus> =
        MutableLiveData()

    fun solveSudoku(model: SudokuModel) {
        viewModelScope.launch {
            sudokuSolveOperations.solveSudoku(model)
                .flowOn(Dispatchers.IO)
                .collect { boardLiveData.value = it }
        }
    }
}