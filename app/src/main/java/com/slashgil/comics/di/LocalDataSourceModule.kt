package com.slashgil.comics.di

import com.slashgil.comics.data.local.contract.ComicsLocalDataSource
import com.slashgil.comics.data.local.impl.ComicsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindComicsLocalDataSource(
        impl: ComicsLocalDataSourceImpl
    ): ComicsLocalDataSource
}
