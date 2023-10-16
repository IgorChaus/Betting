package com.example.betting.viewmodel

import androidx.lifecycle.ViewModel
import com.example.betting.source.DataBaseRepository
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    val playerList = dataBaseRepository.getPlayersList()
}