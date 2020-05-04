package com.example.sudokusolver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class SudokuView : View {
    private lateinit var textPaint: TextPaint
    private var cell: Float = 0f
    private var halfCell: Float = 0f
    private var boardTop: Int = 0
    private var topPadding = 16.dpToPx()
    var sudokuState: List<List<Int>> = mutableListOf()
    set(value) {
        field = value
        invalidate()
    }

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
        minimumHeight
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
            color = ContextCompat.getColor(context, R.color.green2)
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

        setBackgroundColor(ContextCompat.getColor(context, R.color.black))

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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPaint.textSize = min(width, height) / 9f / 2
        boardTop = top
        cell = min(width, height) / 9f
        halfCell = cell / 2f
    }

    //check how are the new views in android are built
    //cache rectangles
    //precalculate number and rectangle positions
    //don't take into consideration paddings, mrgins and other attributes
    //take into account font size

    //todo Improve differentiate guess by color
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var curX = halfCell
        var curY = halfCell + topPadding

        for (row in sudokuState) {
            for (num in row) {
                if (num != 0) canvas.drawText(num.toString(), curX, curY, textPaint)
                curX += cell
            }
            curY += cell
            curX = halfCell
        }
    }
}