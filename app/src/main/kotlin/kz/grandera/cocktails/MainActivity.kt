package kz.grandera.cocktails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kz.grandera.cocktails.features.list.presentation.CocktailsListAction
import kz.grandera.cocktails.features.list.presentation.CocktailsListViewModel
import kz.grandera.cocktails.features.list.ui.CocktailsListContent
import kz.grandera.cocktails.ui.theme.CocktailsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailsTheme {
                CocktailsNavigation()
            }
        }
    }
}

@Serializable
private data object CocktailsListRoute : NavKey

@Composable
private fun CocktailsNavigation() {
    val backStack = rememberNavBackStack(CocktailsListRoute)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<CocktailsListRoute> {
                val viewModel: CocktailsListViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                CocktailsListContent(
                    state = state,
                    onSearchQueryChanged = { query ->
                        viewModel.onAction(CocktailsListAction.SearchChanged(query))
                    },
                    onAlcoholFilterChanged = { alcoholFilter ->
                        viewModel.onAction(CocktailsListAction.AlcoholFilterChanged(alcoholFilter))
                    },
                    onRetryClicked = {
                        viewModel.onAction(CocktailsListAction.RetryClicked)
                    },
                )
            }
        },
    )
}
