package com.mexicandeveloper.sparqpokemon.domain.repository

import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemon(
        offset: Int,
        limit: Int = 15
    ): Flow<List<Pokemon>>
}