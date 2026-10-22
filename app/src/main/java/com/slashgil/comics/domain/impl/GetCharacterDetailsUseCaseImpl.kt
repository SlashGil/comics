package com.slashgil.comics.domain.impl

import com.slashgil.comics.data.repository.contract.ComicsRepositoryContract
import com.slashgil.comics.domain.contract.GetCharacterDetailsUseCase
import com.slashgil.comics.domain.model.Character
import javax.inject.Inject

class GetCharacterDetailsUseCaseImpl @Inject constructor(
    private val repository: ComicsRepositoryContract
) : GetCharacterDetailsUseCase {
    override suspend fun invoke(characterId: String): Result<Character> {
        return repository.getCharacterDetails(characterId)
    }
}
