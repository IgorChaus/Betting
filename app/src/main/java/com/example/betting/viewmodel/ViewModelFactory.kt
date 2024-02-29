package com.example.betting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betting.di.ApplicationScope
import com.example.betting.source.DataBaseRepository
import com.example.betting.source.NetworkRepository
import javax.inject.Inject

@ApplicationScope
class ViewModelFactory@Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository
) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverViewModel::class.java))
            return DiscoverViewModel(networkRepository) as T
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(dataBaseRepository) as T
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java))
            return PlayerViewModel(dataBaseRepository) as T

        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}