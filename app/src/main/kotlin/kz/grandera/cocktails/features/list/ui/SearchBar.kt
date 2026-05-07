package kz.grandera.cocktails.features.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
internal fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalContentColor provides Color(0xFF9E9E9E)) {
        CocktailsTextField(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            placeholder = "Search",
            textStyle = MaterialTheme.typography.h4,
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            trailingContent = {
                if (text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable { onValueChange("") },
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
            ),
        )
    }
}

@Composable
private fun CocktailsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.h4,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val backgroundColor = MaterialTheme.colors.surface
    val placeholderColor = contentColorFor(backgroundColor = backgroundColor)
    val colors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colors.onBackground,
        backgroundColor = backgroundColor,
        placeholderColor = placeholderColor,
        errorIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )

    BasicTextField(
        modifier = modifier
            .semantics { contentDescription = placeholder }
            .background(
                color = colors.backgroundColor(enabled = true).value,
                shape = MaterialTheme.shapes.medium,
            )
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle.merge(TextStyle(color = MaterialTheme.colors.onBackground)),
        cursorBrush = SolidColor(colors.cursorColor(isError = false).value),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            @OptIn(ExperimentalMaterialApi::class)
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = textStyle.copy(color = placeholderColor),
                    )
                },
                colors = colors,
                isError = false,
                enabled = true,
                singleLine = true,
                leadingIcon = leadingContent,
                trailingIcon = trailingContent,
                innerTextField = innerTextField,
                contentPadding = PaddingValues(all = 8.dp),
                interactionSource = remember { MutableInteractionSource() },
                visualTransformation = VisualTransformation.None,
            )
        },
    )
}
