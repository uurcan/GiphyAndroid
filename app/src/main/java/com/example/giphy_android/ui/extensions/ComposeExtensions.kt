package com.example.giphy_android.ui.extensions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable

const val ANIMATION_DURATION_IN_MILLISECONDS = 300
const val PAGE_ANIMATION_DURATION = 700

val slideInHorizontalAnimation: () -> EnterTransition = {
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(ANIMATION_DURATION_IN_MILLISECONDS)
    )
}

val slideOutHorizontalAnimation: () -> ExitTransition = {
    slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(ANIMATION_DURATION_IN_MILLISECONDS)
    )
}

@Composable
fun LazyListState.calculateDelayAndEasing(index: Int, columnCount: Int): Pair<Int, Easing> {
    val row = index / columnCount
    val column = index % columnCount
    val visibleRows = layoutInfo.visibleItemsInfo.count()
    val isFirstLoad = visibleRows == 0
    val rowDelay = 200 * when {
        isFirstLoad -> row // initial load
        else -> 1 // scrolling to top
    }
    val scrollDirectionMultiplier = if (isFirstLoad) 1 else -1
    val columnDelay = column * 150 * scrollDirectionMultiplier
    val easing = if (isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing
    return rowDelay + columnDelay to easing
}
