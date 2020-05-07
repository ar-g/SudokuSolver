package com.arg.sudokusolver.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import kotlinx.coroutines.flow.Flow

@Dao
interface SudokuDao {
    @Query("SELECT * FROM sudoku")
    fun getAll(): Flow<List<SudokuDbEnity>>

    @Insert
    suspend fun insertAll(sudokuList: List<SudokuDbEnity>)

    @Update
    suspend fun update(sudokuDbEnity: SudokuDbEnity)

    @Query("DELETE FROM sudoku")
    suspend fun deleteAll()
}