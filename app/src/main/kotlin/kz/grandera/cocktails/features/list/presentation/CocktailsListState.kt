package kz.grandera.cocktails.features.list.presentation

import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail

data class CocktailsListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val alcoholFilter: AlcoholFilter = AlcoholFilter.NonAlcoholic,
    val searchQuery: String = "",
    val cocktails: List<Cocktail> = emptyList(),
    val visibleCocktails: List<Cocktail> = emptyList(),
)
