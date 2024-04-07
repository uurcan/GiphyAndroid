package com.example.giphy_android.di

import com.example.giphy_android.data.model.gif.GifState
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ViewModelModule::class])
object TestViewModelModule {

    @Provides
    fun provideInitialState(): GifState {
        return GifState.DEFAULT
    }
}