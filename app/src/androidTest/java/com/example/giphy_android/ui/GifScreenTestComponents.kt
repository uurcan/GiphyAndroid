package com.example.giphy_android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.example.giphy_android.data.TestGifData
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.data.remote.response.gif.Data
import com.example.giphy_android.ui.components.AppToolbar
import com.example.giphy_android.ui.components.RoundedBackgroundEditText
import com.example.giphy_android.ui.view.gif.GifGridView

@Composable
fun RoundedBackgroundEditTextTest(input: String? = null) {
    val inputValue = mutableStateOf(TextFieldValue(input ?: ""))
    val focusRequester = FocusRequester()

    RoundedBackgroundEditText(
        inputValue = inputValue,
        placeholder = "Type something..",
        focusRequester = focusRequester
    ) {
        // Update the input value when text changes
        inputValue.value = it
    }
}

@Composable
fun GifGridViewTest(onItemClick:(Data) -> Unit) {
    val gifs = TestGifData.randomGifSearchResult.map { it.data }
    GifGridView(
        gifs = gifs
    ) { selectedGif ->
        onItemClick.invoke(selectedGif)
    }
}

@Composable
fun AppToolbarTest(
    mode: ToolbarMode,
    onSearchChange:(String) -> Unit = {},
    onNavigationIconClicked:() -> Unit = {}
) {
    AppToolbar(
        toolbarMode = mode,
        inputValue = mutableStateOf(TextFieldValue("Testing")),
        title = "Testing",
        onSearchChange = { query ->
            onSearchChange.invoke(query.text)
        },
        onNavigationIconClicked = {
            onNavigationIconClicked.invoke()
        }
    )
}