package com.cg.marvel_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cg.marvel_app.data.CharacterResult

@Database(entities = [CharacterResult::class], version = 1)
abstract class MarvelDatabase: RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao

}