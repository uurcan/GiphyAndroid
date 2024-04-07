package com.example.giphy_android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.giphy_android.R
import com.example.giphy_android.data.model.base.ToolbarMode
import com.example.giphy_android.ui.extensions.slideInHorizontalAnimation
import com.example.giphy_android.ui.extensions.slideOutHorizontalAnimation
import com.example.giphy_android.ui.theme.GiphyTypography
import com.example.giphy_android.util.getContentSpacing

/**
 * Composable function for rendering an application toolbar with customizable behavior.
 *
 * @param toolbarMode The mode of the toolbar, determining its appearance and functionality.
 * @param inputValue MutableState holding the current value of the search bar input field.
 * @param title The title to be displayed in the toolbar.
 * @param showSearchBarLoader Flag indicating whether to show a loader spinner in the search bar.
 * @param focusRequester FocusRequester used to request focus for the search bar.
 * @param topBarIconResId Resource ID for the top bar icon.
 * @param onSearchChange Callback to be invoked when the search query changes.
 * @param onResetQueryClicked Callback to be invoked when the reset query button is clicked.
 * @param onNavigationIconClicked Callback to be invoked when the navigation icon is clicked.
 * @param placeholderTextResId Resource ID for the placeholder text in the search bar.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    toolbarMode: ToolbarMode,
    inputValue: MutableState<TextFieldValue>,
    title: String,
    showSearchBarLoader: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    topBarIconResId: Int =  R.drawable.ic_search,
    onSearchChange: (TextFieldValue) -> Unit,
    onResetQueryClicked: (() -> Unit)? = null,
    onNavigationIconClicked: () -> Unit,
    placeholderTextResId: Int = R.string.search_gifs,
) {
    val isNavigationIconVisible = mutableStateOf(toolbarMode != ToolbarMode.InputOnly)

    TopAppBar(
        title = {
            if (toolbarMode == ToolbarMode.TextOnly) {
                Text(
                    text = title,
                    maxLines = 1,
                    style = GiphyTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.semantics { testTag = "textOnlyToolbar" }
                )
            } else {
                RoundedBackgroundEditText(
                    modifier = Modifier.semantics { testTag = "roundedBackgroundEditText" },
                    textStyle = GiphyTypography.bodyMedium.copy(
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    inputValue = inputValue,
                    imeAction = ImeAction.Search,
                    showLoader = showSearchBarLoader,
                    onResetQueryClicked = onResetQueryClicked,
                    placeholder = stringResource(id = placeholderTextResId),
                    leadingIcon = {
                        Icon(
                            painterResource(id = topBarIconResId),
                            tint = MaterialTheme.colorScheme.outline,
                            contentDescription = "search_field_icon"
                        )
                    },
                    focusRequester = focusRequester,
                    verticalTextPadding = getContentSpacing()
                ) {
                    onSearchChange(it)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        navigationIcon = {
            AnimatedVisibility(
                visible = isNavigationIconVisible.value,
                enter = slideInHorizontalAnimation(),
                exit = slideOutHorizontalAnimation()
            ) {
                IconButton(
                    modifier = Modifier.semantics { testTag = "navigationBackButton" },
                    onClick = onNavigationIconClicked
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "top_left_action"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun AppToolbarPreview() {
    val toolbarMode = ToolbarMode.Search
    val inputValue = remember { mutableStateOf(TextFieldValue("Hello")) }

    AppToolbar(
        toolbarMode = toolbarMode,
        inputValue = inputValue,
        title = "Title",
        onSearchChange = {  },
        onNavigationIconClicked = { }
    )
}