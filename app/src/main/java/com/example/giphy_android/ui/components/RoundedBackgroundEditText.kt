package com.example.giphy_android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.giphy_android.R
import com.example.giphy_android.ui.theme.GiphyTypography
import com.example.giphy_android.util.getContentSpacing
import com.example.giphy_android.util.getDimensionSpacing

/**
 * Composable function for rendering a rounded background text field with various customization options.
 *
 * @param modifier The modifier for positioning and sizing the text field.
 * @param inputValue MutableState holding the current value of the text field.
 * @param inputLabel The label associated with the text field.
 * @param placeholder The placeholder text to be displayed when the text field is empty.
 * @param inputType The keyboard type for the text input.
 * @param imeAction The IME action associated with the text field.
 * @param showLoader Flag indicating whether to show a loader spinner.
 * @param enabled Flag indicating whether the text field is enabled for user interaction.
 * @param isSingleLine Flag indicating whether the text field should allow only a single line of text.
 * @param maxLines Maximum number of lines allowed in the text field.
 * @param textBoxBackgroundColor The background color of the text field.
 * @param textBoxBorder The border stroke for the text field.
 * @param onResetQueryClicked Callback to be invoked when the reset query button is clicked.
 * @param textStyle The style applied to the text within the text field.
 * @param focusRequester FocusRequester used to request focus for the text field.
 * @param horizontalPadding Padding values for horizontal direction.
 * @param verticalTextPadding Vertical padding value for the text.
 * @param topPart Optional composable content to be displayed at the top of the text field.
 * @param leadingIcon Optional composable content to be displayed as a leading icon.
 * @param onImeAction Callback to be invoked when the IME action is triggered.
 * @param onValueChange Callback to be invoked when the value of the text field changes.
 */

@Composable
fun RoundedBackgroundEditText(
    modifier: Modifier = Modifier,
    inputValue: MutableState<TextFieldValue>,
    inputLabel: String = "",
    placeholder: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    showLoader: Boolean = false,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    textBoxBackgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    textBoxBorder: BorderStroke? = null,
    onResetQueryClicked: (() -> Unit)? = null,
    textStyle: TextStyle = GiphyTypography.bodyMedium.copy(MaterialTheme.colorScheme.onBackground),
    focusRequester: FocusRequester = FocusRequester(),
    horizontalPadding: PaddingValues = PaddingValues(
        start = getDimensionSpacing(),
        end = getDimensionSpacing(),
    ),
    verticalTextPadding: Dp = getDimensionSpacing(),
    topPart: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onImeAction: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier
        .fillMaxWidth()
        .background(
            color = textBoxBackgroundColor,
            shape = RoundedCornerShape(corner = CornerSize(getContentSpacing()))
        )
        .clickable(interactionSource = interactionSource, indication = null) {
            focusRequester.requestFocus()
        }
        .also {
            textBoxBorder?.let { borderStroke ->
                it.border(borderStroke)
            }
        }
    ) {
        val focusManager = LocalFocusManager.current

        topPart?.invoke()

        Row(
            modifier = Modifier
                .padding(vertical = verticalTextPadding)
                .padding(horizontalPadding)
        ) {

            BasicTextField(
                value = inputValue.value,
                textStyle = textStyle,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                onValueChange = {
                    onValueChange.invoke(it)
                },
                enabled = enabled,
                singleLine = isSingleLine,
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = inputType,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onImeAction.invoke()
                        keyboardController?.hide()
                    },
                    onSearch = {
                        onImeAction.invoke()
                    },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        leadingIcon?.let {
                            it()

                            Spacer(modifier = Modifier.padding(end = getDimensionSpacing()))
                        }
                        Box {
                            if (placeholder.isNotEmpty() && inputValue.value.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = MaterialTheme.colorScheme.outline,
                                    style = GiphyTypography.bodyMedium,
                                    textAlign = textStyle.textAlign,
                                    maxLines = 1,
                                    modifier = Modifier.semantics { testTag = "placeholder" }
                                )
                            }
                            innerTextField()
                        }
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .weight(1F)
                    .wrapContentWidth()
                    .semantics { testTag = "inputField" }
            )
            if (inputLabel.isNotEmpty()) {
                Text(
                    text = inputLabel,
                    style = GiphyTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth()
                )
            }
            if (showLoader) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(getDimensionSpacing()),
                    color = MaterialTheme.colorScheme.outline,
                    strokeWidth = 2.dp
                )
            } else if (inputValue.value.text.isNotBlank()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(getDimensionSpacing())
                        .clip(CircleShape)
                        .semantics { testTag = "deleteAllIcon" }
                        .clickable {
                            inputValue.value = inputValue.value.copy("")

                            onResetQueryClicked?.invoke()
                        },
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "remove_button",
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}

@Preview
@Composable
fun RoundedBackgroundEditTextPreview() {
    val inputValue = mutableStateOf(TextFieldValue("Testing.."))
    val placeholder = "Search for gifs.."
    val onRemoveAllTextIconClicked = {}

    RoundedBackgroundEditText(
        inputValue = inputValue,
        placeholder = placeholder,
        onResetQueryClicked = onRemoveAllTextIconClicked
    )
}