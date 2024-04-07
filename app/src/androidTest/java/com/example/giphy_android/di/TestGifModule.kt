package com.example.giphy_android.di

import com.example.giphy_android.data.remote.request.GiphyService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [GiphyModule::class])
object TestGifModule {

    @Singleton
    @Provides
    fun provideGiphyApiService(retrofit: Retrofit): GiphyService {
        return retrofit.create(GiphyService::class.java)
    }
}