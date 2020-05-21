package com.arg.sudokusolver.domain.operations

import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.repository.SudokuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SudokuListOperations @Inject constructor(
    private val sudokuRepository: SudokuRepository,
    private val dispatcherIO: CoroutineDispatcher
) {
    fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return sudokuRepository.getAll()
            .flowOn(dispatcherIO)
    }
}