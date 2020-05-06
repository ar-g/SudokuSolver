package com.example.sudokusolver

import retrofit2.http.GET

interface Api {
    @GET("/puzzles")
    suspend fun puzzles() : List<Puzzle>
}