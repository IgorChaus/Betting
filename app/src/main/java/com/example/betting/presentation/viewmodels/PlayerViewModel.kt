package com.example.betting.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.domain.models.Player
import com.example.betting.domain.repositories.AppRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {

    private val _isPlayerFavorite = MutableStateFlow(false)
    val isPlayerFavorite = _isPlayerFavorite.asStateFlow()

    fun checkPlayer(playerAdapterItem: Player) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.isPlayerFavorite(playerAdapterItem.id).collect{
                _isPlayerFavorite.value = it
            }
        }
    }

    fun pressButton(playerAdapterItem: Player){
        val isFavorite = _isPlayerFavorite.value
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

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    private fun handleException(throwable: Throwable?) {
        Log.i("MyTag", "Exception $throwable")
    }

}
