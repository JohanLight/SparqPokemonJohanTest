package com.mexicandeveloper.sparqpokemon.data.remote.api

import com.mexicandeveloper.sparqpokemon.data.remote.dto.PokemonResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponseDto
}