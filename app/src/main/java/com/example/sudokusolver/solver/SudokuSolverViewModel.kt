package com.example.sudokusolver.solver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuSolverViewModel @Inject constructor(
    private val sudokuSolver: SudokuSolver
) : ViewModel() {

    val boardLiveData: MutableLiveData<List<List<Int>>> =
        MutableLiveData()
    private var started = false//todo not nice assistedInject next iteration

    fun solveSudoku(board: List<List<Int>>, animate: Boolean = true) {
        if (!started) { //todo errors if any?
            started = true
            viewModelScope.launch {
                //todo threading of the flow?
                sudokuSolver.solve(board)
                    .onEach { if (animate) delay(1000) }
                    .collect { boardLiveData.value = it }
            }
        }
    }
}