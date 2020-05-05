package com.example.sudokusolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.sudokusolver.databinding.ActivitySudokuSolverBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

//todo read about livedata courutines support
class SudokuSolverActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_KEY = "SudokuSolverActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySudokuSolverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: SudokuSolverViewModel by viewModels()

        intent.getParcelableExtra<SudokuModel>(BUNDLE_KEY)?.let { model ->
            binding.toolbar.title = model.name
            binding.sudokuView.sudokuState = model.sudoku
            viewModel.solveSudoku(model) { list ->
                Timber.d("On vm side")
                logCurrentThread()
                binding.sudokuView.sudokuState = list
            }
        }
    }
}

class SudokuSolverViewModel(
) : ViewModel(){

    private val sudokuSolver: SudokuSolver = SudokuSolver()

    fun solveSudoku(model: SudokuModel, progress: (List<List<Int>>) -> Unit){
        viewModelScope.launch {
            Timber.d("viewModel launch")
            logCurrentThread()
            launch(Dispatchers.Default){
                Timber.d("Default launch")
                logCurrentThread()

                sudokuSolver.solve(model.sudoku) { board ->
                    Timber.d("Board update launch")
                    logCurrentThread()
                    launch(Dispatchers.Main){
                        progress.invoke(board)
                    }
                }
            }
        }
    }
}

fun logCurrentThread(){
    Timber.d("Current thread${Thread.currentThread().name}")
}