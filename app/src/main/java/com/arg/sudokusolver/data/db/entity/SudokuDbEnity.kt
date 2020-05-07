package com.arg.sudokusolver.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudoku")
data class SudokuDbEnity(
    @PrimaryKey val id: Long,
    val name: String,
    val board: String
)