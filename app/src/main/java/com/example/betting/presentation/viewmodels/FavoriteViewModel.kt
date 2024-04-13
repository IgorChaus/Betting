package com.example.betting.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.domain.models.Player
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.presentation.states.State
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    private var playerList = listOf<Player>()
    private var strSearch: String = EMPTY

    fun getFavoritePlayers(){
        viewModelScope.launch {
            playerList = repository.getFavoritePlayerList()
            if (playerList.isNotEmpty()) {
                _state.value = State.ContentList(playerList)
            } else {
                _state.value = State.NoFavoritePlayers
            }
        }

    }

    fun setActivateSearch() {
        _state.value = State.ActivateSearch
        if (this.strSearch.isNotEmpty()){
            val strSearch = this.strSearch
            this.strSearch = EMPTY
            setFilteredListState(strSearch)
        }
    }

    fun setFilteredListState(strSearch: String) {
        if (this.strSearch == strSearch){
            return
        }
        this.strSearch = strSearch
        val filteredList = if (strSearch.isNotEmpty()){
            searchPlayer(strSearch)
        } else {
            playerList
        }
        _state.value = State.FilteredList(filteredList)
    }

    fun setSearchResultState(strSearch: String) {
        this.strSearch = strSearch
        if (strSearch.isEmpty()) {
            _state.value = State.ContentList(playerList )
        } else {
            val filteredList = searchPlayer(strSearch)
            if (filteredList.isNotEmpty()) {
                _state.value = State.ResultSearch(filteredList)
            } else {
                _state.value = State.NothingFound
            }
        }
    }

    private fun searchPlayer(strSearch: String): List<Player>{
        val filteredList = playerList.filter {
            it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                    it.lastName?.contains(strSearch, ignoreCase = true) ?: false
        }
        return filteredList
    }

    companion object{
        const val EMPTY = ""
    }

}