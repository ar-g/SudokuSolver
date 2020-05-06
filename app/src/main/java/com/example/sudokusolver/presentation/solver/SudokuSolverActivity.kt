package com.example.sudokusolver.presentation.solver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.sudokusolver.App
import com.example.sudokusolver.R
import com.example.sudokusolver.domain.model.SudokuModel
import com.example.sudokusolver.databinding.ActivitySudokuSolverBinding
import com.example.sudokusolver.domain.operations.SudokuSolutionStatus.Progress
import com.example.sudokusolver.domain.operations.SudokuSolutionStatus.Solution
import javax.inject.Inject

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

        viewModel.boardLiveData.observe(this, Observer { status ->
            when(status){
                is Progress -> binding.sudokuView.board = status.board
                is Solution -> {
                    binding.toolbar.title = getString(R.string.solution_found)
                    binding.sudokuView.board = status.board
                }
            }
        })

        if (savedInstanceState == null) {
            intent.getParcelableExtra<SudokuModel>(BUNDLE_KEY)?.let { model ->
                binding.toolbar.title = model.name
                binding.sudokuView.board = model.board

                viewModel.solveSudoku(model)
            }
        }
    }
}