package kz.grandera.cocktails.features.list.presentation

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kz.grandera.cocktails.domain.cocktail.AlcoholFilter
import kz.grandera.cocktails.domain.cocktail.Cocktail
import kz.grandera.cocktails.domain.cocktail.CocktailsRepository
import kz.grandera.cocktails.domain.cocktail.CocktailsSearch

@OptIn(ExperimentalCoroutinesApi::class)
class CocktailsListViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeCocktailsRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loads_non_alcoholic_cocktails_on_start() = runTest(dispatcher) {
        repository.results[AlcoholFilter.NonAlcoholic] = listOf(afterglow)
        val viewModel = viewModel()

        advanceUntilIdle()

        assertEquals(listOf(AlcoholFilter.NonAlcoholic), repository.requests)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(false, viewModel.state.value.isError)
        assertEquals(AlcoholFilter.NonAlcoholic, viewModel.state.value.alcoholFilter)
        assertEquals(listOf(afterglow), viewModel.state.value.visibleCocktails)
    }

    @Test
    fun searches_loaded_cocktails() = runTest(dispatcher) {
        repository.results[AlcoholFilter.NonAlcoholic] = listOf(afterglow, sunrise)
        val viewModel = viewModel()
        advanceUntilIdle()

        viewModel.onAction(CocktailsListAction.SearchChanged("sun"))
        advanceUntilIdle()

        assertEquals("sun", viewModel.state.value.searchQuery)
        assertEquals(listOf(sunrise), viewModel.state.value.visibleCocktails)
    }

    @Test
    fun changes_alcohol_filter_and_loads_new_cocktails() = runTest(dispatcher) {
        repository.results[AlcoholFilter.NonAlcoholic] = listOf(afterglow)
        repository.results[AlcoholFilter.Alcoholic] = listOf(margarita)
        val viewModel = viewModel()
        advanceUntilIdle()

        viewModel.onAction(CocktailsListAction.AlcoholFilterChanged(AlcoholFilter.Alcoholic))
        advanceUntilIdle()

        assertEquals(
            listOf(AlcoholFilter.NonAlcoholic, AlcoholFilter.Alcoholic),
            repository.requests,
        )
        assertEquals(AlcoholFilter.Alcoholic, viewModel.state.value.alcoholFilter)
        assertEquals(listOf(margarita), viewModel.state.value.visibleCocktails)
    }

    @Test
    fun changes_alcohol_filter_before_loading_new_cocktails() = runTest(dispatcher) {
        repository.results[AlcoholFilter.NonAlcoholic] = listOf(afterglow)
        repository.pendingResults[AlcoholFilter.Alcoholic] = CompletableDeferred()
        val viewModel = viewModel()
        advanceUntilIdle()

        viewModel.onAction(CocktailsListAction.AlcoholFilterChanged(AlcoholFilter.Alcoholic))
        runCurrent()

        assertEquals(true, viewModel.state.value.isLoading)
        assertEquals(AlcoholFilter.Alcoholic, viewModel.state.value.alcoholFilter)

        repository.pendingResults.getValue(AlcoholFilter.Alcoholic).complete(listOf(margarita))
        advanceUntilIdle()

        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(listOf(margarita), viewModel.state.value.visibleCocktails)
    }

    @Test
    fun shows_error_when_repository_fails() = runTest(dispatcher) {
        repository.failure = IllegalStateException("Network unavailable")
        val viewModel = viewModel()

        advanceUntilIdle()

        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(true, viewModel.state.value.isError)
        assertEquals(emptyList<Cocktail>(), viewModel.state.value.visibleCocktails)
    }

    private fun viewModel(): CocktailsListViewModel = CocktailsListViewModel(
        repository = repository,
        search = CocktailsSearch(),
        ioDispatcher = dispatcher,
    )

    private class FakeCocktailsRepository : CocktailsRepository {
        val results = mutableMapOf<AlcoholFilter, List<Cocktail>>()
        val pendingResults = mutableMapOf<AlcoholFilter, CompletableDeferred<List<Cocktail>>>()
        val requests = mutableListOf<AlcoholFilter>()
        var failure: Exception? = null

        override suspend fun cocktails(alcoholFilter: AlcoholFilter): List<Cocktail> {
            requests += alcoholFilter
            failure?.let { throw it }
            pendingResults[alcoholFilter]?.let { return it.await() }
            return results.getValue(alcoholFilter)
        }
    }

    private companion object {
        val afterglow = cocktail(id = 1L, name = "Afterglow", isAlcoholic = false)
        val sunrise = cocktail(id = 2L, name = "Sunrise", isAlcoholic = false)
        val margarita = cocktail(id = 3L, name = "Margarita", isAlcoholic = true)

        fun cocktail(
            id: Long,
            name: String,
            isAlcoholic: Boolean,
        ): Cocktail = Cocktail(
            id = id,
            name = name,
            imageUrl = "https://example.com/$id.png",
            isAlcoholic = isAlcoholic,
        )
    }
}
