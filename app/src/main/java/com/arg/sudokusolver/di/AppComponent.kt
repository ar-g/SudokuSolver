package com.arg.sudokusolver.di

import android.content.Context
import androidx.room.Room
import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import com.arg.sudokusolver.data.repository.converter.SudokuConverterImpl
import com.arg.sudokusolver.data.db.SudokuDb
import com.arg.sudokusolver.presentation.solver.di.SudokuSolverComponent
import com.arg.sudokusolver.domain.repository.SudokuRepository
import com.arg.sudokusolver.data.repository.SudokuRepositoryImpl
import com.arg.sudokusolver.presentation.sudoku_list.di.SudokuListComponent
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
    fun provideSudokuDao(db: SudokuDb) =
        db.sudokuDao()

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}


@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindsRepository(sudokuRepositoryImpl: SudokuRepositoryImpl): SudokuRepository

    @Singleton
    @Binds
    abstract fun bindsConverter(sudokuRepositoryImpl: SudokuConverterImpl): SudokuConverter
}
