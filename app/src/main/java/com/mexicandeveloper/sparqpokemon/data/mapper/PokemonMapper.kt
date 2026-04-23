package com.mexicandeveloper.sparqpokemon.data.mapper

import com.mexicandeveloper.sparqpokemon.data.remote.dto.PokemonDto
import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon

fun PokemonDto.toDomain(): Pokemon {

    val id = url
        .trimEnd('/')
        .substringAfterLast("/")

    return Pokemon(
        name = name,
        imageUrl =  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        url = url
    )
}