package com.mexicandeveloper.sparqpokemon.feature_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexicandeveloper.sparqpokemon.domain.usecase.GetPokemonUseCase
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonIntent
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonPartialState
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonReducer.reduce
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase
) : ViewModel() {

    private val intents =
        MutableSharedFlow<PokemonIntent>()

    private val _state =
        MutableStateFlow(
            PokemonState()
        )

    val state =
        _state.asStateFlow()

    init {
        processIntents()
    }

    fun sendIntent(
        intent: PokemonIntent
    ) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun processIntents() {

        intents
            .flatMapLatest { intent ->
                when (intent) {
                    PokemonIntent.LoadPokemon ->
                        loadPokemonFlow()

                    PokemonIntent.LoadNextPage ->
                        loadNextPageFlow()

                    PokemonIntent.Retry ->
                        loadPokemonFlow()
                }
            }
            .onEach { change ->

                _state.update {
                    reduce(
                        old = it,
                        change = change
                    )
                }

            }
            .launchIn(viewModelScope)


    }

    private fun loadPokemonFlow():
            Flow<PokemonPartialState> {

        return getPokemon(0)
            .map { pokemon ->

                PokemonPartialState.Success(
                    pokemon
                ) as PokemonPartialState
            }
            .onStart {

                emit(
                    PokemonPartialState.Loading
                )
            }
            .catch { throwable ->

                emit(
                    PokemonPartialState.Error(
                        throwable.message
                            ?: "Unknown error"
                    )
                )
            }
    }

    private fun loadNextPageFlow(): Flow<PokemonPartialState> {
        if (
            _state.value.loadingMore ||
            _state.value.endReached
        ) return emptyFlow()
        return getPokemon(
            _state.value.offset
        ).map {
            PokemonPartialState.Append(it) as PokemonPartialState
        }.onStart {
            emit(PokemonPartialState.LoadingMore)
        }.catch { throwable ->
            emit(PokemonPartialState.Error(throwable.message ?: "Error Loading More"))

        }

    }
}