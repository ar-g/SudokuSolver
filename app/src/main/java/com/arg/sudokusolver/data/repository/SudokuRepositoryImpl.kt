package com.arg.sudokusolver.data.repository

import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.data.db.SudokuDao
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.data.db.entity.mapToDbEntities
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.model.mapToDbEntities
import com.arg.sudokusolver.domain.repository.SudokuRepository
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SudokuRepositoryImpl @Inject constructor(
    private val converter: SudokuConverter,
    private val api: Api,
    private val sudokuDao: SudokuDao,
    private val dispatcherIO: CoroutineDispatcher
) : SudokuRepository {
    override fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return channelFlow {
            val networkLock = CompletableDeferred<Unit>()
            launch(dispatcherIO) {
                sudokuDao.getAll()
                    .map { it.mapToDbEntities(converter) }
                    .map { Lce.Content(it) }
                    .collect {
                        send(it)
                        networkLock.complete(Unit)
                    }
            }
            //wait for the first emission of db
            networkLock.await()
            send(Lce.Loading())
            try {
                val puzzles = api.puzzles()
                sudokuDao.deleteAll()
                sudokuDao.insertAll(puzzles.mapToDbEntities())
            } catch (e: Throwable) {
                send(Lce.Error(e.message ?: ""))
            }
        }
    }

    override suspend fun update(id: Long, name: String, board: List<List<Int>>) {
        sudokuDao.update(SudokuDbEnity(id, name, converter.toString(board)))
    }
}