package com.example.betting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.source.DataBaseRepository
import com.example.betting.wrappers.PlayerItemAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
): ViewModel() {

    var isPlayerFaforite = false

    fun isPlayerFavorite(playerItemAdapter: PlayerItemAdapter) {
        viewModelScope.launch {
            isPlayerFaforite = dataBaseRepository.isPlayerFavorite(playerItemAdapter.id)
        }
    }

    fun addPlayer(playerItemAdapter: PlayerItemAdapter){
        viewModelScope.launch {
            dataBaseRepository.addPlayer(playerItemAdapter)
            Log.i("MyTag", "AddPlayer $playerItemAdapter")
        }
    }

    fun deletePlayer(playerItemAdapter: PlayerItemAdapter){
        viewModelScope.launch {
            dataBaseRepository.deletePlayer(playerItemAdapter.id)
        }
    }
}
