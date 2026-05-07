package kz.grandera.cocktails.data.repository

import javax.inject.Inject
import kz.grandera.cocktails.data.network.CocktailNetworkMapper
import kz.grandera.cocktails.data.network.CocktailsNetworkDataSource
import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail
import kz.grandera.cocktails.domain.cocktail.CocktailsRepository

class BaseCocktailsRepository @Inject constructor(
    private val networkDataSource: CocktailsNetworkDataSource,
    private val networkMapper: CocktailNetworkMapper,
) : CocktailsRepository {
    override suspend fun cocktails(alcoholFilter: AlcoholFilter): List<Cocktail> {
        val isAlcoholic = when (alcoholFilter) {
            AlcoholFilter.Alcoholic -> true
            AlcoholFilter.NonAlcoholic -> false
        }

        return networkDataSource.cocktails(isAlcoholic).map { network ->
            networkMapper.map(
                network = network,
                isAlcoholic = isAlcoholic,
            )
        }
    }
}
