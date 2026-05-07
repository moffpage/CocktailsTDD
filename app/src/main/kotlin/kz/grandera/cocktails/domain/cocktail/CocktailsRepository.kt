package kz.grandera.cocktails.domain.cocktail

interface CocktailsRepository {
    suspend fun cocktails(alcoholFilter: AlcoholFilter): List<Cocktail>
}
