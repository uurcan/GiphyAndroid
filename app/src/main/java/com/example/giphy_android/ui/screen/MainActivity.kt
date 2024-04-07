package com.example.giphy_android.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.data.model.gif.GifState
import com.example.giphy_android.util.NetworkAvailabilityChecker
import com.example.giphy_android.data.repository.GifRepository
import com.example.giphy_android.ui.theme.GiphyAppTheme
import com.example.giphy_android.ui.view.gif.GifViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var gifState: GifState
    @Inject lateinit var repository: GifRepository
    @Inject lateinit var networkAvailabilityChecker: NetworkAvailabilityChecker

    private lateinit var viewModel: GifViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = GifViewModel(
            initialState = gifState,
            gifRepository = repository,
            networkAvailabilityChecker = networkAvailabilityChecker
        )

        setContent {
            GiphyAppTheme {
                GifMainScreen(
                    viewModel = viewModel,
                    preselectedScreen = GifScreen.Initial
                )
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (::viewModel.isInitialized) {
            viewModel.onNavigationIconClicked()
        }
    }
}