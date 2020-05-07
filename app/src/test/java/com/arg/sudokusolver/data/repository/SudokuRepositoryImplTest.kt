package com.arg.sudokusolver.data.repository

import com.arg.sudokusolver.data.api.Api
import com.arg.sudokusolver.data.api.response.SudokuResponse
import com.arg.sudokusolver.data.db.SudokuDao
import com.arg.sudokusolver.data.db.entity.SudokuDbEnity
import com.arg.sudokusolver.domain.model.Lce
import com.arg.sudokusolver.domain.model.Lce.*
import com.arg.sudokusolver.domain.model.SudokuModel
import com.arg.sudokusolver.domain.repository.converter.SudokuConverter
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class SudokuRepositoryImplTest {
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

    @Test
    fun verifyBehaviourOfGetAll() = runBlocking {
        val fakeApi = spy(object : Api {
            override suspend fun puzzles(): List<SudokuResponse> {
               return emptyList()
            }
        })
        val repository = SudokuRepositoryImpl(mockConverter, fakeApi, mockDao)
        repository.getAll().collect {}

        verify(fakeApi, times(1)).puzzles()
        verify(mockDao, times(1)).deleteAll()
        verify(mockDao, times(1)).insertAll(any())
        verify(mockDao, times(1)).getAll()

        return@runBlocking Unit
    }


    @Test
    fun verifyProperStatesAreEmitted() = runBlocking {
        val fakeApi = spy(object : Api {
            override suspend fun puzzles(): List<SudokuResponse> {
                return emptyList()
            }
        })
        val repository = SudokuRepositoryImpl(mockConverter, fakeApi, mockDao)
        val list = mutableListOf<Lce<List<SudokuModel>>>()
        repository.getAll().collect { list.add(it) }

        assertEquals(
            mutableListOf<Lce<List<SudokuModel>>>(Loading(), Content(emptyList()), Content(emptyList())),
            list
        )
    }

    @Test
    fun verifyErrorStateIsEmitted() = runBlocking {
        val message = "interesting"
        val fakeApi = spy(object : Api {
            override suspend fun puzzles(): List<SudokuResponse> {
                throw Exception(message)
            }
        })
        val repository = SudokuRepositoryImpl(mockConverter, fakeApi, mockDao)
        val list = mutableListOf<Lce<List<SudokuModel>>>()
        repository.getAll().collect { list.add(it) }

        assertEquals(
            mutableListOf<Lce<List<SudokuModel>>>(Loading(), Error(message), Content(emptyList()), Content(emptyList())),
            list
        )
    }
}