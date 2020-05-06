package com.example.sudokusolver.solver

import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

//todo rewrite in courutines fashion
//todo rewrite in flow fashion
//todo ObservableList vs Callback vs Flow or courutines?
//todo Cancelling sudoku solving via courutines?

//todo invalid sudoku handling is omitted for now

//todo caution regarding modifying original list
//todo extra copies for thread safety and mutability?

//todo write test cases on this validation logic
//todo check copies and references, encourage immutability where necessary
//todo use arrays for better speed

typealias OnSudokuBoardChanged = suspend (board: List<List<Int>>) -> Unit

class SudokuSolver @Inject constructor() {

    fun solve(board: List<List<Int>>) = flow {
        val mutableCopyOfBoard = board.map { row -> row.toMutableList() }.toMutableList()
        partiallySolve(0, 0, mutableCopyOfBoard) {
            emit(it)
        }
    }

    private suspend fun partiallySolve(
        curI: Int,
        curJ: Int,
        board: MutableList<MutableList<Int>>,
        callback: OnSudokuBoardChanged
    ): Boolean {
        if (curI > board.lastIndex) {
            return true
        }

        Timber.d("Running")

        val curNum = board[curI][curJ]

        val nextI = if (curJ == board.lastIndex) curI + 1 else curI
        val nextJ = if (curJ == board.lastIndex) 0 else curJ + 1

        if (curNum != 0) {
            return partiallySolve(nextI, nextJ, board, callback)
        }

        if (curNum == 0) {
            for (guess in 1..9) {
                if (validate(guess, curI, curJ, board)) {

                    board[curI][curJ] = guess

                    callback.invoke(board.map { it.toList() }.toList())

                    if (partiallySolve(nextI, nextJ, board, callback)) {
                        return true
                    }
                }
            }
        }

        board[curI][curJ] = 0

        return false
    }

    private fun validate(curNum: Int, curI: Int, curJ: Int, board: MutableList<MutableList<Int>>): Boolean {
        if (curNum == 0) {
            return false
        }
        //validate row
        for ((j, num) in board[curI].withIndex()) {
            if (j != curJ && num == curNum) return false
        }
        //validate column
        for (i in 0..board.lastIndex) {
            if (i != curI && board[i][curJ] == curNum) return false
        }
        //validate subarray
        val subI = curI / 3 * 3
        val subJ = curJ / 3 * 3
        for (i in subI..subI + 2) {
            for (j in subJ..subJ + 2) {
                if (i != curI && j != curJ && board[i][j] == curNum) return false
            }
        }
        return true
    }
}