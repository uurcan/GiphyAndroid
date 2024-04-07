package com.example.giphy_android.ui.view.gif

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.giphy_android.data.model.gif.Rating
import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.util.getContentSpacing
import com.example.giphy_android.util.getDimensionSpacing

@Composable
fun GifDetailView(
    data: Data
) {
    val imageUrl = data.images.original.url

    Column(modifier = Modifier.padding(getDimensionSpacing())) {

        GifImageContainer(
            modifier = Modifier.padding(bottom = getDimensionSpacing()),
            imageUrl = imageUrl
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (data.title.isNotBlank()) {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(bottom = getContentSpacing())
                    )
                }
                Text(
                    text = data.bitlyUrl,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = getContentSpacing())
                )
                data.altText?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = getContentSpacing())
                    )
                }
            }

            Spacer(modifier = Modifier.size(getContentSpacing()))

            val ratingImageResId = Rating.getImageResId(data.rating)
            Image(
                painter = painterResource(id = ratingImageResId),
                contentDescription = "rating",
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}