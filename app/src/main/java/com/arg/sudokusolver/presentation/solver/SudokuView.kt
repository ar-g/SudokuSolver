package com.arg.sudokusolver.presentation.solver

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.arg.sudokusolver.R
import kotlin.math.min

class SudokuView : View {
    private lateinit var textPaint: TextPaint
    private val boardSize = 9
    private var cell = 0f
    private var halfCell = 0f
    private var boardTop = 0
    private var topPadding = 0f
    var board: List<List<Int>> = mutableListOf()
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

        setBackgroundColor(ContextCompat.getColor(context, R.color.black))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPaint.textSize = min(width, height).toFloat() / boardSize / 2
        boardTop = top
        cell = min(width, height).toFloat() / boardSize
        halfCell = cell / 2f
        topPadding = halfCell / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var curX = halfCell
        var curY = halfCell + topPadding

        for (row in board) {
            for (num in row) {
                if (num != 0) canvas.drawText(num.toString(), curX, curY, textPaint)
                curX += cell
            }
            curY += cell
            curX = halfCell
        }
    }
}