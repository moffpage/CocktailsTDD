package kz.grandera.cocktails.data.repository

import kz.grandera.cocktails.data.network.CocktailNetwork
import kz.grandera.cocktails.data.network.CocktailNetworkMapper
import kz.grandera.cocktails.data.network.CocktailsNetworkDataSource
import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail

import kotlinx.coroutines.test.runTest

import org.junit.Assert.assertEquals
import org.junit.Test

class BaseCocktailsRepositoryTest {
    private val networkDataSource = FakeCocktailsNetworkDataSource()
    private val repository = BaseCocktailsRepository(
        networkDataSource = networkDataSource,
        networkMapper = CocktailNetworkMapper(),
    )

    @Test
    fun alcoholic() = runTest {
        networkDataSource.result = listOf(
            CocktailNetwork(
                id = "11007",
                name = "Margarita",
                imageUrl = "https://example.com/margarita.png",
            ),
        )

        val actual = repository.cocktails(AlcoholFilter.Alcoholic)

        assertEquals(true, networkDataSource.requestedAlcoholic)
        assertEquals(
            listOf(
                Cocktail(
                    id = 11007L,
                    name = "Margarita",
                    imageUrl = "https://example.com/margarita.png",
                    isAlcoholic = true,
                ),
            ),
            actual,
        )
    }

    @Test
    fun non_alcoholic() = runTest {
        networkDataSource.result = listOf(
            CocktailNetwork(
                id = "12560",
                name = "Afterglow",
                imageUrl = "https://example.com/afterglow.png",
            ),
        )

        val actual = repository.cocktails(AlcoholFilter.NonAlcoholic)

        assertEquals(false, networkDataSource.requestedAlcoholic)
        assertEquals(false, actual.first().isAlcoholic)
    }

    private class FakeCocktailsNetworkDataSource : CocktailsNetworkDataSource {
        var result: List<CocktailNetwork> = emptyList()
        var requestedAlcoholic: Boolean? = null

        override suspend fun cocktails(isAlcoholic: Boolean): List<CocktailNetwork> {
            requestedAlcoholic = isAlcoholic
            return result
        }
    }
}
