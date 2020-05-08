package com.arg.sudokusolver.presentation.sudoku_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.databinding.SudokuItemBinding

internal class SudokuAdapter(private val onItemClick: (SudokuModel) -> Unit) :
    RecyclerView.Adapter<SudokuViewHolder>() {

    private val data = mutableListOf<SudokuModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SudokuViewHolder {
        val binding = SudokuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val postsViewHolder = SudokuViewHolder(binding)

        binding.root.setOnClickListener {
            val position = postsViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(data[position])
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