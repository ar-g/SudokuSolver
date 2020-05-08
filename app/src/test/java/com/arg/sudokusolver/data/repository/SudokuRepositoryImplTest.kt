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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class SudokuRepositoryImplTest {
    val mockConverter = mock<SudokuConverter> {
        on { fromString(any()) } doReturn emptyList()
        on { toString(any()) } doReturn ""
    }

    val fakeDao = object : SudokuDao {
        private val channel = ConflatedBroadcastChannel<List<SudokuDbEnity>>()

        override fun getAll(): Flow<List<SudokuDbEnity>> {
            return channel.asFlow().apply {
                channel.offer(emptyList())
            }
        }

        override suspend fun insertAll(sudokuList: List<SudokuDbEnity>) {
            channel.offer(emptyList())
        }

        override suspend fun update(sudokuDbEnity: SudokuDbEnity) {}
        override suspend fun deleteAll() {}
    }

    @Test
    fun verifyProperStatesAreEmitted() = runBlocking {
        //given
        val fakeApi = spy(object : Api {
            override suspend fun puzzles(): List<SudokuResponse> {
                return emptyList()
            }
        })
        val repository = createRepository(fakeApi)
        val result = mutableListOf<Lce<List<SudokuModel>>>()

        //when
        repository.getAll().take(3).collect { result.add(it) }

        //then
        val expectedStates = mutableListOf<Lce<List<SudokuModel>>>(
            Content(emptyList()),
            Loading(),
            Content(emptyList())
        )
        assertEquals(expectedStates, result)
    }

    @Test
    fun verifyErrorStateIsEmitted() = runBlocking {
        //given
        val message = "interesting"
        val fakeApi = spy(object : Api {
            override suspend fun puzzles(): List<SudokuResponse> {
                throw Exception(message)
            }
        })
        val repository = createRepository(fakeApi)
        val result = mutableListOf<Lce<List<SudokuModel>>>()

        //when
        repository.getAll().take(3).collect { result.add(it) }

        //then
        val expectedStates = mutableListOf<Lce<List<SudokuModel>>>(
            Content(emptyList()),
            Loading(),
            Error(message)
        )
        assertEquals(expectedStates, result)
    }

    private fun createRepository(fakeApi: Api) =
        SudokuRepositoryImpl(mockConverter, fakeApi, fakeDao, Dispatchers.Default)
}