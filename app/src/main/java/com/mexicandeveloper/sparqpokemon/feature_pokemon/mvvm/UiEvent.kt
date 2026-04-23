package com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm

sealed interface UiEvent {

    data class ShowError(
        val message: String
    ) : UiEvent

    data class ShowSnackbar(
        val message: String
    ) : UiEvent
}