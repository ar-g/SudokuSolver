package com.example.sudokusolver.di

import android.content.Context
import androidx.room.Room
import com.example.sudokusolver.Api
import com.example.sudokusolver.SudokuDb
import com.example.sudokusolver.solver.di.SudokuSolverComponent
import com.example.sudokusolver.sudoku_list.di.SudokuListComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelBuilderModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun addSudokuListComponent(): SudokuListComponent.Factory
    fun addSudokuSolverComponent(): SudokuSolverComponent.Factory
}

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApi() =
        Retrofit.Builder()
            .baseUrl("https://sudoku-samples.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

    @Singleton
    @Provides
    fun provideDb(context: Context) =
        Room.databaseBuilder(context, SudokuDb::class.java, "sudoku-db")
            .build()

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
