package com.mexicandeveloper.sparqpokemon.feature_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexicandeveloper.sparqpokemon.domain.usecase.GetPokemonUseCase
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm.PokemonUIState
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonViewModel
@Inject constructor(
    private val getPokemon: GetPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonUIState>(PokemonUIState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    init {
        loadPokemon()
    }

    fun loadPokemon() {

        viewModelScope.launch {

            _uiState.value = PokemonUIState.Loading
            getPokemon(0).catch { e ->

                    _uiState.value = PokemonUIState.Error(
                        e.message ?: "Failed"
                    )
                }.collect { pokemon ->
                    _uiState.value = PokemonUIState.Success(
                        pokemon = pokemon
                    )
                }
        }
    }

    fun loadNextPage() {

        val current = _uiState.value
        if (current !is PokemonUIState.Success) return
        if (current.loadingMore || current.endReached) return

        viewModelScope.launch {

            _uiState.value = current.copy(
                loadingMore = true
            )

            getPokemon(
                current.pokemon.size
            ).catch {
                    _uiState.value = current.copy(
                        loadingMore = false
                    )
                    emitEvent(
                        UiEvent.ShowSnackbar(
                            "Pagination failed"
                        )
                    )
                }.collect { newItems ->
                    _uiState.value = current.copy(
                        pokemon = current.pokemon + newItems,
                        loadingMore = false,
                        endReached = newItems.size < 10
                    )
                }
        }
    }

    fun retry() {
        loadPokemon()
    }

    private fun emitEvent(
        event: UiEvent
    ) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}