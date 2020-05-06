package com.example.sudokusolver.sudoku_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudokusolver.SudokuModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SudokuListViewModel @Inject constructor(
    private val sudokusOperations: SudokusOperations
) : ViewModel(){

    val sudokusLiveData =
        MutableLiveData<List<SudokuModel>>()

    init {
        viewModelScope.launch {
            sudokusLiveData.value = sudokusOperations.getAll()
        }
    }
}