package kz.grandera.cocktails.domain.cocktail

class CocktailsSearch {
    fun apply(
        cocktails: List<Cocktail>,
        query: String,
    ): List<Cocktail> {
        val trimmedQuery = query.trim()

        if (trimmedQuery.isEmpty()) {
            return cocktails
        }

        return cocktails.filter { cocktail ->
            cocktail.name.contains(
                other = trimmedQuery,
                ignoreCase = true,
            )
        }
    }
}
