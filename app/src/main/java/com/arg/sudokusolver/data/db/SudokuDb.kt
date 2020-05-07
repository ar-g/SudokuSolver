package com.arg.sudokusolver.data.db

import androidx.room.*
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity

@Database(entities = [SudokuDbEnity::class], version = 1, exportSchema = false)
abstract class SudokuDb : RoomDatabase() {
    abstract fun sudokuDao(): SudokuDao
}
