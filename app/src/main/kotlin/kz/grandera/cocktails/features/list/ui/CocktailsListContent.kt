package kz.grandera.cocktails.features.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail
import kz.grandera.cocktails.features.list.presentation.CocktailsListState

@Composable
fun CocktailsListContent(
    state: CocktailsListState,
    onSearchQueryChanged: (String) -> Unit,
    onAlcoholFilterChanged: (AlcoholFilter) -> Unit,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .background(color = MaterialTheme.colors.background)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            )
            .fillMaxSize(),
    ) {
        if (state.isError) {
            ErrorContent(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                onRetryClicked = onRetryClicked,
            )
        } else {
            CocktailsGrid(
                state = state,
                onSearchQueryChanged = onSearchQueryChanged,
                onAlcoholFilterChanged = onAlcoholFilterChanged,
            )
        }

        if (state.isLoading) {
            LoadingContent(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun CocktailsGrid(
    state: CocktailsListState,
    onSearchQueryChanged: (String) -> Unit,
    onAlcoholFilterChanged: (AlcoholFilter) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 12.dp,
            bottom = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        item(
            key = "title",
            span = { GridItemSpan(currentLineSpan = maxLineSpan) },
        ) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Cocktails",
                style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                ),
            )
        }

        item(
            key = "search_bar",
            span = { GridItemSpan(currentLineSpan = maxLineSpan) },
        ) {
            SearchBar(
                modifier = Modifier.height(height = 36.dp),
                text = state.searchQuery,
                onValueChange = onSearchQueryChanged,
            )
        }

        item(
            key = "filters",
            span = { GridItemSpan(currentLineSpan = maxLineSpan) },
        ) {
            ProvideTextStyle(
                value = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),
            ) {
                SegmentedControl(
                    modifier = Modifier.padding(vertical = 8.dp),
                    selected = { index ->
                        if (index == 0) {
                            state.alcoholFilter == AlcoholFilter.NonAlcoholic
                        } else {
                            state.alcoholFilter == AlcoholFilter.Alcoholic
                        }
                    },
                    titles = listOf("Non-alcoholic", "Alcoholic"),
                    onSegmentClick = { index ->
                        if (index == 0) {
                            onAlcoholFilterChanged(AlcoholFilter.NonAlcoholic)
                        } else {
                            onAlcoholFilterChanged(AlcoholFilter.Alcoholic)
                        }
                    },
                )
            }
        }

        if (!state.isLoading && state.visibleCocktails.isEmpty()) {
            item(
                key = "empty",
                span = { GridItemSpan(currentLineSpan = maxLineSpan) },
            ) {
                EmptyContent()
            }
        }

        items(
            items = state.visibleCocktails,
            key = Cocktail::id,
        ) { cocktail ->
            CocktailItem(
                modifier = Modifier.aspectRatio(ratio = 1f),
                cocktail = cocktail,
            )
        }
    }
}
