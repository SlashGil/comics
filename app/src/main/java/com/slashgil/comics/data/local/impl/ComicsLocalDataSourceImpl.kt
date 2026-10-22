package com.slashgil.comics.data.local.impl

import com.slashgil.comics.data.local.contract.ComicsLocalDataSource
import com.slashgil.comics.data.local.dao.CharacterDao
import com.slashgil.comics.data.local.mapper.toDomain
import com.slashgil.comics.data.local.mapper.toEntity
import com.slashgil.comics.domain.model.Character
import javax.inject.Inject

class ComicsLocalDataSourceImpl @Inject constructor(
    private val characterDao: CharacterDao
) : ComicsLocalDataSource {

    override suspend fun searchCharacters(query: String, publisher: String): List<Character> {
        return characterDao.searchCharacters(query, publisher).map { it.toDomain() }
    }

    override suspend fun getCharacterById(id: String): Character? {
        return characterDao.getCharacterById(id)?.toDomain()
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        if (characters.isNotEmpty()) {
            characterDao.insertCharacters(characters.map { it.toEntity() })
        }
    }

    override suspend fun getCharacterCount(): Int {
        return characterDao.getCharacterCount()
    }
}
