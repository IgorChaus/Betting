package com.example.betting.di

import com.example.betting.view.DiscoverScreen
import dagger.Component

@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: DiscoverScreen)

}