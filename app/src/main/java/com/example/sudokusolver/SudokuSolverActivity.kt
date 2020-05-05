package com.example.sudokusolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.sudokusolver.databinding.ActivitySudokuSolverBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

//todo room
//todo repository
//todo dagger/koin?
//todo tests
//todo imporove design

//todo read about livedata courutines support
class SudokuSolverActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_KEY = "SudokuSolverActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySudokuSolverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<SudokuModel>(BUNDLE_KEY)?.let { model ->
            binding.toolbar.title = model.name
            binding.sudokuView.board = model.sudoku

            val viewModel: SudokuSolverViewModel by viewModels {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        return SudokuSolverViewModel(model.sudoku) as T
                    }
                }
            }

            viewModel.boardLiveData.observe(this, Observer { board ->
                binding.sudokuView.board = board
            })
        }
    }
}

//todo inject parcelable model?
class SudokuSolverViewModel(
    private val board: List<List<Int>>
) : ViewModel() {

    private val sudokuSolver: SudokuSolver = SudokuSolver()
    val boardLiveData: MutableLiveData<List<List<Int>>> = MutableLiveData()

    init {
        solveSudoku(board)
    }

    private fun solveSudoku(board: List<List<Int>>, animate: Boolean = true) {
        viewModelScope.launch {
            sudokuSolver.solve(board)
                .onEach { if (animate) delay(1000) }
                .collect{ boardLiveData.value = it }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("OnCleared called")

        viewModelScope.cancel()
    }
}