package com.cg.marvel_app.ui

import androidx.test.core.app.launchActivity
import androidx.test.filters.SmallTest

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun mainActivityTest(){
        val scenario = launchActivity<MainActivity>()
         scenario.onActivity {  }
    }

}
