package com.arg.sudokusolver.data.api.response

import com.google.gson.annotations.SerializedName

data class SudokuResponse(
    @field:SerializedName("puzzle")
    val puzzle: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Long
)
