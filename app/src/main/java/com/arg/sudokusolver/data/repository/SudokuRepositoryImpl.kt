package com.arg.sudokusolver.data.repository

import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.data.db.SudokuDao
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.data.db.entity.mapToDbEntities
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.model.mapToDbEntities
import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus
import com.arg.sudokusolver.domain.repository.SudokuRepository
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class SudokuRepositoryImpl @Inject constructor(
    private val converter: SudokuConverter,
    private val api: Api,
    private val sudokuDao: SudokuDao
) : SudokuRepository {
    override fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return flow {
            emit(Lce.Loading())
            try {
                val puzzles = api.puzzles()
                sudokuDao.deleteAll()
                sudokuDao.insertAll(puzzles.mapToDbEntities())
            } catch (e: Throwable) {
                emit(Lce.Error(e.message ?: ""))
            }
            emitAll(
                sudokuDao.getAll()
                    .map { it.mapToDbEntities(converter) }
                    .map { Lce.Content(it) }
            )
        }
    }

    override suspend fun update(id: Long, name: String, board: List<List<Int>>) {
        sudokuDao.update(SudokuDbEnity(id, name, converter.toString(board)))
    }
}