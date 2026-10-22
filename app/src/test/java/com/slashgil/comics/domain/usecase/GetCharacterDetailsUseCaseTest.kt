package com.slashgil.comics.domain.usecase

import com.slashgil.comics.domain.contract.GetCharacterDetailsUseCase
import com.slashgil.comics.domain.impl.GetCharacterDetailsUseCaseImpl
import com.slashgil.comics.domain.model.Character
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetCharacterDetailsUseCaseTest {

    private val fakeRepository = FakeComicsRepository()
    private val useCase: GetCharacterDetailsUseCase = GetCharacterDetailsUseCaseImpl(fakeRepository)

    @Test
    fun `invoke returns character details when repository succeeds`() = runTest {
        val mockCharacter = Character(
            id = "620",
            name = "Spider-Man",
            imageUrl = "http://example.com/spiderman.jpg"
        )
        fakeRepository.characterDetailsResult = Result.success(mockCharacter)

        val result = useCase("620")

        assertTrue(result.isSuccess)
        assertEquals(mockCharacter, result.getOrNull())
    }
}
