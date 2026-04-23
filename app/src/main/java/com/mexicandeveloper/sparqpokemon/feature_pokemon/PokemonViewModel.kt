package com.mexicandeveloper.sparqpokemon.feature_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexicandeveloper.sparqpokemon.domain.usecase.GetPokemonUseCase
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm.PokemonUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            getPokemon(0)
                .onStart {
                    _uiState.update {
                        it.copy(
                            loading = true,
                            error = null
                        )
                    }
                }
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = e.message
                        )
                    }
                }
                .collect { pokemon ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            pokemon = pokemon,
                            offset = pokemon.size
                        )
                    }
                }
        }
    }

    fun loadNextPage() {
        if (
            _uiState.value.loadingMore ||
            _uiState.value.endReached
        ) return
        viewModelScope.launch {
            getPokemon(
                _uiState.value.offset
            )
                .onStart {
                    _uiState.update {
                        it.copy(
                            loadingMore = true
                        )
                    }
                }
                .catch {
                    _uiState.update {oldState->
                        oldState.copy(
                            loadingMore = false,
                            error = it.message ?: "unknown Error"
                        )
                    }
                }
                .collect { newItems ->
                    _uiState.update { oldState->
                        oldState.copy(
                            loadingMore = false,
                            pokemon =
                                oldState.pokemon + newItems,
                            offset =
                                oldState.offset +
                                        newItems.size,
                            endReached =
                                newItems.size < 10
                        )
                    }
                }
        }
    }

    fun retry() {
        loadPokemon()
    }
}