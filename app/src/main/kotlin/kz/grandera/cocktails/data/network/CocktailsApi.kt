package kz.grandera.cocktails.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {
    @GET("filter.php")
    suspend fun cocktails(
        @Query("a") alcoholType: String,
    ): CocktailsNetworkResponse
}
