package kz.grandera.cocktails.di

import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Test
import kz.grandera.cocktails.data.network.CocktailsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class AppModulesTest {
    @Test
    fun provides_retrofit_network_dependencies() {
        val json = NetworkModule.provideJson()
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(
            json = json,
            okHttpClient = okHttpClient,
        )
        val api = NetworkModule.provideCocktailsApi(retrofit)

        assertNotNull(json as Json)
        assertNotNull(okHttpClient as OkHttpClient)
        assertNotNull(retrofit as Retrofit)
        assertNotNull(api as CocktailsApi)
    }

    @Test
    fun provides_cocktails_feature_dependencies() {
        assertNotNull(CocktailsModule.provideCocktailNetworkMapper())
        assertNotNull(CocktailsModule.provideCocktailsSearch())
        assertNotNull(DispatchersModule.provideIoDispatcher())
    }
}
