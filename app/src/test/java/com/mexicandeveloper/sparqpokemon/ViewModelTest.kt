package com.mexicandeveloper.sparqpokemon

import app.cash.turbine.test
import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon
import com.mexicandeveloper.sparqpokemon.domain.usecase.GetPokemonUseCase
import com.mexicandeveloper.sparqpokemon.feature_pokemon.PokemonViewModel
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm.PokemonUIState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    private val fakePokemon = listOf(
        Pokemon(
            name = "bulbasaur", imageUrl = "",
            url = "TODO()"
        ),
        Pokemon(name = "pikachu", imageUrl = "", url = "")
    )
    private val getPokemon: GetPokemonUseCase = mockk()
    private lateinit var viewModel: PokemonViewModel


    @Test
    fun loadPokemon_success_updatesUiState() = runTest {

        // Given
        every {
            getPokemon(0)
        } returns flowOf(fakePokemon)

        // When
        viewModel = PokemonViewModel(getPokemon)

        // Then
        viewModel.uiState.test {

            // 1️⃣ Initial state
            assert(awaitItem() is PokemonUIState.Loading)

            // 2️⃣ After data loads
            val success = awaitItem()

            assertTrue(success is PokemonUIState.Success)

            success as PokemonUIState.Success

            assertEquals(2, success.pokemon.size)
            assertEquals("bulbasaur", success.pokemon[0].name)

            cancelAndIgnoreRemainingEvents()
        }
    }

}