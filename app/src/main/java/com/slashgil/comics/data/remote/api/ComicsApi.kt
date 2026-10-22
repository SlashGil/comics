package com.slashgil.comics.data.remote.api

import com.slashgil.comics.data.remote.dto.SuperheroDto
import com.slashgil.comics.data.remote.dto.SuperheroSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsApi {

    @GET("search/{name}")
    suspend fun searchCharacters(
        @Path("name") name: String
    ): SuperheroSearchResponseDto

    @GET("{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: String
    ): SuperheroDto
}
