package com.arg.sudokusolver.presentation.sudoku_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.operations.SudokuListOperations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuListViewModel @Inject constructor(
    private val sudokuListOperations: SudokuListOperations,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val sudokusLiveData =
        MutableLiveData<Lce<List<SudokuModel>>>()

    init {
        viewModelScope.launch {
            sudokuListOperations.getAll()
                .flowOn(ioDispatcher)
                .collect { sudokusLiveData.value = it }
        }
    }
}