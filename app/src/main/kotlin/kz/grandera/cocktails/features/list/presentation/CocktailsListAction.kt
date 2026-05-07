package kz.grandera.cocktails.features.list.presentation

import kz.grandera.cocktails.domain.cocktail.AlcoholFilter

sealed interface CocktailsListAction {
    data class SearchChanged(val query: String) : CocktailsListAction
    data class AlcoholFilterChanged(val alcoholFilter: AlcoholFilter) : CocktailsListAction
    data object RetryClicked : CocktailsListAction
}
