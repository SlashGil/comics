package com.slashgil.comics.data.local.contract

import com.slashgil.comics.domain.model.Character

interface ComicsLocalDataSource {
    suspend fun searchCharacters(query: String, publisher: String): List<Character>
    suspend fun getCharacterById(id: String): Character?
    suspend fun saveCharacters(characters: List<Character>)
    suspend fun getCharacterCount(): Int
}
