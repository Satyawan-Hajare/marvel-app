package com.cg.marvel_app.ui.characterseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.cachedIn
import com.cg.marvel_app.TestCoroutineRule
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.api.repository.MarvelRepository
import com.cg.marvel_app.db.CharacterDao
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SeriesViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: SeriesViewModel

    @Mock
    lateinit var marvelRepository: MarvelRepository

    @Mock
    lateinit var viewModelScope: CoroutineScope

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var characterDao: CharacterDao


    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        initMocks(this)
        marvelRepository = MarvelRepository(marvelApi, characterDao)
        viewModel = SeriesViewModel(marvelRepository)
    }

    @Test
    fun getCharacterSeries() {
        //given
        val characterId = "1"
        //actual
        val data = marvelRepository.getCharacterSeries(characterId).cachedIn(viewModelScope)
        //Expected
        Truth.assertThat(data)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}