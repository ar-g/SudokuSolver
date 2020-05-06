package com.example.sudokusolver

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SudokuModel(
    val id: Long,
    val name: String,
    val board: List<List<Int>>
) : Parcelable