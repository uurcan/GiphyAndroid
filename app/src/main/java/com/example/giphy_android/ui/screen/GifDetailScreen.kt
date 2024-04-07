package com.example.giphy_android.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.giphy_android.R
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.ui.view.base.EmptyStateView
import com.example.giphy_android.ui.view.gif.GifDetailView
import com.example.giphy_android.ui.view.gif.GifViewModel
import kotlinx.coroutines.delay

@Composable
fun GifDetailScreen(
    viewModel: GifViewModel
) {
    val state = viewModel.subscribeToState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(state.screen) {
        if (state.screen == GifScreen.Detail){
            return@LaunchedEffect
        }

        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            while (true) {
                viewModel.getRandomGif(context)
                delay(10000L)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.screen == GifScreen.Detail && state.selectedGif != null) {
            GifDetailView(data = state.selectedGif)
        } else {
            when(state.randomGifApiResult) {
                is ApiResult.Success -> {
                    val gif = state.randomGifApiResult.data.data
                    GifDetailView(data = gif)
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
        }
    }
}