package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi

import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon

sealed interface PokemonPartialState {

    data object Loading
        : PokemonPartialState

    data class Success(
        val data: List<Pokemon>
    ) : PokemonPartialState

    data class Error(
        val message: String
    ) : PokemonPartialState

    data object LoadingMore :
        PokemonPartialState

    data class Append(
        val data: List<Pokemon>
    ) : PokemonPartialState

}