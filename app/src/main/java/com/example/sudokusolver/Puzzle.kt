package com.example.sudokusolver

import com.google.gson.annotations.SerializedName

data class Puzzle(
	@field:SerializedName("puzzle")
	val puzzle: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Long
)
