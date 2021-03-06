package com.arg.sudokusolver.presentation.sudoku_list.di

import androidx.lifecycle.ViewModel
import com.arg.sudokusolver.di.ViewModelKey
import com.arg.sudokusolver.presentation.sudoku_list.SudokuListActivity
import com.arg.sudokusolver.presentation.sudoku_list.SudokuListViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [SudokuListModule::class])
interface SudokuListComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SudokuListComponent
    }

    fun inject(activity: SudokuListActivity)
}

@Module
abstract class SudokuListModule {
    @Binds
    @IntoMap
    @ViewModelKey(SudokuListViewModel::class)
    abstract fun bindViewModel(viewmodel: SudokuListViewModel): ViewModel
}