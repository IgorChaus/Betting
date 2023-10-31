package com.example.betting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.source.DataBaseRepository
import com.example.betting.wrappers.PlayerAdapterItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
): ViewModel() {

    private val _isPlayerFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isPlayerFavorite: LiveData<Boolean>
        get() = _isPlayerFavorite


    fun checkPlayer(playerAdapterItem: PlayerAdapterItem) {
        viewModelScope.launch {
            _isPlayerFavorite.value = dataBaseRepository.isPlayerFavorite(playerAdapterItem.id)
        }
    }

    fun pressButton(playerAdapterItem: PlayerAdapterItem){
        val isFavorite: Boolean = isPlayerFavorite.value ?: false
        if(isFavorite){
            deletePlayer(playerAdapterItem)
        }else{
            addPlayer(playerAdapterItem)
        }
    }

    private fun addPlayer(playerAdapterItem: PlayerAdapterItem){
        viewModelScope.launch {
            dataBaseRepository.addPlayer(playerAdapterItem)
            _isPlayerFavorite.value = true
        }
    }

    private fun deletePlayer(playerAdapterItem: PlayerAdapterItem){
        viewModelScope.launch {
            dataBaseRepository.deletePlayer(playerAdapterItem.id)
            _isPlayerFavorite.value = false
        }
    }


}
