package com.example.giphy_android.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import com.example.giphy_android.R
import com.example.giphy_android.ui.view.gif.GifViewModel
import androidx.navigation.compose.rememberNavController
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.ui.components.AppToolbar
import com.example.giphy_android.util.getDimensionSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifMainScreen(
    viewModel: GifViewModel,
    preselectedScreen: GifScreen?
) {
    val navController = rememberNavController()
    val startDestination = preselectedScreen ?: GifScreen.Initial
    val state = viewModel.subscribeToState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current as ComponentActivity
    val isLoading = state.gifSearchApiResult is ApiResult.Loading
    val searchText = remember { mutableStateOf(TextFieldValue(state.query)) }

    LaunchedEffect(key1 = state.query) {
        if (state.query.length >= 3) {
            viewModel.goToSearchScreen()
            viewModel.searchGifs(state.query, context)
        } else {
            viewModel.goToInitialScreen()
        }
    }

    LaunchedEffect(key1 = state.screen) {
        viewModel.updateTopBar()

        navController.navigate(state.screen.name) {
            launchSingleTop = true
            restoreState = true
        }

        if (state.screen == GifScreen.Initial && state.query.isNotBlank()) {
            searchText.value = searchText.value.copy("")
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(end = getDimensionSpacing())
            ) {

                AppToolbar(
                    inputValue = searchText,
                    toolbarMode = state.toolbarMode,
                    title = state.selectedGif?.title ?: "",
                    topBarIconResId = R.drawable.ic_search,
                    focusRequester = focusRequester,
                    onResetQueryClicked = {
                        viewModel.resetQuery()
                        context.onBackPressed()
                    },
                    onNavigationIconClicked = {
                        context.onBackPressed()
                    },
                    placeholderTextResId = R.string.search_gifs,
                    showSearchBarLoader = isLoading,
                    onSearchChange = { searchQuery ->
                        searchText.value = searchQuery
                        viewModel.updateQuery(searchQuery.text)
                    }
                )
            }
        }
    ) { paddingValues ->
        GifScreenNavigator(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navController = navController,
            startDestination = startDestination
        )
    }
}