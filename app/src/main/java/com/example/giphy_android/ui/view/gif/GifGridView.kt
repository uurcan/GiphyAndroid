package com.example.giphy_android.ui.view.gif

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.ui.extensions.calculateDelayAndEasing
import com.example.giphy_android.util.ScaleAndAlphaArgs
import com.example.giphy_android.util.getContentSpacing
import com.example.giphy_android.util.getDimensionSpacing
import com.example.giphy_android.util.scaleAndAlpha

@Composable
fun GifGridView(
    gifs: List<Data>,
    columnCount: Int = 3,
    onItemClicked:(Data) -> Unit
) {
    val gifUrls = gifs.map { it.images.original.url }
    val state = rememberLazyListState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = PaddingValues(
            horizontal = getDimensionSpacing(),
            vertical = getContentSpacing()
        )
    ) {
        items(gifUrls.size) { index ->
            val (delay, easing) = state.calculateDelayAndEasing(index, columnCount)
            val animation = tween<Float>(durationMillis = 200, delayMillis = delay, easing = easing)
            val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
            val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

            val imageUrl = gifUrls[index]

            GifImageContainer(
                modifier = Modifier
                    .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale)
                    .padding(getContentSpacing())
                    .semantics { testTag = "gif_item_$index" },
                imageUrl = imageUrl,
                shouldAnimateGif = false
            ) {
                onItemClicked(gifs[index])
            }
        }
    }
}
