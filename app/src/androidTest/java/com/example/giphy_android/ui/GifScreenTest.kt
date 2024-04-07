package com.example.giphy_android.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.requestFocus
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.giphy_android.data.TestGifData
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.data.model.gif.GifState
import com.example.giphy_android.data.remote.request.GiphyService
import com.example.giphy_android.data.repository.GifRepository
import com.example.giphy_android.ui.view.gif.GifViewModel
import com.example.giphy_android.util.NetworkAvailabilityChecker
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GifScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject lateinit var state: GifState
    @Inject lateinit var giphyService: GiphyService
    @Inject lateinit var networkAvailabilityChecker: NetworkAvailabilityChecker
    @Inject lateinit var context: Context

    private lateinit var viewModel: GifViewModel

    companion object {
        // Define constants for tag names
        private const val PLACEHOLDER_TAG = "placeholder"
        private const val DELETE_ALL_ICON_TAG = "deleteAllIcon"
        private const val INPUT_FIELD_TAG = "inputField"
        private const val NAVIGATION_BACK_BUTTON_TAG = "navigationBackButton"
        private const val TEXT_ONLY_TOOLBAR_TAG = "textOnlyToolbar"
        private const val ROUNDED_BACKGROUND_EDIT_TEXT_TAG = "roundedBackgroundEditText"
    }

    @Before
    fun setupGifAppNavHost() {
        hiltAndroidRule.inject()

        val gifRepository = GifRepository(giphyService)

        viewModel = GifViewModel(
            initialState = state,
            networkAvailabilityChecker = networkAvailabilityChecker,
            gifRepository = gifRepository
        )
    }

    @Test
    fun updateSearchBar_showDeleteAllIcon_andHidePlaceholder() {
        // Set up the Compose UI
        composeTestRule.setContent { RoundedBackgroundEditTextTest() }

        // Locate UI elements by their tags
        val placeholder = composeTestRule.onNodeWithTag(PLACEHOLDER_TAG, useUnmergedTree = true)
        val deleteAllIcon = composeTestRule.onNodeWithTag(DELETE_ALL_ICON_TAG, useUnmergedTree = true)
        val textField = composeTestRule.onNodeWithTag(INPUT_FIELD_TAG, useUnmergedTree = true)

        // Assert initial conditions
        placeholder.assertExists()
        deleteAllIcon.assertDoesNotExist()

        // Trigger text input by focusing and typing into the text field
        textField.requestFocus()
        textField.performClick()
        textField.performTextInput("Testing..")

        // Assert updated state after text input
        placeholder.assertDoesNotExist()
        deleteAllIcon.assertExists()
    }

    @Test
    fun updateSearchBar_onRemoveAllClick_clearQueryAndShowPlaceholder() {
        // Define initial state variables
        composeTestRule.setContent { RoundedBackgroundEditTextTest("Testing") }

        // Locate UI elements by their tags
        val placeholder = composeTestRule.onNodeWithTag(PLACEHOLDER_TAG, useUnmergedTree = true)
        val deleteAllIcon = composeTestRule.onNodeWithTag(DELETE_ALL_ICON_TAG, useUnmergedTree = true)

        // Assert initial conditions
        placeholder.assertDoesNotExist()
        deleteAllIcon.assertExists()

        // Trigger text input by focusing and typing into the text field
        deleteAllIcon.performClick()

        // Assert updated state after text input
        placeholder.assertExists()
        deleteAllIcon.assertDoesNotExist()
    }

    @Test
    fun searchResultScreen_onSecondItemClick_selectedItemIsUpdated() {
        composeTestRule.setContent {
            GifGridViewTest { viewModel.goToDetailScreen(it) }
        }

        composeTestRule.onNodeWithTag("gif_item_1").performClick()
        assertEquals(viewModel.state.selectedGif, TestGifData.randomGifSearchResult[1].data)
    }

    @Test
    fun inputOnlyToolbar_textAndNavigationBarIsNotVisible() {
        composeTestRule.setContent {
            AppToolbarTest(
                mode = ToolbarMode.InputOnly,
                onSearchChange = { viewModel.updateQuery(it) },
                onNavigationIconClicked = { viewModel.onNavigationIconClicked() }
            )
        }

        val navigationIcon = composeTestRule.onNodeWithTag(NAVIGATION_BACK_BUTTON_TAG, useUnmergedTree = true)
        val toolbarText = composeTestRule.onNodeWithTag(TEXT_ONLY_TOOLBAR_TAG, useUnmergedTree = true)
        val roundedBackgroundEditText = composeTestRule.onNodeWithTag(
            ROUNDED_BACKGROUND_EDIT_TEXT_TAG
        )

        navigationIcon.assertDoesNotExist()
        toolbarText.assertDoesNotExist()
        roundedBackgroundEditText.assertExists()
    }

    @Test
    fun textOnlyToolbar_inputFieldIsNotVisible_navigationButtonIsVisible() {
        composeTestRule.setContent {
            AppToolbarTest(
                mode = ToolbarMode.TextOnly,
                onSearchChange = { viewModel.updateQuery(it) },
                onNavigationIconClicked = { viewModel.onNavigationIconClicked() }
            )
        }

        val navigationIcon = composeTestRule.onNodeWithTag(NAVIGATION_BACK_BUTTON_TAG, useUnmergedTree = true)
        val toolbarText = composeTestRule.onNodeWithTag(TEXT_ONLY_TOOLBAR_TAG, useUnmergedTree = true)
        val roundedBackgroundEditText = composeTestRule.onNodeWithTag(
            ROUNDED_BACKGROUND_EDIT_TEXT_TAG
        )

        navigationIcon.assertExists()
        toolbarText.assertExists()
        roundedBackgroundEditText.assertDoesNotExist()
    }

    @Test
    fun searchToolbar_InputFieldAndNavigationIconIsVisible_toolbarTextIsNotVisible() {
        composeTestRule.setContent {
            AppToolbarTest(
                mode = ToolbarMode.Search,
                onSearchChange = { viewModel.updateQuery(it) },
                onNavigationIconClicked = { viewModel.onNavigationIconClicked() }
            )
        }

        val navigationIcon = composeTestRule.onNodeWithTag(NAVIGATION_BACK_BUTTON_TAG, useUnmergedTree = true)
        val toolbarText = composeTestRule.onNodeWithTag(TEXT_ONLY_TOOLBAR_TAG, useUnmergedTree = true)
        val roundedBackgroundEditText = composeTestRule.onNodeWithTag(
            ROUNDED_BACKGROUND_EDIT_TEXT_TAG
        )

        navigationIcon.assertExists()
        toolbarText.assertDoesNotExist()
        roundedBackgroundEditText.assertExists()
    }
}
