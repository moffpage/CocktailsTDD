package kz.grandera.cocktails.domain.cocktail

import org.junit.Assert.assertEquals
import org.junit.Test

class CocktailsSearchTest {
    private val search = CocktailsSearch()

    @Test
    fun empty_query_returns_source_list() {
        val cocktails = listOf(
            cocktail(id = 1L, name = "Margarita"),
            cocktail(id = 2L, name = "Mojito"),
        )

        val actual = search.apply(
            cocktails = cocktails,
            query = "   ",
        )

        assertEquals(cocktails, actual)
    }

    @Test
    fun query_matches_name_ignoring_case() {
        val cocktails = listOf(
            cocktail(id = 1L, name = "Margarita"),
            cocktail(id = 2L, name = "Espresso Martini"),
            cocktail(id = 3L, name = "Mojito"),
        )

        val actual = search.apply(
            cocktails = cocktails,
            query = "mart",
        )

        assertEquals(
            listOf(cocktails[1]),
            actual,
        )
    }

    private fun cocktail(
        id: Long,
        name: String,
    ): Cocktail = Cocktail(
        id = id,
        name = name,
        imageUrl = "https://example.com/$id.png",
        isAlcoholic = true,
    )
}
