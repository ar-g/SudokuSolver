package com.example.sudokusolver.domain.repository

import com.example.sudokusolver.domain.model.Lce
import com.example.sudokusolver.domain.model.SudokuModel
import kotlinx.coroutines.flow.Flow

interface SudokuRepository {
    fun getAll(): Flow<Lce<List<SudokuModel>>>

    suspend fun update(id:Long, name:String, board:List<List<Int>>)
}