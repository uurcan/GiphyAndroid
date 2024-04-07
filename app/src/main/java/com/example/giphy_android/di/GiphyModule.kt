package com.example.giphy_android.di

import com.example.giphy_android.data.remote.request.GiphyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GiphyModule {

    @Singleton
    @Provides
    fun provideGiphyApiService(retrofit: Retrofit): GiphyService {
        return retrofit.create(GiphyService::class.java)
    }
}