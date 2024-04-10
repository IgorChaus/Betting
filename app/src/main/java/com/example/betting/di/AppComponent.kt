package com.example.betting.di


import android.content.Context
import com.example.betting.BettingApp
import com.example.betting.presentation.view.DiscoverListFragment
import com.example.betting.presentation.view.DiscoverScreen
import com.example.betting.presentation.view.FavoritesListFragment
import com.example.betting.presentation.view.FavoritesScreen
import com.example.betting.presentation.view.PlayerScreen
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
            @BindsInstance context: Context,
        ): AppComponent
    }

    fun inject(app: BettingApp)

    fun inject(fragment: DiscoverScreen)
    fun inject(fragment: FavoritesScreen)
    fun inject(fragment: PlayerScreen)
    fun inject(fragment: DiscoverListFragment)
    fun inject(fragment: FavoritesListFragment)

}