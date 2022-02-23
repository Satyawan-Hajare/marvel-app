package com.cg.marvel_app.ui.allcharacters

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.cg.marvel_app.R
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AllCharacterFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        Thread.sleep(2000)
    }
    @Mock
    lateinit var character: CharacterResult
    @Test
    fun fragmentLaunch() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AllCharacterFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
    }

}
