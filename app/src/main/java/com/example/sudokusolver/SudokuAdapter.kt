package com.example.sudokusolver

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sudokusolver.databinding.SudokuItemBinding

import com.example.sudokusolver.dummy.DummyContent.DummyItem

//todo diffing
data class SudokuModel(val name: String)

class SudokuAdapter(private val itemClick: (SudokuModel) -> Unit) :
    RecyclerView.Adapter<PostsViewHolder>() {

    private val data = mutableListOf<SudokuModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = SudokuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val postsViewHolder = PostsViewHolder(binding)

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

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
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

class PostsViewHolder(private val sudokuItemBinding: SudokuItemBinding)
    : RecyclerView.ViewHolder(sudokuItemBinding.root) {

    fun bind(model: SudokuModel) {
        sudokuItemBinding.tvName.text = model.name
    }
}