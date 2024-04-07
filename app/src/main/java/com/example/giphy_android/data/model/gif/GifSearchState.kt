package com.example.giphy_android.data.model.gif

import com.example.giphy_android.data.model.base.NoContentReason
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.data.remote.response.gif.search.SearchGifResponse
import com.example.giphy_android.data.remote.response.gif.random.RandomGifResponse

data class GifState(
    val randomGifApiResult: ApiResult<RandomGifResponse>?,
    val gifSearchApiResult: ApiResult<SearchGifResponse>?,
    val noContentReason: NoContentReason,
    val query: String,
    val screen: GifScreen,
    val toolbarMode: ToolbarMode,
    val selectedGif: Data?
) {
    companion object {
        val DEFAULT = GifState(
            randomGifApiResult = null,
            gifSearchApiResult = null,
            noContentReason = NoContentReason.None,
            query = "",
            toolbarMode = ToolbarMode.InputOnly,
            selectedGif = null,
            screen = GifScreen.Initial
        )
    }
}
