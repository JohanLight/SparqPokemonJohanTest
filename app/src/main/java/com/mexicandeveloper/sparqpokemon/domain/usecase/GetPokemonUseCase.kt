package com.mexicandeveloper.sparqpokemon.domain.usecase

import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon
import com.mexicandeveloper.sparqpokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke(offset: Int): Flow<List<Pokemon>> = repository.getPokemon(offset)

}