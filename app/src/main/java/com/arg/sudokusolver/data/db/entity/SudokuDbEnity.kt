package com.arg.sudokusolver.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arg.sudokusolver.data.api.response.SudokuResponse

@Entity(tableName = "sudoku")
data class SudokuDbEnity(
    @PrimaryKey val id: Long,
    val name: String,
    val board: String
)

fun List<SudokuResponse>.mapToDbEntities(): List<SudokuDbEnity> {
    return map { puzzle -> SudokuDbEnity(puzzle.id, puzzle.name, puzzle.puzzle) }
}