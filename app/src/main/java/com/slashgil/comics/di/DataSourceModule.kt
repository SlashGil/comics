package com.slashgil.comics.di

import com.slashgil.comics.data.remote.contract.ComicsRemoteDataSource
import com.slashgil.comics.data.remote.impl.ComicsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindComicsRemoteDataSource(
        remoteDataSourceImpl: ComicsRemoteDataSourceImpl
    ): ComicsRemoteDataSource
}
