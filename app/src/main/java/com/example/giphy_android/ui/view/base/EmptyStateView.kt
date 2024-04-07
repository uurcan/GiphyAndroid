package com.example.giphy_android.ui.view.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giphy_android.R
import com.example.giphy_android.ui.theme.GiphyTypography
import com.example.giphy_android.util.getContentSpacing
import com.example.giphy_android.util.getDimensionSpacing
import com.example.giphy_android.util.getInputSpacing

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    descriptionResId: Int,
    iconResId: Int?
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(getInputSpacing()), horizontalAlignment = Alignment.CenterHorizontally) {

        if (iconResId != null) {
            Image(
                painter = painterResource(id = iconResId),
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
            )

            Spacer(modifier = Modifier.size(getContentSpacing()))
        }

        Text(
            modifier = Modifier.padding(start = getDimensionSpacing(), end = getDimensionSpacing()),
            text = stringResource(id = descriptionResId),
            textAlign = TextAlign.Center,
            style = GiphyTypography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview
@Composable
fun EmptyStateViewPreview() {
    val noContentReasonResId = R.string.error_no_network_connection
    val noContentImageResId = R.drawable.ic_empty_state

    EmptyStateView(descriptionResId = noContentReasonResId, iconResId = noContentImageResId)
}