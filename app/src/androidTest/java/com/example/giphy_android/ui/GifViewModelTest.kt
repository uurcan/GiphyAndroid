package com.example.giphy_android.ui

import android.content.Context
import com.example.giphy_android.data.TestGifData
import com.example.giphy_android.data.model.base.NoContentReason
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.data.model.gif.GifScreen
import com.example.giphy_android.data.model.gif.GifState
import com.example.giphy_android.data.remote.request.GiphyService
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.data.repository.GifRepository
import com.example.giphy_android.ui.view.gif.GifViewModel
import com.example.giphy_android.util.NetworkAvailabilityChecker
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GifViewModelTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject lateinit var state: GifState
    @Inject lateinit var giphyService: GiphyService
    @Inject lateinit var context: Context

    private lateinit var viewModel: GifViewModel
    private lateinit var gifRepository: GifRepository
    private lateinit var networkAvailabilityChecker: NetworkAvailabilityChecker

    @Before
    fun setup() {
        hiltAndroidRule.inject()

        networkAvailabilityChecker = mockk()
        gifRepository = GifRepository(giphyService)

        viewModel = GifViewModel(
            initialState = state,
            gifRepository = gifRepository,
            networkAvailabilityChecker = networkAvailabilityChecker
        )
    }

    @Test
    fun testSearchGif_withActiveInternetConnectivity_returnsValue() = runBlocking {
        val query = "cat"
        coEvery { networkAvailabilityChecker.isNetworkAvailable(context) } returns true

        viewModel.searchGifs(query, context)

        assert(viewModel.state.gifSearchApiResult is ApiResult.Success)
        assertEquals(NoContentReason.None, viewModel.state.noContentReason)
    }

    @Test
    fun testRandomGif_withActiveInternetConnectivity_returnsValue() = runBlocking {
        coEvery { networkAvailabilityChecker.isNetworkAvailable(context) } returns true

        viewModel.getRandomGif(context)

        assert(viewModel.state.randomGifApiResult is ApiResult.Success)
        assertEquals(NoContentReason.None, viewModel.state.noContentReason)
    }

    @Test
    fun testSearchGif_withNoActiveInternetConnectivity_returnsError() = runBlocking {
        val query = "cat"
        coEvery { networkAvailabilityChecker.isNetworkAvailable(context) } returns false

        viewModel.searchGifs(query, context)

        assert(viewModel.state.gifSearchApiResult == null)
        assertEquals(NoContentReason.NoInternet(), viewModel.state.noContentReason)
    }

    @Test
    fun testRandomGif_withNoActiveInternetConnectivity_returnsError() = runBlocking {
        coEvery { networkAvailabilityChecker.isNetworkAvailable(context) } returns false

        viewModel.getRandomGif(context)

        assert(viewModel.state.randomGifApiResult == null)
        assertEquals(NoContentReason.NoInternet(), viewModel.state.noContentReason)
    }

    @Test
    fun testOnUserQueryChanged_queryIsUpdated_andOnReset_queryIsBlank() = runBlocking {
        viewModel.updateQuery("scooby doo")
        assert(viewModel.state.query == "scooby doo")

        viewModel.resetQuery()
        assert(viewModel.state.query.isBlank())
    }

    @Test
    fun testOnNavigationIconClicked_detailScreenToSearchScreen() = runBlocking {
        viewModel.goToDetailScreen(TestGifData.randomGif.data)
        viewModel.onNavigationIconClicked()

        assertEquals(GifScreen.Search, viewModel.state.screen)
    }

    @Test
    fun testOnNavigationIconClicked_searchScreenToInitialScreen() = runBlocking {
        viewModel.goToSearchScreen()
        viewModel.onNavigationIconClicked()

        assertEquals(GifScreen.Initial, viewModel.state.screen)
    }

    @Test
    fun testUpdateTopBar_initialScreen_toolbarModeInputOnly() = runBlocking {
        viewModel.goToInitialScreen()
        viewModel.updateTopBar()

        assertEquals(ToolbarMode.InputOnly, viewModel.state.toolbarMode)
    }

    @Test
    fun testUpdateTopBar_searchScreen_toolbarModeSearch() = runBlocking {
        viewModel.goToSearchScreen()
        viewModel.updateTopBar()

        assertEquals(ToolbarMode.Search, viewModel.state.toolbarMode)
    }

    @Test
    fun testUpdateTopBar_detailScreen_toolbarModeTextOnly() = runBlocking {
        viewModel.goToDetailScreen(TestGifData.randomGif.data)
        viewModel.updateTopBar()

        assertEquals(ToolbarMode.TextOnly, viewModel.state.toolbarMode)
    }
}