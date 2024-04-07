package com.example.giphy_android.ui.view.gif

import android.content.Context
import com.example.giphy_android.R
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.data.model.gif.GifState
import com.example.giphy_android.data.model.base.NoContentReason
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.util.NetworkAvailabilityChecker
import com.example.giphy_android.data.repository.GifRepository
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.data.remote.response.gif.random.RandomGifResponse
import com.example.giphy_android.data.remote.response.gif.search.SearchGifResponse
import com.example.giphy_android.ui.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GifViewModel @Inject constructor(
    initialState: GifState,
    private val gifRepository: GifRepository,
    private val networkAvailabilityChecker: NetworkAvailabilityChecker
): BaseViewModel<GifState>(MutableStateFlow(initialState)) {

    suspend fun searchGifs(query: String, context: Context) {
        if (!networkAvailabilityChecker.isNetworkAvailable(context = context)) {
            state = state.copy(noContentReason = NoContentReason.NoInternet())
            return
        }

        state = state.copy(gifSearchApiResult = ApiResult.Loading)

        val response = gifRepository.getGifByQuery(
            query = query,
            apiKey = context.getString(R.string.api_key)
        )

        when (response) {
            is ApiResult.Success -> { onSearchGifSucceeded(response.data) }
            is ApiResult.Error -> onSearchGifFailed(response.message)
            else -> {} // Loading state handled on Composable side
        }
    }

    suspend fun getRandomGif(context: Context) {
        if (!networkAvailabilityChecker.isNetworkAvailable(context = context)) {
            state = state.copy(noContentReason = NoContentReason.NoInternet())
            return
        }

        state = state.copy(randomGifApiResult = ApiResult.Loading)

        val response = gifRepository.getRandomGif(
            apiKey = context.getString(R.string.api_key)
        )

        when (response) {
            is ApiResult.Success -> { onGetRandomGifSucceeded(response.data) }
            is ApiResult.Error -> onGetRandomGifFailed(response.message)
            else -> {} // Loading state handled on Composable side
        }
    }

    fun updateQuery(searchQuery: String) {
        state = state.copy(query = searchQuery)
    }

    fun resetQuery() {
        state = state.copy(query = "")
    }

    fun goToSearchScreen() {
        state = state.copy(screen = GifScreen.Search)
    }

    fun goToInitialScreen() {
        state = state.copy(screen = GifScreen.Initial)
    }

    fun goToDetailScreen(selectedGif: Data) {
        state = state.copy(selectedGif = selectedGif, screen = GifScreen.Detail)
    }

    fun onNavigationIconClicked() {
        val nextScreen = when(state.screen) {
            GifScreen.Detail -> GifScreen.Search
            GifScreen.Search-> GifScreen.Initial
            else -> GifScreen.Initial
        }

        state = state.copy(screen = nextScreen)
    }

    fun updateTopBar() {
        state = when(state.screen) {
            GifScreen.Initial -> {
                state.copy(toolbarMode = ToolbarMode.InputOnly)
            }

            GifScreen.Search -> {
                state.copy(toolbarMode = ToolbarMode.Search)
            }

            GifScreen.Detail -> {
                state.copy(toolbarMode = ToolbarMode.TextOnly)
            }
        }
    }

    private fun onGetRandomGifFailed(errorMessage: String) {
        state = state.copy(randomGifApiResult = ApiResult.Error(errorMessage))
    }

    private fun onSearchGifFailed(errorMessage: String) {
        state = state.copy(gifSearchApiResult = ApiResult.Error(errorMessage))
    }

    private fun onSearchGifSucceeded(response: SearchGifResponse) {
        state = if (response.data.isEmpty()) {
            state.copy(noContentReason = NoContentReason.EmptyData())
        } else {
            state.copy(noContentReason = NoContentReason.None)
        }
        state = state.copy(gifSearchApiResult = ApiResult.Success(response))
    }

    private fun onGetRandomGifSucceeded(response: RandomGifResponse) {
        state = state.copy(randomGifApiResult = ApiResult.Success(response), noContentReason = NoContentReason.None)
    }
}
