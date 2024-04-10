package com.example.betting.di


import android.app.Application
import com.example.betting.BettingApp
import com.example.betting.view.DiscoverListFragment
import com.example.betting.view.DiscoverScreen
import com.example.betting.view.FavoritesListFragment
import com.example.betting.view.FavoritesScreen
import com.example.betting.view.PlayerScreen
import dagger.BindsInstance
import dagger.Component


@Component(
    modules = [
        DataModule::class,
        ViewModelsModule::class,
        ViewModelBuilderModule::class
    ]
)

@ApplicationScope
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }

    fun inject(app: BettingApp)

    fun inject(fragment: DiscoverScreen)
    fun inject(fragment: FavoritesScreen)
    fun inject(fragment: PlayerScreen)
    fun inject(fragment: DiscoverListFragment)
    fun inject(fragment: FavoritesListFragment)

}