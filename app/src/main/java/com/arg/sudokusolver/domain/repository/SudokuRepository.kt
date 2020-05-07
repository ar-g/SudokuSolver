package com.arg.sudokusolver.domain.repository

import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import kotlinx.coroutines.flow.Flow

interface SudokuRepository {
    fun getAll(): Flow<Lce<List<SudokuModel>>>

    suspend fun update(id: Long, name: String, board: List<List<Int>>)
}