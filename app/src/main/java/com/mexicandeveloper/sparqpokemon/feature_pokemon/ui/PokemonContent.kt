package com.mexicandeveloper.sparqpokemon.feature_pokemon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mexicandeveloper.sparqpokemon.feature_pokemon.mvi.PokemonState

@Composable
fun PokemonContent(
    modifier: Modifier, state: PokemonState, listState: LazyListState, onRetry: () -> Unit
) {

    when {

        state.loading -> {
            LoadingContent(modifier = modifier)
        }

        state.error != null -> {
            ErrorContent(
                modifier = modifier, message = state.error, onRetry = onRetry
            )
        }

        else -> {
            PokemonList(
                modifier = modifier, listState = listState, state = state
            )
        }
    }
}

@Composable
fun LoadingContent(modifier: Modifier) {

    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(
    modifier: Modifier, message: String, onRetry: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = message
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onRetry
        ) {
            Text("Retry")
        }
    }
}