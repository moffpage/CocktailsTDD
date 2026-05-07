package kz.grandera.cocktails.features.list.presentation

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import kz.grandera.cocktails.data.network.CocktailNetworkMapper
import kz.grandera.cocktails.data.network.CocktailsApi
import kz.grandera.cocktails.data.network.RetrofitCocktailsNetworkDataSource
import kz.grandera.cocktails.data.repository.BaseCocktailsRepository
import kz.grandera.cocktails.domain.cocktail.CocktailsSearch

internal open class CocktailsListIntegrationTestScope {
    protected lateinit var mockWebServer: MockWebServer

    protected fun initializeTestScope() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    protected fun clearTestScope() {
        if (::mockWebServer.isInitialized) {
            mockWebServer.shutdown()
        }
    }

    protected fun createViewModel(dispatcher: CoroutineDispatcher): CocktailsListViewModel {
        val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(CocktailsApi::class.java)
        val repository = BaseCocktailsRepository(
            networkDataSource = RetrofitCocktailsNetworkDataSource(api),
            networkMapper = CocktailNetworkMapper(),
        )

        return CocktailsListViewModel(
            repository = repository,
            search = CocktailsSearch(),
            ioDispatcher = dispatcher,
        )
    }

    protected companion object {
        const val cocktailsResponse: String = """
            {
              "drinks": [
                {
                  "idDrink": "12560",
                  "strDrink": "Afterglow",
                  "strDrinkThumb": "https://example.com/afterglow.png"
                },
                {
                  "idDrink": "11542",
                  "strDrink": "Sunrise",
                  "strDrinkThumb": "https://example.com/sunrise.png"
                }
              ]
            }
        """
    }
}
