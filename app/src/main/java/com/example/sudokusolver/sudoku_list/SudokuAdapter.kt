package com.example.sudokusolver.sudoku_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.sudokusolver.SudokuModel
import com.example.sudokusolver.databinding.SudokuItemBinding

class SudokuAdapter(private val itemClick: (SudokuModel) -> Unit) :
    RecyclerView.Adapter<SudokuViewHolder>() {

    private val data = mutableListOf<SudokuModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SudokuViewHolder {
        val binding = SudokuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val postsViewHolder = SudokuViewHolder(binding)

        binding.root.setOnClickListener {
            val position = postsViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClick(data[position])
            }
        }

        return postsViewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SudokuViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(data: List<SudokuModel>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }
}

class SudokuViewHolder(private val sudokuItemBinding: SudokuItemBinding)
    : RecyclerView.ViewHolder(sudokuItemBinding.root) {

    fun bind(model: SudokuModel) {
        sudokuItemBinding.tvName.text = model.name
        sudokuItemBinding.sudokuView.board = model.board
    }
}