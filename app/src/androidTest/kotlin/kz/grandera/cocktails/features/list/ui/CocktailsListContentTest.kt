package kz.grandera.cocktails.features.list.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail
import kz.grandera.cocktails.features.list.presentation.CocktailsListState
import kz.grandera.cocktails.ui.theme.CocktailsTheme

class CocktailsListContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shows_loaded_cocktails() {
        setContent(
            state = state(
                visibleCocktails = listOf(afterglow, sunrise),
            ),
        )

        composeRule.onNodeWithText("Afterglow").assertIsDisplayed()
        composeRule.onNodeWithText("Sunrise").assertIsDisplayed()
    }

    @Test
    fun shows_cocktail_images() {
        setContent(
            state = state(
                visibleCocktails = listOf(afterglow, margarita),
            ),
        )

        composeRule.onNodeWithContentDescription("Afterglow").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Margarita").assertIsDisplayed()
    }

    @Test
    fun shows_loading_state() {
        setContent(state = state(isLoading = true))

        composeRule.onNodeWithContentDescription("Loading cocktails").assertIsDisplayed()
    }

    @Test
    fun changes_search_query() {
        var query = ""
        setContent(
            state = state(searchQuery = query),
            onSearchQueryChanged = { query = it },
        )

        composeRule.onNodeWithContentDescription("Search").performTextInput("sun")

        assertEquals("sun", query)
    }

    @Test
    fun changes_alcohol_filter() {
        var alcoholFilter = AlcoholFilter.NonAlcoholic
        setContent(
            state = state(alcoholFilter = alcoholFilter),
            onAlcoholFilterChanged = { alcoholFilter = it },
        )

        composeRule.onNodeWithText("Alcoholic").performClick()

        assertEquals(AlcoholFilter.Alcoholic, alcoholFilter)
    }

    @Test
    fun retries_after_error() {
        var retries = 0
        setContent(
            state = state(isError = true),
            onRetryClicked = { retries++ },
        )

        composeRule.onNodeWithText("Retry").performClick()

        assertEquals(1, retries)
    }

    @Test
    fun explains_error_state() {
        setContent(state = state(isError = true))

        composeRule.onNodeWithText("Could not load cocktails").assertIsDisplayed()
        composeRule.onNodeWithText("Check the connection and try again").assertIsDisplayed()
    }

    @Test
    fun shows_empty_state_when_no_cocktails_match() {
        setContent(state = state(searchQuery = "zzz"))

        composeRule.onNodeWithText("No cocktails found").assertIsDisplayed()
    }

    private fun setContent(
        state: CocktailsListState,
        onSearchQueryChanged: (String) -> Unit = {},
        onAlcoholFilterChanged: (AlcoholFilter) -> Unit = {},
        onRetryClicked: () -> Unit = {},
    ) {
        composeRule.setContent {
            CocktailsTheme {
                CocktailsListContent(
                    state = state,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onAlcoholFilterChanged = onAlcoholFilterChanged,
                    onRetryClicked = onRetryClicked,
                )
            }
        }
    }

    private fun state(
        isLoading: Boolean = false,
        isError: Boolean = false,
        alcoholFilter: AlcoholFilter = AlcoholFilter.NonAlcoholic,
        searchQuery: String = "",
        cocktails: List<Cocktail> = emptyList(),
        visibleCocktails: List<Cocktail> = emptyList(),
    ): CocktailsListState = CocktailsListState(
        isLoading = isLoading,
        isError = isError,
        alcoholFilter = alcoholFilter,
        searchQuery = searchQuery,
        cocktails = cocktails,
        visibleCocktails = visibleCocktails,
    )

    private companion object {
        val afterglow = cocktail(id = 1L, name = "Afterglow", isAlcoholic = false)
        val sunrise = cocktail(id = 2L, name = "Sunrise", isAlcoholic = false)
        val margarita = cocktail(id = 3L, name = "Margarita", isAlcoholic = true)

        fun cocktail(
            id: Long,
            name: String,
            isAlcoholic: Boolean,
        ): Cocktail = Cocktail(
            id = id,
            name = name,
            imageUrl = "https://example.com/$id.png",
            isAlcoholic = isAlcoholic,
        )
    }
}
