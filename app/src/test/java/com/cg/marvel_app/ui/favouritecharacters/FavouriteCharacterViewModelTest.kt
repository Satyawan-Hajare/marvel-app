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
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavouriteCharacterViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    lateinit var viewModel: FavouriteCharacterViewModel

    @Mock
    lateinit var marvelRepository: MarvelRepository

    @Mock
    lateinit var marvelApi: MarvelApi

    @Mock
    lateinit var characterDao: CharacterDao

    @Mock
    lateinit var characterResult: CharacterResult

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        marvelRepository = MarvelRepository(marvelApi, characterDao)
        viewModel = FavouriteCharacterViewModel(marvelRepository)
    }

    @Test
    fun addToFavourite() {
        testDispatcher.runBlockingTest {
            //actual
            val addToFavourite = viewModel.addToFavourite(characterResult)
            //expected
            Truth.assertThat(addToFavourite)
        }
    }

    @Test
    fun removeFromFavourite() {
        testDispatcher.runBlockingTest {
            //actual
            val removeFromFavourite = viewModel.removeFromFavourite(characterResult)
            //expected
            Truth.assertThat(removeFromFavourite)
        }
    }

//    @Test
//    fun getFavouriteCharacters() {
//        testDispatcher.runBlockingTest {
//            //actual
//            val getFavouriteCharacters = marvelRepository.getFavouriteCharacters().asLiveData()
//            //expected
//            Truth.assertThat(getFavouriteCharacters)
//        }
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}