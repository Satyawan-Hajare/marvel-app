package com.cg.marvel_app.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.launchFragmentInHiltContainer
import com.cg.marvel_app.ui.allcharacters.AllCharacterFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CharacterDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Inject
    @Named("marvel_database")
    lateinit var database: MarvelDatabase
    private lateinit var dao: CharacterDao
    private val characterResult = CharacterResult(
        "1",
        "Spider-Man",
        "Spider-Man is a superhero appearing in American comic books published by Marvel Comics.",
        CharacterResult.Thumbnail("https://media.istockphoto.com/pho", ".jpg"),
        CharacterResult.Comics("comic_available"),
        CharacterResult.Series("series_available")
    )

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.getCharacterDao()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun insertCharacter() = runBlocking {
        dao.insert(characterResult)
        val allCharacter = dao.getFavouriteCharacters()
        assertThat(allCharacter).isNotNull()
    }

    @Test
    fun deleteCharacter() = runBlocking {
        dao.insert(characterResult)
        val deleteCharacter = dao.delete(characterResult)
        assertThat(deleteCharacter).isNotEqualTo(characterResult)
    }

    @After
    fun teardown() {
        database.close()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @InternalCoroutinesApi
    @Test
    fun testInsertAndGetAllCharacter() = runBlocking {
        dao.insert(characterResult)
        val latch = CountDownLatch(1)
        val job = launch(Dispatchers.IO) {
            val firstItem = dao.getFavouriteCharacters().first()
            assertThat(firstItem)
            latch.countDown()
        }
        latch.await()
        job.cancel()
    }
}

