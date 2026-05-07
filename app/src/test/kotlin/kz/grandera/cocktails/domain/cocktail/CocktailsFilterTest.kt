package kz.grandera.cocktails.domain.cocktail

import org.junit.Assert.assertEquals
import org.junit.Test

class CocktailsFilterTest {
    private val filter = CocktailsFilter()

    @Test
    fun alcoholic() {
        val cocktails = listOf(
            cocktail(id = 1L, isAlcoholic = true),
            cocktail(id = 2L, isAlcoholic = false),
            cocktail(id = 3L, isAlcoholic = true),
        )

        val actual = filter.apply(
            cocktails = cocktails,
            alcoholFilter = AlcoholFilter.Alcoholic,
        )

        assertEquals(
            listOf(cocktails[0], cocktails[2]),
            actual,
        )
    }

    @Test
    fun non_alcoholic() {
        val cocktails = listOf(
            cocktail(id = 1L, isAlcoholic = true),
            cocktail(id = 2L, isAlcoholic = false),
            cocktail(id = 3L, isAlcoholic = false),
        )

        val actual = filter.apply(
            cocktails = cocktails,
            alcoholFilter = AlcoholFilter.NonAlcoholic,
        )

        assertEquals(
            listOf(cocktails[1], cocktails[2]),
            actual,
        )
    }

    private fun cocktail(
        id: Long,
        isAlcoholic: Boolean,
    ): Cocktail = Cocktail(
        id = id,
        name = "Cocktail $id",
        imageUrl = "https://example.com/$id.png",
        isAlcoholic = isAlcoholic,
    )
}
