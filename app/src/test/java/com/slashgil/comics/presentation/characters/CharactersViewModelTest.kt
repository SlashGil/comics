package com.slashgil.comics.presentation.characters

import com.slashgil.comics.domain.impl.GetCharacterDetailsUseCaseImpl
import com.slashgil.comics.domain.impl.GetCharactersUseCaseImpl
import com.slashgil.comics.domain.model.Character
import com.slashgil.comics.domain.usecase.FakeComicsRepository
import com.slashgil.comics.presentation.characters.impl.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeComicsRepository()

    private val getCharactersUseCase = GetCharactersUseCaseImpl(fakeRepository)
    private val getCharacterDetailsUseCase = GetCharacterDetailsUseCaseImpl(fakeRepository)

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads characters successfully`() = runTest {
        val mockCharacters = listOf(
            Character(
                id = "620",
                name = "Spider-Man",
                imageUrl = "http://example.com/spiderman.jpg"
            )
        )
        fakeRepository.charactersResult = Result.success(mockCharacters)

        viewModel = CharactersViewModel(
            getCharactersUseCase,
            getCharacterDetailsUseCase
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(mockCharacters, state.characters)
    }

    @Test
    fun `onPublisherSelected filters character list`() = runTest {
        val mockCharacters = listOf(
            Character(
                id = "620",
                name = "Spider-Man",
                imageUrl = "http://example.com/spiderman.jpg"
            )
        )
        fakeRepository.charactersResult = Result.success(mockCharacters)

        viewModel = CharactersViewModel(
            getCharactersUseCase,
            getCharacterDetailsUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onPublisherSelected("DC Comics")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("DC Comics", state.selectedPublisher)
    }
}
