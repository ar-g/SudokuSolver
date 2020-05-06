package com.example.sudokusolver.sudoku_list

import com.example.sudokusolver.Api
import com.example.sudokusolver.SudokuDb
import com.example.sudokusolver.SudokuEntity
import com.example.sudokusolver.SudokuModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SudokusOperations @Inject constructor(
    private val parser: SudokuParser,
    private val api: Api,
    private val db: SudokuDb
) {

    //todo repository relay
    fun getAll(): Flow<List<SudokuModel>> = flow {
        //todo threading
        emit(db.sudokuDao().getAll().map { sudoku ->
            SudokuModel(
                sudoku.id,
                sudoku.name,
                parser.parse(sudoku.board)
            )
        })

        val puzzles = api.puzzles()

        db.sudokuDao().deleteAll()
        db.sudokuDao().insertAll(puzzles.map { puzzle ->
            SudokuEntity(
                puzzle.id,
                puzzle.name,
                puzzle.puzzle
            )
        })

        emit(puzzles.map { puzzle ->
            SudokuModel(
                puzzle.id,
                puzzle.name,
                parser.parse(puzzle.puzzle)
            )
        })
    }
}