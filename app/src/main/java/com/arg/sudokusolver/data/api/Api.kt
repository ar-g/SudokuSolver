package com.arg.sudokusolver.data.api

import com.arg.sudokusolver.data.api.response.SudokuResponse
import retrofit2.http.GET

interface Api {
    @GET("/puzzles")
    suspend fun puzzles(): List<SudokuResponse>
}