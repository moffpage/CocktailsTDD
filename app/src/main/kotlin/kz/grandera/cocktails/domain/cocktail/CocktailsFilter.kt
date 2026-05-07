package kz.grandera.cocktails.domain.cocktail

class CocktailsFilter {
    fun apply(
        cocktails: List<Cocktail>,
        alcoholFilter: AlcoholFilter,
    ): List<Cocktail> {
        val isAlcoholic = when (alcoholFilter) {
            AlcoholFilter.Alcoholic -> true
            AlcoholFilter.NonAlcoholic -> false
        }

        return cocktails.filter { cocktail ->
            cocktail.isAlcoholic == isAlcoholic
        }
    }
}
