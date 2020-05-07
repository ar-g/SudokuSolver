package com.arg.sudokusolver.domain.operations

import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SudokuListOperations @Inject constructor(
    private val sudokuRepository: SudokuRepository
) {
    fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return sudokuRepository.getAll()
    }
}