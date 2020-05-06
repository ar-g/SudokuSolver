package com.example.sudokusolver

import androidx.room.*

//todo consistent naming for entities in different layers
@Entity(tableName = "sudoku")
data class SudokuEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val board: String
)

@Dao
interface SudokuDao{
    @Query("SELECT * FROM sudoku")
    suspend fun getAll(): List<SudokuEntity>

    @Insert
    suspend fun insertAll(sudokuList: List<SudokuEntity>)

    @Update
    suspend fun update(sudokuEntity: SudokuEntity)

    @Query("DELETE FROM sudoku")
    suspend fun deleteAll()
}

@Database(entities = [SudokuEntity::class], version = 1)
abstract class SudokuDb: RoomDatabase(){
    abstract fun sudokuDao() : SudokuDao
}
