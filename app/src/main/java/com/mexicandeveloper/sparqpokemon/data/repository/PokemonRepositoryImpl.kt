package com.mexicandeveloper.sparqpokemon.data.repository

import com.mexicandeveloper.sparqpokemon.data.mapper.toDomain
import com.mexicandeveloper.sparqpokemon.data.remote.api.PokemonApi
import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon
import com.mexicandeveloper.sparqpokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
): PokemonRepository {

    override fun getPokemon(
        offset: Int,
        limit: Int
    ): Flow<List<Pokemon>> =
        flow {

            val response =
                api.getPokemon(
                    limit = limit,
                    offset = offset
                )

            emit(
                response.results.map {
                    it.toDomain()
                }
            )
        }
}