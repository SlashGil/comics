package com.slashgil.comics.domain.contract

import com.slashgil.comics.domain.model.Character

interface GetCharactersUseCase {
    suspend operator fun invoke(
        query: String? = null,
        publisher: String? = null
    ): Result<List<Character>>
}
