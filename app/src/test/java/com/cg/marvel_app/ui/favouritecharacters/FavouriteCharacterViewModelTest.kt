package com.cg.marvel_app.ui.favouritecharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cg.marvel_app.TestCoroutineRule
import com.cg.marvel_app.api.MarvelApi
import com.cg.marvel_app.api.repository.MarvelRepository
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.db.CharacterDao
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteCharacterViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: FavouriteCharacterViewModel

    @Mock
    lateinit var marvelRepository: MarvelRepository

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var characterDao: CharacterDao

    @Mock
    lateinit var characterResult: CharacterResult

    @Mock
    private lateinit var charFlow: Flow<List<CharacterResult>>


    @Before
    fun setUp() {
        marvelRepository = MarvelRepository(marvelApi, characterDao)
        viewModel = FavouriteCharacterViewModel(marvelRepository)
    }

    @Test
    fun addToFavourite() {
        runTest {
            //actual
            val addToFavourite = viewModel.addToFavourite(characterResult)
            //expected
            Truth.assertThat(addToFavourite)
        }
    }

    @Test
    fun removeFromFavourite() {
        runTest {
            //actual
            val removeFromFavourite = viewModel.removeFromFavourite(characterResult)
            //expected
            Truth.assertThat(removeFromFavourite)
        }
    }

    @Test
    fun getFavouriteCharacters() {
        runTest {
            Mockito.lenient().`when`(marvelRepository.getFavouriteCharacters())
                .thenReturn(charFlow)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}