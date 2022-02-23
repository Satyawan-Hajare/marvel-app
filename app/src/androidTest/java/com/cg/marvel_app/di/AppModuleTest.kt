package com.cg.marvel_app.di

import android.content.Context
import androidx.room.Room
import com.cg.marvel_app.db.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModuleTest {
    @Provides
    @Named("marvel_database")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, MarvelDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}