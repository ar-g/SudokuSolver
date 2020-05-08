package com.arg.sudokusolver.presentation.solver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.arg.sudokusolver.App
import com.arg.sudokusolver.R
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.databinding.ActivitySudokuSolverBinding
import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus.Progress
import com.arg.sudokusolver.domain.operations.SudokuSolutionStatus.Solution
import javax.inject.Inject

class SudokuSolverActivity : AppCompatActivity() {
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
            when (status) {
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

    companion object {
        const val BUNDLE_KEY = "SudokuSolverActivity"

        fun navigateToSudokuSolver(context: Context, model: SudokuModel) {
            context.startActivity(
                Intent(context, SudokuSolverActivity::class.java)
                    .putExtra(BUNDLE_KEY, model)
            )
        }
    }
}