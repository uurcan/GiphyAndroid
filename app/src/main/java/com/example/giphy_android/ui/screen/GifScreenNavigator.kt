package com.example.giphy_android.ui.screen

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.ui.extensions.PAGE_ANIMATION_DURATION
import com.example.giphy_android.ui.view.gif.GifViewModel

/**
 * Composable function for navigating between different screens in the Gif feature.
 *
 * @param modifier Modifier for styling the layout.
 * @param viewModel ViewModel for managing data and UI logic related to GIFs.
 * @param navController NavHostController for navigating between destinations.
 * @param startDestination The starting destination for navigation.
 */

@Composable
fun GifScreenNavigator(
    modifier: Modifier = Modifier,
    viewModel: GifViewModel,
    navController: NavHostController,
    startDestination: GifScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.name,
        modifier = modifier
    ) {
        composable(
            route = GifScreen.Initial.name,
            enterTransition = { slideIntoContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            exitTransition = { slideOutOfContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popEnterTransition = { slideIntoContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popExitTransition = { slideOutOfContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
        ) {
            GifDetailScreen(viewModel = viewModel)
        }

        composable(
            route = GifScreen.Search.name,
            enterTransition = { slideIntoContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            exitTransition = { slideOutOfContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popEnterTransition = { slideIntoContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popExitTransition = { slideOutOfContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
        ) {
            GifSearchResultScreen(viewModel = viewModel)
        }

        composable(
            route = GifScreen.Detail.name,
            enterTransition = { slideIntoContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            exitTransition = { slideOutOfContainer(towards = Left, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popEnterTransition = { slideIntoContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
            popExitTransition = { slideOutOfContainer(towards = Right, animationSpec = tween(
                PAGE_ANIMATION_DURATION
            )) },
        ) {
            GifDetailScreen(viewModel = viewModel)
        }
    }
}
