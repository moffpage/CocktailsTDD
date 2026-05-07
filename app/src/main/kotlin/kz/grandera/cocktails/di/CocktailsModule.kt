package kz.grandera.cocktails.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kz.grandera.cocktails.data.network.CocktailNetworkMapper
import kz.grandera.cocktails.data.network.CocktailsNetworkDataSource
import kz.grandera.cocktails.data.network.RetrofitCocktailsNetworkDataSource
import kz.grandera.cocktails.data.repository.BaseCocktailsRepository
import kz.grandera.cocktails.domain.cocktail.CocktailsRepository
import kz.grandera.cocktails.domain.cocktail.CocktailsSearch

@Module
@InstallIn(SingletonComponent::class)
abstract class CocktailsBindingsModule {
    @Binds
    @Singleton
    abstract fun bindCocktailsNetworkDataSource(
        dataSource: RetrofitCocktailsNetworkDataSource,
    ): CocktailsNetworkDataSource

    @Binds
    @Singleton
    abstract fun bindCocktailsRepository(
        repository: BaseCocktailsRepository,
    ): CocktailsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CocktailsModule {
    @Provides
    fun provideCocktailNetworkMapper(): CocktailNetworkMapper = CocktailNetworkMapper()

    @Provides
    fun provideCocktailsSearch(): CocktailsSearch = CocktailsSearch()
}
