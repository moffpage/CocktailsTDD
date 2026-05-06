package kz.grandera.cocktails

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CocktailsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CocktailsApplication)
            modules(emptyList())
        }
    }
}
