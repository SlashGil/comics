package com.slashgil.comics.data.repository.contract

import com.slashgil.comics.domain.model.Character

interface ComicsRepositoryContract {
    suspend fun getCharacters(
        query: String? = null,
        publisher: String? = null
    ): Result<List<Character>>

    suspend fun getCharacterDetails(characterId: String): Result<Character>
}
