package com.example.giphy_android.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giphy_android.R
import com.example.giphy_android.data.model.base.NoContentReason
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.ui.view.base.EmptyStateView
import com.example.giphy_android.ui.view.gif.GifGridView
import com.example.giphy_android.ui.view.gif.GifViewModel

@Composable
fun GifSearchResultScreen(
    viewModel: GifViewModel
) {
    val state = viewModel.subscribeToState()

    Box(modifier = Modifier.fillMaxSize()) {
        when(state.gifSearchApiResult) {
            is ApiResult.Success -> {
                val gifs = state.gifSearchApiResult.data.data

                GifGridView(gifs = gifs) { selectedGif ->
                    viewModel.goToDetailScreen(selectedGif)
                }
            }

            is ApiResult.Error -> {
                EmptyStateView(
                    iconResId = R.drawable.ic_search,
                    descriptionResId = R.string.search_result_empty
                )
            }

            is ApiResult.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp),
                    color = MaterialTheme.colorScheme.outline,
                    strokeWidth = 2.dp
                )
            }
            else -> {}
        }

        if (state.noContentReason != NoContentReason.None) {
            when (val reason = state.noContentReason) {
                is NoContentReason.NoInternet -> {
                    EmptyStateView(
                        iconResId = reason.imageResId,
                        descriptionResId = reason.textResId!!
                    )
                }
                is NoContentReason.EmptyData -> {
                    EmptyStateView(
                        iconResId = reason.imageResId,
                        descriptionResId = reason.textResId!!
                    )
                }
                else -> {}
            }
        }
    }
}