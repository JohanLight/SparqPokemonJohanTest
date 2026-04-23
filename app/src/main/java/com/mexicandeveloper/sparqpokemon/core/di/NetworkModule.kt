package com.mexicandeveloper.sparqpokemon.core.di

import com.mexicandeveloper.sparqpokemon.data.remote.api.PokemonApi
import com.mexicandeveloper.sparqpokemon.BuildConfig
import com.mexicandeveloper.sparqpokemon.core.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(
        isLogAvailable: Boolean
    ): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {
            level =
                if (isLogAvailable) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = RetrofitFactory().create(okHttpClient)


    @Provides
    fun provideUserApi(
        retrofit: Retrofit
    ): PokemonApi =
        retrofit.create(PokemonApi::class.java)
}