package kz.grandera.cocktails.features.list.presentation

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail

@OptIn(ExperimentalCoroutinesApi::class)
internal class CocktailsListIntegrationTest : CocktailsListIntegrationTestScope() {
    private val mainDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainDispatcher)
        initializeTestScope()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearTestScope()
    }

    @Test
    fun loads_network_cocktails_into_view_model_state() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(cocktailsResponse)
        )

        val viewModel = createViewModel(Dispatchers.IO)

        val state = withTimeout(5_000L) {
            viewModel.state.first { state ->
                !state.isLoading && state.visibleCocktails.isNotEmpty()
            }
        }

        assertFalse(state.isError)
        assertEquals(AlcoholFilter.NonAlcoholic, state.alcoholFilter)
        assertEquals(
            listOf(
                Cocktail(
                    id = 12560L,
                    name = "Afterglow",
                    imageUrl = "https://example.com/afterglow.png",
                    isAlcoholic = false,
                ),
                Cocktail(
                    id = 11542L,
                    name = "Sunrise",
                    imageUrl = "https://example.com/sunrise.png",
                    isAlcoholic = false,
                ),
            ),
            state.visibleCocktails,
        )

        val request = mockWebServer.takeRequest(1L, TimeUnit.SECONDS)
        assertEquals("/filter.php?a=Non_Alcoholic", request?.path)
    }
}
