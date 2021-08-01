package com.novina.amazeme.di

import com.novina.amazeme.data.network.api.ApiFactory
import com.novina.amazeme.data.network.api.ApiService
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
    fun provideApiService(): ApiService {
       return ApiFactory().apiService
    }


}