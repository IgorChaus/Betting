package com.example.betting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.betting.source.DataBaseRepository
import com.example.betting.wrappers.PlayerItemAdapter
import com.example.betting.wrappers.State
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    private var playerList = listOf<PlayerItemAdapter>()
    private var strSearch: String = ""
    private val playersObserver: Observer<List<PlayerItemAdapter>> = Observer {
        playerList = it
        when( state.value){
            is State.ContentList -> setContentListState()
            is State.FilteredList -> setFilteredListState(strSearch)
            is State.ResultSearch -> setSearchResultState(strSearch)
            is State.NothingFound -> setSearchResultState(strSearch)
            is State.NoFavoritePlayers -> setContentListState()
           else -> Unit
        }
    }

    init{
        dataBaseRepository.getPlayersList().observeForever(playersObserver)
        setContentListState()
    }

    fun setContentListState() {
        if (playerList.isNotEmpty()) {
            _state.value = State.ContentList(playerList)
        } else {
            _state.value = State.NoFavoritePlayers
        }
    }

    fun setActivateSearch() {
        _state.value = State.ActivateSearch
        if (this.strSearch.isNotEmpty()){
            val strSearch = this.strSearch
            this.strSearch = ""
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

    private fun searchPlayer(strSearch: String): List<PlayerItemAdapter>{
        val filteredList = playerList.filter {
            it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                    it.lastName?.contains(strSearch, ignoreCase = true) ?: false
        }
        return filteredList
    }

    override fun onCleared() {
        super.onCleared()
        dataBaseRepository.getPlayersList().removeObserver(playersObserver)
    }

}