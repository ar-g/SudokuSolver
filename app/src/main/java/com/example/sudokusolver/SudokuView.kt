package com.example.sudokusolver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
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
            """_ 5 _ _ 2 9 _ _ _
      _ 8 7 _ 1 4 _ _ _
      7 _ 8 _ _ 3 _ _ _
      _ 6 _ 1 5 _ _ 4 8
      _ _ _ 2 6 _ 1 _ 9
      _ 2 1 9 _ _ _ _ _
      _ _ _ 8 _ _ _ 9 4
      3 _ _ _ _ _ 8 _ 6"""
        )


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

class SudokuSolver(){
    fun solve(sudoku: List<List<Int>>){
        for ((i, row) in sudoku.withIndex()) {
            for ((j, num) in row.withIndex()) {
                validate(num, i, j, sudoku)
            }
        }
    }

    private fun validate(curNum: Int, curI: Int, curJ: Int, sudoku: List<List<Int>>): Boolean {
        //validate row
        for ((i, num) in sudoku[curI].withIndex()) {
            if (i != curI && num == curNum) return false
        }
        //validate column
        for (j in 0..sudoku.lastIndex){
            if (j != curJ && num == sudoku[j][curJ])
        }
    }
}