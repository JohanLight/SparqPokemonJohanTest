package com.mexicandeveloper.sparqpokemon.feature_pokemon.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mexicandeveloper.sparqpokemon.feature_pokemon.PokemonViewModel
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonIntent

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel(),
    modifier: Modifier
) {

    val state by viewModel.state.collectAsState()
    val listState =
        rememberLazyListState()

    LaunchedEffect(
        listState,
        state.loadingMore
    ) {

        snapshotFlow {

            listState.layoutInfo
                .visibleItemsInfo
                .lastOrNull()
                ?.index

        }.collect { lastIndex ->

            if (
                lastIndex ==
                state.pokemon.lastIndex &&
                !state.loadingMore &&
                !state.endReached
            ) {

                viewModel.sendIntent(
                    PokemonIntent.LoadNextPage
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.sendIntent(
            PokemonIntent.LoadPokemon
        )
    }

    PokemonContent(
        modifier = modifier,
        state = state,
        listState = listState,
        onRetry = {
            viewModel.sendIntent(
                PokemonIntent.Retry
            )
        }
    )
}