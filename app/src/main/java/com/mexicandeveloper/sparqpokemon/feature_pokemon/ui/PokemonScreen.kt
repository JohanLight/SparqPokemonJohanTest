package com.mexicandeveloper.sparqpokemon.feature_pokemon.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mexicandeveloper.sparqpokemon.feature_pokemon.PokemonViewModel
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvvm.UiEvent

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel(), modifier: Modifier
) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(Unit) {

        viewModel.events.collect { event ->

            when (event) {

                is UiEvent.ShowError ->
                    snackbarHostState
                        .showSnackbar(
                            event.message
                        )

                is UiEvent.ShowSnackbar ->
                    snackbarHostState
                        .showSnackbar(
                            event.message
                        )
            }
        }
    }
    Scaffold(
        modifier = modifier, snackbarHost = {
            SnackbarHost(
                snackbarHostState
            )
        }) {

        PokemonContent(
            modifier = Modifier.padding(it),
            uiState = uiState,
            onLoadMore = viewModel::loadNextPage,
            onRetry = viewModel::retry
        )
    }
}