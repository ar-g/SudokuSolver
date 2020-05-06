package com.example.sudokusolver.sudoku_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sudokusolver.App
import com.example.sudokusolver.solver.SudokuSolverActivity
import com.example.sudokusolver.databinding.ActivitySudokusBinding
import javax.inject.Inject

//todo Ask misha and sergei to review your tech test
//todo leave todo comments about siliness of some things
//todo leave some comments as it is for now

//todo how many entities you wanna have for each layer, ask kaushik

//todo progress,error etc.
//todo material paddings and so on
//todo dark mode?
//todo swipe refresh layout as a progress

//todo recycling? of sudoku views
//todo diffing

//todo SavedStateViewModelFactory
//todo check lalafo and babylon use cases communication
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

        sudokuAdapter = SudokuAdapter { model -> //todo extract navigation as sideeffect where it shoult sit in MVVM
            startActivity(
                Intent(this, SudokuSolverActivity::class.java)
                    .putExtra(SudokuSolverActivity.BUNDLE_KEY, model)
            )
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@SudokuListActivity)
            adapter = sudokuAdapter
        }

        viewModel.sudokusLiveData.observe(this, Observer { sudokus ->
            sudokuAdapter.setData(sudokus)
        })
    }
}