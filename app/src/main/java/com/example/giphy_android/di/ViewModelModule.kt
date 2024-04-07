package com.example.giphy_android.di

import com.example.giphy_android.data.model.gif.GifState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger Hilt module providing View Model related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    /**
     * Provides the initial state for the GifState.
     *
     * @return The initial state for the GifState.
     */
    @Provides
    fun provideInitialState(): GifState {
        return GifState.DEFAULT
    }
}
