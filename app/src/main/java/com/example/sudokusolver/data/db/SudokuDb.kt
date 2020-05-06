package com.example.sudokusolver.data.db

import androidx.room.*
import com.example.sudokusolver.data.db.entity.SudokuDbEnity

@Database(entities = [SudokuDbEnity::class], version = 1, exportSchema = false)
abstract class SudokuDb: RoomDatabase(){
    abstract fun sudokuDao() : SudokuDao
}
