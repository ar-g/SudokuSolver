package com.arg.sudokusolver.presentation.sudoku_list

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arg.sudokusolver.App
import com.arg.sudokusolver.databinding.ActivitySudokusBinding
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.presentation.solver.SudokuSolverActivity.Companion.navigateToSudokuSolver
import javax.inject.Inject

class SudokuListActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SudokuListViewModel> { factory }

    private lateinit var sudokuAdapter: SudokuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent
            .addSudokuListComponent()
            .create()
            .inject(this)

        val binding = ActivitySudokusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sudokuAdapter = SudokuAdapter(onItemClick = { model ->
            navigateToSudokuSolver(this, model)
        })
        binding.srl.isEnabled = false
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@SudokuListActivity)
            adapter = sudokuAdapter
        }

        viewModel.sudokusLiveData.observe(this, Observer { lce ->
            when (lce) {
                is Lce.Loading -> binding.srl.isRefreshing = true
                is Lce.Content -> {
                    sudokuAdapter.setData(lce.data)
                    binding.srl.isRefreshing = false
                }
                is Lce.Error -> {
                    binding.srl.isRefreshing = false
                    Toast.makeText(this, lce.errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}