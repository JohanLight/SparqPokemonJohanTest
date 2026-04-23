package com.mexicandeveloper.sparqpokemon.core.di

import com.mexicandeveloper.sparqpokemon.data.repository.PokemonRepositoryImpl
import com.mexicandeveloper.sparqpokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository
}
