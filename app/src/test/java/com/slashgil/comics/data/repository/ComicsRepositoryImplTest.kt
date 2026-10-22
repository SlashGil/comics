package com.slashgil.comics.data.repository

import com.slashgil.comics.data.local.contract.ComicsLocalDataSource
import com.slashgil.comics.data.remote.contract.ComicsRemoteDataSource
import com.slashgil.comics.data.repository.impl.ComicsRepositoryImpl
import com.slashgil.comics.data.remote.dto.ImageDto
import com.slashgil.comics.data.remote.dto.SuperheroDto
import com.slashgil.comics.data.remote.dto.SuperheroSearchResponseDto
import com.slashgil.comics.domain.model.Character
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ComicsRepositoryImplTest {

    private val remoteDataSource: ComicsRemoteDataSource = mockk()
    private val localDataSource: ComicsLocalDataSource = mockk(relaxed = true)
    private val repository = ComicsRepositoryImpl(remoteDataSource, localDataSource)

    @Test
    fun `getCharacters returns mapped domain models when api call succeeds`() = runTest {
        val heroDto = SuperheroDto(
            id = "620",
            name = "Spider-Man",
            image = ImageDto(url = "http://example.com/spiderman.jpg")
        )
        val response = SuperheroSearchResponseDto(
            response = "success",
            results = listOf(heroDto)
        )
        val domainHero = Character(id = "620", name = "Spider-Man", imageUrl = "http://example.com/spiderman.jpg")

        coEvery { localDataSource.searchCharacters("Spider", "") } returnsMany listOf(emptyList(), listOf(domainHero))
        coEvery { remoteDataSource.searchCharacters("Spider") } returns response

        val result = repository.getCharacters(query = "Spider", publisher = null)

        assertTrue(result.isSuccess)
        val characters = result.getOrNull()
        assertEquals("Spider-Man", characters?.first()?.name)
        coVerify(exactly = 1) { remoteDataSource.searchCharacters("Spider") }
    }

    @Test
    fun `getCharacterDetails returns character domain model when found`() = runTest {
        val heroDto = SuperheroDto(
            id = "620",
            name = "Spider-Man",
            image = ImageDto(url = "http://example.com/spiderman.jpg")
        )
        coEvery { localDataSource.getCharacterById("620") } returns null
        coEvery { remoteDataSource.getCharacterDetails("620") } returns heroDto

        val result = repository.getCharacterDetails("620")

        assertTrue(result.isSuccess)
        assertEquals("Spider-Man", result.getOrNull()?.name)
    }
}
