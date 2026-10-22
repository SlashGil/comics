package com.slashgil.comics.domain.impl

import com.slashgil.comics.data.repository.contract.ComicsRepositoryContract
import com.slashgil.comics.domain.contract.GetCharactersUseCase
import com.slashgil.comics.domain.model.Character
import javax.inject.Inject

class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: ComicsRepositoryContract
) : GetCharactersUseCase {
    override suspend fun invoke(
        query: String?,
        publisher: String?
    ): Result<List<Character>> {
        return repository.getCharacters(query = query, publisher = publisher)
    }
}
