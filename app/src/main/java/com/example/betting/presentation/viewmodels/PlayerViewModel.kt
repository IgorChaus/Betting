package com.example.betting.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.domain.models.Player
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {

    private val _isPlayerFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isPlayerFavorite: LiveData<Boolean>
        get() = _isPlayerFavorite


    fun checkPlayer(playerAdapterItem: Player) {
        viewModelScope.launch {
            _isPlayerFavorite.value = repository.isPlayerFavorite(playerAdapterItem.id)
        }
    }

    fun pressButton(playerAdapterItem: Player){
        val isFavorite: Boolean = isPlayerFavorite.value ?: false
        if(isFavorite){
            deletePlayer(playerAdapterItem)
        }else{
            addPlayer(playerAdapterItem)
        }
    }

    private fun addPlayer(playerAdapterItem: Player){
        viewModelScope.launch {
            repository.addFavoritePlayer(playerAdapterItem)
            _isPlayerFavorite.value = true
        }
    }

    private fun deletePlayer(playerAdapterItem: Player){
        viewModelScope.launch {
            repository.deleteFavoritePlayer(playerAdapterItem.id)
            _isPlayerFavorite.value = false
        }
    }


}
