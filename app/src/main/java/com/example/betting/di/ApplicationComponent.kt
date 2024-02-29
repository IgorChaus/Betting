package com.example.betting.di


import android.app.Application
import com.example.betting.view.DiscoverScreen
import com.example.betting.view.FavoritesScreen
import com.example.betting.view.PlayerScreen
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: DiscoverScreen)
    fun inject(fragment: FavoritesScreen)
    fun inject(fragment: PlayerScreen)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}