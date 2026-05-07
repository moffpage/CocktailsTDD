package kz.grandera.cocktails.data.network

interface CocktailsNetworkDataSource {
    suspend fun cocktails(isAlcoholic: Boolean): List<CocktailNetwork>
}
