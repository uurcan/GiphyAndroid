package com.example.giphy_android.data.remote.request

import com.example.giphy_android.data.remote.response.gif.search.SearchGifResponse
import com.example.giphy_android.data.remote.response.gif.random.RandomGifResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {
    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("q") query: String,
        @Query("api_key") apiKey: String
    ): SearchGifResponse

    @GET("gifs/random")
    suspend fun getRandomGif(
        @Query("api_key") apiKey: String
    ) : RandomGifResponse
}
