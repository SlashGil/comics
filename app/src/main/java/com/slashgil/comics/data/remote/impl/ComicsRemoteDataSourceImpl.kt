package com.slashgil.comics.data.remote.impl

import com.slashgil.comics.data.remote.api.ComicsApi
import com.slashgil.comics.data.remote.contract.ComicsRemoteDataSource
import com.slashgil.comics.data.remote.dto.SuperheroDto
import com.slashgil.comics.data.remote.dto.SuperheroSearchResponseDto
import javax.inject.Inject

class ComicsRemoteDataSourceImpl @Inject constructor(
    private val api: ComicsApi
) : ComicsRemoteDataSource {

    override suspend fun searchCharacters(name: String): SuperheroSearchResponseDto {
        return api.searchCharacters(name)
    }

    override suspend fun getCharacterDetails(id: String): SuperheroDto {
        return api.getCharacterDetails(id)
    }
}
