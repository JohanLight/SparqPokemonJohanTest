package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm

import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon

sealed interface PokemonUIState {

    data object Loading
        : PokemonUIState

    data class Success(
        val pokemon: List<Pokemon>,
        val loadingMore: Boolean = false,
        val endReached: Boolean = false
    ) : PokemonUIState

    data class Error(
        val message: String
    ) : PokemonUIState
}