package com.example.sudokusolver

import kotlinx.coroutines.GlobalScope

//todo rewrite in courutines fashion
//todo rewrite in flow fashion



typealias OnSudokuBoardChanged = (board: List<List<Int>>) -> Unit
//todo ObservableList vs Callback vs Flow or courutines?
//todo Cancelling sudoku solving via courutines?

//todo invalid sudoku handling is omitted for now
class SudokuSolver() {

    //todo threadSafety if many of them will be called copy ObservableList scheme
    private var sudokuBoardChangedListener: OnSudokuBoardChanged? = null

    suspend fun solve(board: List<List<Int>>, sudokuBoardChangedListener: OnSudokuBoardChanged) {
        this.sudokuBoardChangedListener = sudokuBoardChangedListener

        //todo waste
        partiallySolve(0,0, board.map { it.toMutableList() }.toMutableList())

        this.sudokuBoardChangedListener = null
    }


    //todo caution regarding modifying original list
    suspend fun partiallySolve(curI: Int, curJ: Int, board: MutableList<MutableList<Int>>): Boolean {
        if (curI > board.lastIndex) {
            return true//end of array and valid return true
        }

        val curNum = board[curI][curJ]

        //println("Num $curNum i $curI j $curJ")

        val nextI = if (curJ == board.lastIndex) curI + 1 else curI
        val nextJ = if (curJ == board.lastIndex) 0 else curJ + 1

        if (curNum != 0){
            return partiallySolve(nextI, nextJ, board)
        }

        if (curNum == 0) {
            for (guess in 1..9) {
                if (validate(guess, curI, curJ, board)) {

                    board[curI][curJ] = guess

                    //todo extra copies for thread safety and mutability?
                    sudokuBoardChangedListener?.invoke(board.map { it.toList() }.toList())

                    if(partiallySolve(nextI, nextJ, board)){
                        return true
                    }
                }
            }
        }

        //println("Backtracking")

        board[curI][curJ] = 0

        return false
    }

    fun validate(curNum: Int, curI: Int, curJ: Int, board: MutableList<MutableList<Int>>): Boolean {
        //todo write test cases on this validation logic
        //todo check copies and references, encourage immutability where necessary
        //todo use arrays for better speed
        if (curNum == 0){
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