package com.arg.sudokusolver.domain.model

import android.os.Parcelable
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SudokuModel(
    val id: Long,
    val name: String,
    val board: List<List<Int>>
) : Parcelable

fun List<SudokuDbEnity>.mapToDbEntities(converter: SudokuConverter): List<SudokuModel> {
    return map { sudoku -> SudokuModel(sudoku.id, sudoku.name, converter.fromString(sudoku.board)) }
}