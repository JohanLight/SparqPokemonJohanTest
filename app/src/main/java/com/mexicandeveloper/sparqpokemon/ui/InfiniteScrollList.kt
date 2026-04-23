package com.mexicandeveloper.sparqpokemon.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier


@Composable
fun InfiniteScrollList(
    modifier: Modifier,
    isLoadingMore: Boolean,
    listOfItems: List<Any>,
    content: @Composable (Int) -> Unit,
    onLoadMore:()-> Unit
) {
    val listState =
        rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo
                .visibleItemsInfo
                .lastOrNull()
                ?.index ?: 0
        }.collect { lastVisibleIndex ->
            val shouldLoadMore =
                lastVisibleIndex >=
                        listOfItems.lastIndex - 3
            if (shouldLoadMore) {
                onLoadMore()
            }
        }
    }
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(listOfItems.size) {
            content(it)
        }
        if (isLoadingMore) {
            item {
                CircularProgressIndicator()
            }
        }
    }
}