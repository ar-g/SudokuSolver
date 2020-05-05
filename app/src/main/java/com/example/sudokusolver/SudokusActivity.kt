package com.example.sudokusolver

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sudokusolver.databinding.ActivitySudokusBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.Exception

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
class SudokusActivity : AppCompatActivity() {

    private lateinit var sudokuAdapter: SudokuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySudokusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo improve? only UI tests
        sudokuAdapter = SudokuAdapter { model ->
            val intent = Intent(this, SudokuSolverActivity::class.java)
                .putExtra(SudokuSolverActivity.BUNDLE_KEY, model)
            startActivity(intent)
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@SudokusActivity)
            adapter = sudokuAdapter
        }

        val model: SudokusViewModel by viewModels()

        model.getPuzzles().observe(this, Observer { puzzles ->
            val models = puzzles.map {
                SudokuModel(
                    it.id, it.name, SudokuParser().parse(it.puzzle)
                )
            }

            sudokuAdapter.setData(models)
        })
    }
}

class SudokusViewModel : ViewModel(){
    fun getPuzzles() = liveData(Dispatchers.IO){
        try {
            emit(Api.create().puzzles())
        } catch (e: Exception){
            Log.e("sudoku", e.message, e)
        }
    }
}



interface Api {
    @GET("/puzzles")
    suspend fun puzzles() : List<Puzzle>

    companion object {
        fun create(): Api {
            return Retrofit.Builder()
                .baseUrl("https://sudoku-samples.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}

//todo objects and wiring




























