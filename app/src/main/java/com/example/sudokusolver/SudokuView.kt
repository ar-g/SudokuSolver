package com.example.sudokusolver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.ui.unit.sp

// red and green or light and dark green

/**
 * View to display sudoku game
 */
class SudokuView : View {
    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    private var sudokuState: List<List<Int>> = mutableListOf()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
            textSize = 40.sp.value
            color = ContextCompat.getColor(context, R.color.green2)
            textWidth = measureText("Something")
            textHeight = fontMetrics.bottom
        }

        sudokuState = SudokuParser().parse(
            """5 _ _ _ _ _ _ 1 _
               _ 8 7 _ 9 _ 3 _ 6
               _ _ 3 6 7 2 _ _ _
               _ _ _ _ 1 _ 9 _ _
               3 4 1 _ _ _ 2 _ _
               _ _ _ 8 _ _ _ 5 _
               _ _ _ 7 _ _ _ 6 8
               8 _ _ 1 _ 9 _ _ 3
               _ 2 _ _ _ 3 5 _ _"""
        )


        Thread {
            SudokuSolver().solve(sudokuState) { board ->
                Thread.sleep(10)
                Handler(context.mainLooper).post({
                    sudokuState = board
                    invalidate()
                })
            }

        }.start()
    }

    //check how are the new views in android are built
    //cache rectangles
    //precalculate number and rectangle positions
    //don't take into consideration paddings, mrgins and other attributes
    //take into account font size

    //todo differentiate guess by color
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setBackgroundColor(ContextCompat.getColor(context, R.color.black))

        val cell = width / 9f
        val halfCell = cell / 2f
        var curX = halfCell
        var curY = halfCell
        for (row in sudokuState) {
            for (num in row) {
                if (num != 0) {
                    canvas.drawText(num.toString(), curX, curY, textPaint)
                }
                curX += cell
            }
            curY += cell
            curX = halfCell
        }
    }
}

class SudokuParser() {
    fun parse(sudoku: String): List<List<Int>> {
        return sudoku
            .splitToSequence("\n")
            .map { row -> row.trimStart() }
            .map { row ->
                row.split(" ")
                    .map { cell -> cell.replace("_", "0").toInt() }
            }
            .toList()
    }
}

typealias OnSudokuBoardChanged = (board: List<List<Int>>) -> Unit

//todo ObservableList vs Callback vs Flow or courutines?
//todo Cancelling sudoku solving via courutines?
class SudokuSolver() {

    //todo threadSafety if many of them will be called copy ObservableList scheme
    private var sudokuBoardChangedListener: OnSudokuBoardChanged? = null

    fun solve(board: List<List<Int>>, sudokuBoardChangedListener: OnSudokuBoardChanged) {
        this.sudokuBoardChangedListener = sudokuBoardChangedListener

        //todo waste
        partiallySolve(0,0, board.map { it.toMutableList() }.toMutableList())

        this.sudokuBoardChangedListener = null
    }

    //todo try to use stack as an option

    //iterate through list of numbers for specific cell
    //once valid write into the list and go to the next empty cell// todo caution regarding modifying original list
    //if no valid number for the cell go back to previous cell and try another number

    fun partiallySolve(curI: Int, curJ: Int, board: MutableList<MutableList<Int>>): Boolean {
        if (curI > board.lastIndex) {
            return true//end of array and valid return true
        }

        val curNum = board[curI][curJ]

        println("Num $curNum i $curI j $curJ")

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
        //validate row
        if (curNum == 0){
            return false
        }

        for ((j, num) in board[curI].withIndex()) {
            if (j != curJ && num == curNum) return false
        }
        //validate column
        for (i in 0..board.lastIndex) {
            if (i != curI && board[i][curJ] == curNum) return false
        }
        //validate subarray
        val subI = curI / 3 * 3 //todo: looks silly
        val subJ = curJ / 3 * 3
        for (i in subI..subI + 2) {
            for (j in subJ..subJ + 2) {
                if (i != curI && j != curJ && board[i][j] == curNum) return false
            }
        }
        return true
    }
}
