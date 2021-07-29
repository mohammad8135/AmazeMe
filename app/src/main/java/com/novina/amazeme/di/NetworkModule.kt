package com.novina.amazeme.di

import com.novina.amazeme.data.network.TvMazeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideTvMazeService(): TvMazeApiService {
        return TvMazeApiService.create()
    }
}