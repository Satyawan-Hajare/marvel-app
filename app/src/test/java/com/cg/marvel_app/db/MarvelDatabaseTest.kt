package com.cg.marvel_app.db

import android.content.Context
import androidx.room.Room
import com.cg.marvel_app.data.characters.CharacterResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MarvelDatabaseTest {
    @Mock
    private lateinit var marvelDatabase: MarvelDatabase

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var characterDao: CharacterDao

    private val characterResult = CharacterResult(
        "1",
        "Spider-Man",
        "Spider-Man is a superhero appearing in American comic books published by Marvel Comics.",
        CharacterResult.Thumbnail("https://media.istockphoto.com/pho", ".jpg"),
        CharacterResult.Comics("comic_available"),
        CharacterResult.Series("series_available")
    )

    @Test
    fun setUp() {
        initMocks(this)
        // initialize the db and dao variable
        marvelDatabase = Room.inMemoryDatabaseBuilder(context, MarvelDatabase::class.java).build()
        characterDao = marvelDatabase.getCharacterDao()
    }

    @After
    fun closeDb() {
        marvelDatabase.close()
    }

    @Test
    fun getCharacterDao(): Unit = runBlocking {
        //given
        characterDao.insert(characterResult)
        //actual
        val getFavouriteCharacters = characterDao.getFavouriteCharacters()
        //expected
        assertThat(getFavouriteCharacters)
    }

    @Test
    fun insertAndDelete(): Unit = runBlocking {
        //given
        characterDao.insert(characterResult)
        characterDao.getFavouriteCharacters()
        characterDao.delete(characterResult)
        //actual
        val resultsDelete = characterDao.getFavouriteCharacters()
        //expected
        assertThat(resultsDelete)
    }
}