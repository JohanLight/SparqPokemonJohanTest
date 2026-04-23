package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi

import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon

data class PokemonState(
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val pokemon: List<Pokemon> = emptyList(),
    val error: String? = null,
    val offset: Int = 0,
    val endReached: Boolean = false
)