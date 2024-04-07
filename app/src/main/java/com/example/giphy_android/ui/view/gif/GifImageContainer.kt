package com.example.giphy_android.ui.view.gif

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.giphy_android.R
import com.example.giphy_android.util.getContentSpacing

/**
 * Composable function for displaying a container for GIF images with customizable options.
 *
 * @param modifier The modifier for positioning and sizing the GIF image container.
 * @param imageUrl The URL of the GIF image to be displayed.
 * @param shouldAnimateGif Flag indicating whether the GIF should be animated.
 * @param onItemClicked Callback to be invoked when the GIF image container is clicked.
 */

@Composable
fun GifImageContainer(
    modifier: Modifier = Modifier,
    imageUrl: String,
    shouldAnimateGif: Boolean = true,
    onItemClicked:() -> Unit? = {}
) {
    val context = LocalContext.current

    val placeholder = if (isSystemInDarkTheme()) {
        R.drawable.loading_placeholder_dark
    } else {
        R.drawable.loading_placeholder_light
    }

    val decoderFactory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoderDecoder.Factory()
    } else {
        GifDecoder.Factory()
    }

    val imageLoader = ImageRequest.Builder(context)
        .data(imageUrl)
        .error(R.drawable.ic_remove)
        .placeholder(placeholder)
        .apply {
            if (shouldAnimateGif) {
                decoderFactory(decoderFactory)
            }
        }
        .build()

    AsyncImage(
        model = imageLoader,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(getContentSpacing())
            )
            .clip(RoundedCornerShape(getContentSpacing()))
            .clickable {
                onItemClicked()
            },
        contentScale = ContentScale.Crop
    )
}