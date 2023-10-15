package com.example.betting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betting.source.DataRepository
import javax.inject.Inject

class DiscoverViewModelFactory @Inject constructor(private val dataRepository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverViewModel::class.java))
            return DiscoverViewModel(dataRepository) as T
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}