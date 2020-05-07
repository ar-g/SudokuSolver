package com.arg.sudokusolver.domain.repository.converter

interface SudokuConverter {
    fun fromString(sudoku: String): List<List<Int>>
    fun toString(board: List<List<Int>>): String
}