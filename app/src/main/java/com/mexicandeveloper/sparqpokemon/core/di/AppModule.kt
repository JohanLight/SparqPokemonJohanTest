package com.mexicandeveloper.sparqpokemon.core.di

import com.mexicandeveloper.sparqpokemon.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIsLogAvailable(): Boolean {
        return BuildConfig.LOG_NETWORK
    }


}