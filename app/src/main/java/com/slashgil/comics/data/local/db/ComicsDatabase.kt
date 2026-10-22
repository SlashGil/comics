package com.slashgil.comics.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slashgil.comics.data.local.dao.CharacterDao
import com.slashgil.comics.data.local.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class ComicsDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
