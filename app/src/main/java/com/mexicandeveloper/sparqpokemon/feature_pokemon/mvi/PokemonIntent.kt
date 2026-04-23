package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi

sealed interface PokemonIntent {

    data object LoadPokemon : PokemonIntent

    data object Retry : PokemonIntent

    data object LoadNextPage : PokemonIntent

}