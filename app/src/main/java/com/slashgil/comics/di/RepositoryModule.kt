package com.slashgil.comics.di

import com.slashgil.comics.data.repository.contract.ComicsRepositoryContract
import com.slashgil.comics.data.repository.impl.ComicsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindComicsRepository(
        comicsRepositoryImpl: ComicsRepositoryImpl
    ): ComicsRepositoryContract
}
