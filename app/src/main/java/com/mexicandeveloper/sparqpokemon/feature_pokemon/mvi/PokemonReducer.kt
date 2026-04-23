package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi

object PokemonReducer {
    fun reduce(
        old: PokemonState,
        change: PokemonPartialState
    ): PokemonState {

        return when (change) {

            PokemonPartialState.Loading ->
                old.copy(
                    loading = true,
                    error = null
                )

            PokemonPartialState.LoadingMore ->
                old.copy(
                    loadingMore = true
                )

            is PokemonPartialState.Success ->
                old.copy(
                    loading = false,
                    pokemon = change.data
                )

            is PokemonPartialState.Append ->
                old.copy(
                    loadingMore = false,
                    pokemon =
                        old.pokemon + change.data,
                    offset =
                        old.offset + change.data.size,
                    endReached =
                        change.data.isEmpty()
                )

            is PokemonPartialState.Error ->
                old.copy(
                    loading = false,
                    error = change.message
                )
        }
    }
}