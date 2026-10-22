package com.slashgil.comics.domain.contract

import com.slashgil.comics.domain.model.Character

interface GetCharacterDetailsUseCase {
    suspend operator fun invoke(characterId: String): Result<Character>
}
