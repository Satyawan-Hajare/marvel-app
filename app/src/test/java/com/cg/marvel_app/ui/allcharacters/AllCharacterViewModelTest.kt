package com.cg.marvel_app.ui.allcharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.cachedIn
import com.cg.marvel_app.TestCoroutineRule
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.api.repository.MarvelRepository
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.db.CharacterDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllCharacterViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    lateinit var viewModel: AllCharacterViewModel

    @Mock
    lateinit var marvelRepository: MarvelRepository

    @Mock
    lateinit var viewModelScope: CoroutineScope

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var characterDao: CharacterDao

    @Mock
    lateinit var characterResult: CharacterResult

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        initMocks(this)
        marvelRepository = MarvelRepository(marvelApi, characterDao)
        viewModel = AllCharacterViewModel(marvelRepository)
    }

    @Test
    fun getSearchQuery() {
        testDispatcher.runBlockingTest {
            val searchQuery = MutableStateFlow("")
            assertThat(searchQuery)
        }

    }

    @Test
    fun getSearchResult() {
        testDispatcher.runBlockingTest {
            //given
            val mutableStateFlow = MutableStateFlow("")
            //actual
            mutableStateFlow.mapLatest {
                val data = marvelRepository.searchCharacter(it).cachedIn(viewModelScope)
                //Expected
                assertThat(data)
            }
        }

    }

    @Test
    fun addToFavourite() {
        testDispatcher.runBlockingTest {
            //actual
            val addToFavourite = viewModel.addToFavourite(characterResult)
            //expected
            assertThat(addToFavourite)
        }
    }

    @Test
    fun removeFromFavourite() {
        testDispatcher.runBlockingTest {
            //actual
            val removeFromFavourite = viewModel.removeFromFavourite(characterResult)
            //excepted
            assertThat(removeFromFavourite)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}