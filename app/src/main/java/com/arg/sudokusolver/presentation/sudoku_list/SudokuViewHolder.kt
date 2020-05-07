package com.arg.sudokusolver.presentation.sudoku_list

import androidx.recyclerview.widget.RecyclerView
import com.arg.sudokusolver.databinding.SudokuItemBinding
import com.arg.sudokusolver.domain.model.SudokuModel

class SudokuViewHolder(private val sudokuItemBinding: SudokuItemBinding) :
    RecyclerView.ViewHolder(sudokuItemBinding.root) {

    fun bind(model: SudokuModel) {
        sudokuItemBinding.tvName.text = model.name
        sudokuItemBinding.sudokuView.board = model.board
    }
}