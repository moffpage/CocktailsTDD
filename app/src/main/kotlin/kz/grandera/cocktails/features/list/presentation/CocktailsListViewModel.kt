package kz.grandera.cocktails.features.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.grandera.cocktails.di.IoDispatcher
import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.CocktailsRepository
import kz.grandera.cocktails.domain.cocktail.CocktailsSearch

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val search: CocktailsSearch,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val mutableState = MutableStateFlow(CocktailsListState())
    val state: StateFlow<CocktailsListState> = mutableState

    init {
        loadCocktails(AlcoholFilter.NonAlcoholic)
    }

    fun onAction(action: CocktailsListAction) {
        when (action) {
            is CocktailsListAction.AlcoholFilterChanged -> loadCocktails(action.alcoholFilter)
            CocktailsListAction.RetryClicked -> loadCocktails(mutableState.value.alcoholFilter)
            is CocktailsListAction.SearchChanged -> onSearchChanged(action.query)
        }
    }

    private fun onSearchChanged(query: String) {
        mutableState.update { state ->
            state.copy(
                searchQuery = query,
                visibleCocktails = search.apply(
                    cocktails = state.cocktails,
                    query = query,
                ),
            )
        }
    }

    private fun loadCocktails(alcoholFilter: AlcoholFilter) {
        viewModelScope.launch {
            mutableState.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false,
                    alcoholFilter = alcoholFilter,
                )
            }
            try {
                val cocktails = withContext(ioDispatcher) {
                    repository.cocktails(alcoholFilter)
                }
                mutableState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = false,
                        cocktails = cocktails,
                        visibleCocktails = search.apply(
                            cocktails = cocktails,
                            query = state.searchQuery,
                        ),
                    )
                }
            } catch (exception: Exception) {
                mutableState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        cocktails = emptyList(),
                        visibleCocktails = emptyList(),
                    )
                }
            }
        }
    }
}
