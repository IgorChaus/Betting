package com.example.betting.di


import android.app.Application
import com.example.betting.view.DiscoverScreen
import com.example.betting.view.FavoritesScreen
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: DiscoverScreen)
    fun inject(fragment: FavoritesScreen)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}