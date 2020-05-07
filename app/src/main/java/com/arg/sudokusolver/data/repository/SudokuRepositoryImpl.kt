package com.arg.sudokusolver.data.repository

import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.data.db.SudokuDb
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.data.converter.SudokuCoverter
import com.arg.sudokusolver.domain.repository.SudokuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SudokuRepositoryImpl @Inject constructor(
    private val converter: SudokuCoverter,
    private val api: Api,
    private val db: SudokuDb
) : SudokuRepository {
    override fun getAll(): Flow<Lce<List<SudokuModel>>> {
        return flow {
            emit(Lce.Loading())
            coroutineScope {
                try {
                    launch(Dispatchers.IO) {
                        val puzzles = api.puzzles()
                        db.sudokuDao().deleteAll()
                        db.sudokuDao().insertAll(puzzles.map { puzzle ->
                            SudokuDbEnity(
                                puzzle.id,
                                puzzle.name,
                                puzzle.puzzle
                            )
                        })
                    }
                } catch (e: Exception) {
                    emit(Lce.Error(e.message ?: ""))
                }
                emitAll(
                    db.sudokuDao().getAll().map {
                        it.map { sudoku ->
                            SudokuModel(
                                sudoku.id,
                                sudoku.name,
                                converter.fromString(sudoku.board)
                            )
                        }
                    }.map { Lce.Content(it) }
                )
            }
        }
    }

    override suspend fun update(id: Long, name: String, board: List<List<Int>>) {
        db.sudokuDao().update(
            SudokuDbEnity(
                id,
                name,
                converter.toString(board)
            )
        )
    }
}