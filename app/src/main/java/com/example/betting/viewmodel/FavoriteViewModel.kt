package com.example.betting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.betting.source.DataBaseRepository
import com.example.betting.wrappers.AdapterItems
import com.example.betting.wrappers.PlayerItemAdapter
import com.example.betting.wrappers.State
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    val playerList = dataBaseRepository.getPlayersList()

    fun setContentListState() {
        _state.value = playerList.value?.let { State.ContentList(it) }
        _state.value = State.ContentList(playerList.value ?:listOf<PlayerItemAdapter>())
    }

    fun setFilteredListState(strSearch: String) {
        _state.value = State.ActivateSearch
        val searchList: List<AdapterItems>
        if (strSearch.isNotEmpty()) {
            searchList = searchPlayer(strSearch)
        } else {
            searchList = playerList.value ?: listOf<PlayerItemAdapter>()
        }
        _state.value = State.FilteredList(searchList)
    }

    fun setSearchResultState(strSearch: String) {
        if (strSearch.isEmpty()) {
            _state.value = State.ContentList(playerList.value ?:listOf<PlayerItemAdapter>())
        } else {
            val searchList = searchPlayer(strSearch)
            if (searchList.isNotEmpty()) {
                _state.value = State.ResultSearch(searchList)
            } else {
                _state.value = State.NothingFound
            }
        }
    }

    private fun searchPlayer(strSearch: String): List<PlayerItemAdapter>{
        val itemList = if (playerList.value != null){
            playerList.value as List<PlayerItemAdapter>
        } else {
            listOf()
        }
        return itemList.filter {
                it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                        it.lastName?.contains(strSearch, ignoreCase = true) ?: false
            }
    }

}