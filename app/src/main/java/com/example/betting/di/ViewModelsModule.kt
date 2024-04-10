package com.example.betting.di

import androidx.lifecycle.ViewModel
import com.example.betting.presentation.viewmodels.DiscoverViewModel
import com.example.betting.presentation.viewmodels.FavoriteViewModel
import com.example.betting.presentation.viewmodels.PlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class)
    fun bindDiscoverViewModel(viewModel: DiscoverViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    fun bindPlayerViewModel(viewModel: PlayerViewModel): ViewModel

}