package com.example.sudokusolver.presentation.sudoku_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudokusolver.domain.model.Lce
import com.example.sudokusolver.domain.model.SudokuModel
import com.example.sudokusolver.domain.operations.SudokuListOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuListViewModel @Inject constructor(
    private val sudokuListOperations: SudokuListOperations
) : ViewModel() {

    val sudokusLiveData =
        MutableLiveData<Lce<List<SudokuModel>>>()

    init {
        viewModelScope.launch {
            sudokuListOperations.getAll()
                .flowOn(Dispatchers.IO)
                .collect {
                    viewModelScope.launch {
                        sudokusLiveData.value = it
                    }
                }
        }
    }
}