package com.mexicandeveloper.sparqpokemon.feature_pokemon.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mexicandeveloper.sparqpokemon.domain.model.Pokemon
import com.mexicandeveloper.sparqpokemon.ui.InfiniteScrollList

@Composable
fun PokemonList(
    modifier: Modifier,
    pokemon: List<Pokemon>,
    loadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    InfiniteScrollList(
        modifier = modifier,
        isLoadingMore = loadingMore,
        listOfItems = pokemon,
        content = {
            PokemonRow(pokemon[it])
        },
        onLoadMore = onLoadMore
    )
}

@Composable
fun PokemonRow(
    pokemon: Pokemon
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription =
                pokemon.name,
            modifier =
                Modifier.size(72.dp)
        )

        Spacer(
            modifier =
                Modifier.width(16.dp)
        )

        Text(
            text = pokemon.name.replaceFirstChar {
                it.uppercase()
            }
        )
    }
}