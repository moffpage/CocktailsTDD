package kz.grandera.cocktails.data.network

import kz.grandera.cocktails.domain.cocktail.Cocktail

import org.junit.Assert.assertEquals
import org.junit.Test

class CocktailNetworkMapperTest {
    private val mapper = CocktailNetworkMapper()

    @Test
    fun maps_network_fields_to_domain_model() {
        val network = CocktailNetwork(
            id = "11007",
            name = "Margarita",
            imageUrl = "https://example.com/margarita.png",
        )

        val actual = mapper.map(
            network = network,
            isAlcoholic = true,
        )

        assertEquals(
            Cocktail(
                id = 11007L,
                name = "Margarita",
                imageUrl = "https://example.com/margarita.png",
                isAlcoholic = true,
            ),
            actual,
        )
    }

    @Test
    fun maps_non_alcoholic_flag_from_request_context() {
        val network = CocktailNetwork(
            id = "12560",
            name = "Afterglow",
            imageUrl = "https://example.com/afterglow.png",
        )

        val actual = mapper.map(
            network = network,
            isAlcoholic = false,
        )

        assertEquals(false, actual.isAlcoholic)
    }
}
