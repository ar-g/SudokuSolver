package com.arg.sudokusolver.domain.operations

import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import javax.inject.Inject

class SudokuSolverImpl @Inject constructor() : SudokuSolver {
    override fun solve(board: List<List<Int>>) = flow {
        partiallySolve(0, 0, mutableCopyOfBoard(board)) { status ->
            emit(status)
        }
    }

    private suspend fun partiallySolve(
        curI: Int,
        curJ: Int,
        board: MutableList<MutableList<Int>>,
        callback: suspend (board: SudokuSolutionStatus) -> Unit
    ): Boolean {
        yield()

        if (curI > board.lastIndex) {
            callback.invoke(Solution(copyOfBoard(board)))
            return true
        }

        val curNum = board[curI][curJ]

        val nextI = if (curJ == board.lastIndex) curI + 1 else curI
        val nextJ = if (curJ == board.lastIndex) 0 else curJ + 1

        if (curNum != 0) {
            return partiallySolve(nextI, nextJ, board, callback)
        }

        if (curNum == 0) {
            for (guess in 1..9) {
                if (validateCell(guess, curI, curJ, board)) {

                    board[curI][curJ] = guess

                    callback.invoke(Progress(copyOfBoard(board)))

                    if (partiallySolve(nextI, nextJ, board, callback)) {
                        return true
                    }
                }
            }
        }

        board[curI][curJ] = 0

        return false
    }

    fun validateCell(guess: Int, curI: Int, curJ: Int, board: List<List<Int>>): Boolean {
        if (guess == 0) {
            return false
        }
        //validate row
        for ((j, num) in board[curI].withIndex()) {
            if (j != curJ && num == guess) return false
        }
        //validate column
        for (i in 0..board.lastIndex) {
            if (i != curI && board[i][curJ] == guess) return false
        }
        //validate subarray
        val subI = curI / 3 * 3
        val subJ = curJ / 3 * 3
        for (i in subI..subI + 2) {
            for (j in subJ..subJ + 2) {
                if (i != curI && j != curJ && board[i][j] == guess) return false
            }
        }
        return true
    }

    private fun copyOfBoard(board: MutableList<MutableList<Int>>): List<List<Int>> {
        return board.map { it.toList() }.toList()
    }

    private fun mutableCopyOfBoard(board: List<List<Int>>): MutableList<MutableList<Int>> {
        return board.map { it.toMutableList() }.toMutableList()
    }
}

sealed class SudokuSolutionStatus {
    data class Progress(val board: List<List<Int>>) : SudokuSolutionStatus()
    data class Solution(val board: List<List<Int>>) : SudokuSolutionStatus()
}