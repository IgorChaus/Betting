package com.example.betting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betting.source.DataBaseRepository
import javax.inject.Inject

class PlayerScreenViewModelFactory @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerScreenViewModel::class.java))
            return PlayerScreenViewModel(dataBaseRepository) as T
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}