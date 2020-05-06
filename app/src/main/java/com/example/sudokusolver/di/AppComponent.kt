package com.example.sudokusolver.di

import android.content.Context
import androidx.room.Room
import com.example.sudokusolver.data.api.Api
import com.example.sudokusolver.data.db.SudokuDb
import com.example.sudokusolver.presentation.solver.di.SudokuSolverComponent
import com.example.sudokusolver.domain.repository.SudokuRepository
import com.example.sudokusolver.data.repository.SudokuRepositoryImpl
import com.example.sudokusolver.presentation.sudoku_list.di.SudokuListComponent
import dagger.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class,
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


@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindsRepository(sudokuRepositoryImpl: SudokuRepositoryImpl) : SudokuRepository
}
