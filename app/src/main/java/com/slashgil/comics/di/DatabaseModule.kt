package com.slashgil.comics.di

import android.content.Context
import androidx.room.Room
import com.slashgil.comics.data.local.dao.CharacterDao
import com.slashgil.comics.data.local.db.ComicsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideComicsDatabase(
        @ApplicationContext context: Context
    ): ComicsDatabase {
        return Room.databaseBuilder(
            context,
            ComicsDatabase::class.java,
            "comics_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: ComicsDatabase): CharacterDao {
        return database.characterDao()
    }
}
