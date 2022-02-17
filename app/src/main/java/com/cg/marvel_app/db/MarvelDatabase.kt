package com.cg.marvel_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cg.marvel_app.data.characters.CharacterResult
import com.cg.marvel_app.utils.Constants

@Database(entities = [CharacterResult::class], version = Constants.DbConstant.DB_VERSION)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao

}