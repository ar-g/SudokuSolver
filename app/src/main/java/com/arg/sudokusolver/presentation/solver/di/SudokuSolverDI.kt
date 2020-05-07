package com.arg.sudokusolver.presentation.solver.di

import androidx.lifecycle.ViewModel
import com.arg.sudokusolver.di.ViewModelKey
import com.arg.sudokusolver.presentation.solver.SudokuSolverActivity
import com.arg.sudokusolver.presentation.solver.SudokuSolverViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [SudokuSolverModule::class])
interface SudokuSolverComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SudokuSolverComponent
    }

    fun inject(activity: SudokuSolverActivity)
}

@Module
abstract class SudokuSolverModule {
    @Binds
    @IntoMap
    @ViewModelKey(SudokuSolverViewModel::class)
    abstract fun bindViewModel(viewmodel: SudokuSolverViewModel): ViewModel
}