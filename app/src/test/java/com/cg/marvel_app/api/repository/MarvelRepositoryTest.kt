package com.cg.marvel_app.api.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.cg.marvel_app.TestCoroutineRule
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.data.characters.CharacterResponse
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.data.comic.ComicResponse
import com.cg.marvel_app.data.series.SeriesResponse
import com.cg.marvel_app.db.CharacterDao
import com.cg.marvel_app.ui.allcharacters.AllCharacterViewModel
import com.cg.marvel_app.ui.allcharacters.CharacterPagingSource
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: AllCharacterViewModel

    @Inject
    lateinit var marvelRepository: MarvelRepository

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var characterDao: CharacterDao

    @Mock
    lateinit var characterResult: CharacterResult

    @Mock
    lateinit var comicResponse: ComicResponse

    @Mock
    lateinit var seriesResponse: SeriesResponse

    @Mock
    lateinit var characterResponse: CharacterResponse

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var pagingSource: CharacterPagingSource

    @Mock
    lateinit var results: List<CharacterResult>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        marvelRepository = MarvelRepository(marvelApi, characterDao)
        viewModel = AllCharacterViewModel(marvelRepository)
        pagingSource = CharacterPagingSource(marvelApi, "")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `character getAllCharacter`() =
        testDispatcher.runBlockingTest {
            lenient().`when`(marvelApi.getAllCharacters(0, 1)).thenReturn(characterResponse)
        }

    @Test
    fun searchCharacter() {
        testDispatcher.runBlockingTest {
            lenient().`when`(marvelApi.searchCharacter("sa", 0, 1)).thenReturn(characterResponse)
        }
    }

    @Test
    fun getCharacterComics() {
        testDispatcher.runBlockingTest {
            lenient().`when`(marvelApi.getCharacterComics("1", 0, 1)).thenReturn(comicResponse)
        }
    }

    @Test
    fun getCharacterSeries() {
        testDispatcher.runBlockingTest {
            lenient().`when`(marvelApi.getCharacterSeries("1", 0, 1)).thenReturn(seriesResponse)
        }
    }

    @Test
    fun addCharacterToFavourite() {
        testDispatcher.runBlockingTest {
            var addToFavourite = marvelRepository.addCharacterToFavourite(characterResult)
            assertThat(addToFavourite)
        }
    }

    @Test
    fun removeCharacterFromFavourite() {
        testDispatcher.runBlockingTest {
            var removeCharacterFromFavourite =
                marvelRepository.removeCharacterFromFavourite(characterResult)
            assertThat(removeCharacterFromFavourite)
        }
    }

}