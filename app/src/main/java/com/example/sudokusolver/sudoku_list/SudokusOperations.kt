package com.example.sudokusolver.sudoku_list

import com.example.sudokusolver.Api
import com.example.sudokusolver.SudokuModel
import javax.inject.Inject

class SudokusOperations @Inject constructor(
    private val parser: SudokuParser,
    private val api: Api
){
    suspend fun getAll(): List<SudokuModel> {
        //todo repository relay
        val puzzles = api.puzzles()
        return puzzles.map { puzzle ->
            SudokuModel(
                puzzle.id,
                puzzle.name,
                parser.parse(puzzle.puzzle)
            )
        }
    }
}