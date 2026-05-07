package kz.grandera.cocktails.data.network

import javax.inject.Inject

class RetrofitCocktailsNetworkDataSource @Inject constructor(
    private val api: CocktailsApi,
) : CocktailsNetworkDataSource {
    override suspend fun cocktails(isAlcoholic: Boolean): List<CocktailNetwork> {
        val alcoholType = if (isAlcoholic) {
            "Alcoholic"
        } else {
            "Non_Alcoholic"
        }
        return api.cocktails(alcoholType).cocktails
    }
}
