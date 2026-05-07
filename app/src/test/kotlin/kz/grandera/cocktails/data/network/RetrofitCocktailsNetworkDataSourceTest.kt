package kz.grandera.cocktails.data.network

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class RetrofitCocktailsNetworkDataSourceTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun requests_non_alcoholic_cocktails_from_retrofit_api() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(cocktailsResponse)
        )
        val dataSource = RetrofitCocktailsNetworkDataSource(api())

        val cocktails = dataSource.cocktails(isAlcoholic = false)

        assertEquals("/filter.php?a=Non_Alcoholic", mockWebServer.takeRequest().path)
        assertEquals(
            listOf(
                CocktailNetwork(
                    id = "12560",
                    name = "Afterglow",
                    imageUrl = "https://example.com/afterglow.png",
                )
            ),
            cocktails,
        )
    }

    private fun api(): CocktailsApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(CocktailsApi::class.java)

    private companion object {
        const val cocktailsResponse = """
            {
              "drinks": [
                {
                  "idDrink": "12560",
                  "strDrink": "Afterglow",
                  "strDrinkThumb": "https://example.com/afterglow.png"
                }
              ]
            }
        """
    }
}
