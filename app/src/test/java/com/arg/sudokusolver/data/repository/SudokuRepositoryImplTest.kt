package com.arg.sudokusolver.data.repository

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.data.api.response.SudokuResponse
import com.arg.sudokusolver.data.db.SudokuDao
import com.arg.sudokusolver.data.db.SudokuDb
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException

//todo verify order of execution of methods
class SudokuRepositoryImplTest {
    val board = listOf(
        listOf(5, 0, 0, 0, 0, 0, 0, 1, 0),
        listOf(0, 8, 7, 0, 9, 0, 3, 0, 6),
        listOf(0, 0, 3, 6, 7, 2, 0, 0, 0),
        listOf(0, 0, 0, 0, 1, 0, 9, 0, 0),
        listOf(3, 4, 1, 0, 0, 0, 2, 0, 0),
        listOf(0, 0, 0, 8, 0, 0, 0, 5, 0),
        listOf(0, 0, 0, 7, 0, 0, 0, 6, 8),
        listOf(8, 0, 0, 1, 0, 9, 0, 0, 3),
        listOf(0, 2, 0, 0, 0, 3, 5, 0, 9)
    )


    object fakeApi : Api {
        override suspend fun puzzles(): List<SudokuResponse> {
            delay(100)
            throw Exception("Interesting")
        }
    }

    val mockConverter = mock<SudokuConverter> {
        on { fromString(any()) } doReturn emptyList()
        on { toString(any()) } doReturn ""
    }

    val mockDao = mock<SudokuDao> {
        on { getAll() } doReturn flow {
            emit(emptyList<SudokuDbEnity>())
            emit(emptyList<SudokuDbEnity>())
        }
    }

    val repository = SudokuRepositoryImpl(
        mockConverter,
        fakeApi,
        mockDao
    )

    //todo check all events in proper way delivered
    @Test
    fun getAll() = runBlocking {
        val list = mutableListOf<Lce<List<SudokuModel>>>()
        repository.getAll().collect {
            list.add(it)
        }
        println(list)
    }
}