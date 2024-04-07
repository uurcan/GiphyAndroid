package com.example.giphy_android.data.repository

import com.example.giphy_android.data.remote.request.GiphyService
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.data.remote.response.gif.search.SearchGifResponse
import com.example.giphy_android.data.remote.response.gif.random.RandomGifResponse
import javax.inject.Inject

class GifRepository @Inject constructor(private val giphyService: GiphyService) {

    /**
     * Fetches GIFs based on the provided query.
     *
     * @param query The search query for GIFs.
     * @param apiKey The API key for accessing the Giphy API.
     * @return An [ApiResult] containing the response data or an error message.
     */
    suspend fun getGifByQuery(query: String, apiKey: String): ApiResult<SearchGifResponse> {
        return try {
            val response = giphyService.searchGifs(
                query = query,
                apiKey = apiKey
            )
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }

    /**
     * Fetches a random GIF.
     *
     * @param apiKey The API key for accessing the Giphy API.
     * @return An [ApiResult] containing the response data or an error message.
     */
    suspend fun getRandomGif(apiKey: String) : ApiResult<RandomGifResponse> {
        return try {
            val response = giphyService.getRandomGif(
                apiKey = apiKey
            )
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "An error occurred")
        }
    }
}
