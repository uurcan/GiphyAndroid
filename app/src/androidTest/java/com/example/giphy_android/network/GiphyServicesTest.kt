package com.example.giphy_android.network

import androidx.test.platform.app.InstrumentationRegistry
import com.example.giphy_android.data.remote.request.GiphyService
import com.example.giphy_android.data.remote.response.base.ApiResult
import com.example.giphy_android.data.repository.GifRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GiphyServicesTest {

    @get:Rule var hiltAndroidRule = HiltAndroidRule(this)

    @Inject lateinit var giphyService: GiphyService

    private lateinit var gifRepository: GifRepository
    private lateinit var apiKey: String

    @Before
    fun before() {
        hiltAndroidRule.inject()

        apiKey = InstrumentationRegistry.getArguments().getString("testApiKey") ?: ""
        gifRepository = GifRepository(giphyService)
    }

    @Test
    fun searchRandomGif_returnsRandomGifObject() = runBlocking {
        val result = gifRepository.getRandomGif(
            apiKey = apiKey
        )

        assert(result is ApiResult.Success)
    }

    @Test
    fun searchGifsByQuery_returnsListOfGifObject() = runBlocking {
        val result = gifRepository.getGifByQuery(
            query = "football",
            apiKey = apiKey
        )

        assert(result is ApiResult.Success)

        val data = (result as ApiResult.Success).data.data

        assertTrue(data.isNotEmpty())
        assertTrue(data.size == 50) //giphy random api returns 50 results
    }

    @Test
    fun searchApiWithComplexQuery_apiSuccessButNoResult_dataIsEmpty() = runBlocking {
        val result = gifRepository.getGifByQuery(
            query = "~~utht53xyP2xehz-qtCY6RQ==/",
            apiKey = apiKey
        )

        assertTrue(result is ApiResult.Success)

        val data = (result as ApiResult.Success).data.data

        assertTrue(data.isEmpty())
    }

}