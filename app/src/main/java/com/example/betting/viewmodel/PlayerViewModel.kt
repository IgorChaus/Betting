package com.example.betting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.source.DataBaseRepository
import com.example.betting.wrappers.PlayerItemAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
): ViewModel() {

    private val _isPlayerFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isPlayerFavorite: LiveData<Boolean>
        get() = _isPlayerFavorite


    fun checkPlayer(playerItemAdapter: PlayerItemAdapter) {
        viewModelScope.launch {
            _isPlayerFavorite.value = dataBaseRepository.isPlayerFavorite(playerItemAdapter.id)
        }
    }

    fun pressButton(playerItemAdapter: PlayerItemAdapter){
        val isFavorite: Boolean = isPlayerFavorite.value ?: false
        if(isFavorite){
            deletePlayer(playerItemAdapter)
        }else{
            addPlayer(playerItemAdapter)
        }
    }

    private fun addPlayer(playerItemAdapter: PlayerItemAdapter){
        viewModelScope.launch {
            dataBaseRepository.addPlayer(playerItemAdapter)
            _isPlayerFavorite.value = true
        }
    }

    private fun deletePlayer(playerItemAdapter: PlayerItemAdapter){
        viewModelScope.launch {
            dataBaseRepository.deletePlayer(playerItemAdapter.id)
            _isPlayerFavorite.value = false
        }
    }


}
