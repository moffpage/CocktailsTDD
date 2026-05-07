package kz.grandera.cocktails.data.network

import kz.grandera.cocktails.domain.cocktail.Cocktail

class CocktailNetworkMapper {
    fun map(
        network: CocktailNetwork,
        isAlcoholic: Boolean,
    ): Cocktail = Cocktail(
        id = network.id.toLong(),
        name = network.name,
        imageUrl = network.imageUrl,
        isAlcoholic = isAlcoholic,
    )
}
