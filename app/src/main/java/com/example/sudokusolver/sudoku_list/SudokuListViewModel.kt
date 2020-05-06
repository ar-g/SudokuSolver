package com.example.sudokusolver.sudoku_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudokusolver.SudokuModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuListViewModel @Inject constructor(
    private val sudokusOperations: SudokusOperations
) : ViewModel(){

    val sudokusLiveData =
        MutableLiveData<List<SudokuModel>>()

    init {
        viewModelScope.launch {
            sudokusOperations.getAll()
                .collect { sudokusLiveData.value = it }
        }
    }
}