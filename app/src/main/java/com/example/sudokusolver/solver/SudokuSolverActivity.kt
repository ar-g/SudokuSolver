package com.example.sudokusolver.solver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.sudokusolver.App
import com.example.sudokusolver.SudokuModel
import com.example.sudokusolver.databinding.ActivitySudokuSolverBinding
import javax.inject.Inject

//todo room
//todo repository
//todo tests
//todo imporove design

//todo read about livedata courutines support
class SudokuSolverActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_KEY = "SudokuSolverActivity"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SudokuSolverViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent
            .addSudokuSolverComponent()
            .create()
            .inject(this)

        val binding = ActivitySudokuSolverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<SudokuModel>(BUNDLE_KEY)?.let { model ->
            binding.toolbar.title = model.name
            binding.sudokuView.board = model.board

            viewModel.boardLiveData.observe(this, Observer { board ->
                binding.sudokuView.board = board
            })
            viewModel.solveSudoku(model.board)
        }
    }
}