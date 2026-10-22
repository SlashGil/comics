package com.slashgil.comics.data.remote.contract

import com.slashgil.comics.data.remote.dto.SuperheroDto
import com.slashgil.comics.data.remote.dto.SuperheroSearchResponseDto

interface ComicsRemoteDataSource {
    suspend fun searchCharacters(name: String): SuperheroSearchResponseDto
    suspend fun getCharacterDetails(id: String): SuperheroDto
}
