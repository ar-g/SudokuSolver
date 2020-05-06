package com.example.sudokusolver.domain.operations

import com.example.sudokusolver.domain.model.Lce
import com.example.sudokusolver.domain.model.SudokuModel
import com.example.sudokusolver.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SudokuListOperations @Inject constructor(
    private val sudokuRepository: SudokuRepository
) {
    fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return sudokuRepository.getAll()
    }
}