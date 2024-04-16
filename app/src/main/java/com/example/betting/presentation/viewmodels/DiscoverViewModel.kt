package com.example.betting.presentation.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.presentation.adapter.PlayerListAdapter
import com.example.betting.presentation.states.State
import com.example.betting.domain.models.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Integer.min
import java.util.Calendar
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

    private val _state = MutableStateFlow<State>(
        State.Loading(
            data = listOf(),
            progress = 0,
            progressVisible = View.VISIBLE
        )
    )
    val state = _state.asStateFlow()

    private var leagueList: List<League>? = null
    private val playerList = arrayListOf<PlayerListAdapter.AdapterItems>()
    private var strSearch: String = EMPTY

    init{
        getPlayersFromAllLeagues()
    }

    fun setContentListState() {
        _state.value = State.ContentList(playerList)
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
        if (strSearch.isEmpty()) {
            _state.value = State.ContentList(playerList)
        } else {
            val searchList = searchPlayer(strSearch)
            if (searchList.isNotEmpty()) {
                _state.value = State.ResultSearch(searchList)
            } else {
                _state.value = State.NothingFound
            }
        }
    }

    private fun searchPlayer(strSearch: String) = playerList.filterIsInstance<Player>()
        .filter {
            it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                    it.lastName?.contains(strSearch, ignoreCase = true) ?: false
        }

    fun getPlayersFromAllLeagues() {
        viewModelScope.launch {
            getLeagues()
            if (leagueList != null) {
                playerList.clear()
                _state.value = State.Loading(
                    data = playerList,
                    progress = 0,
                    progressVisible = View.VISIBLE
                )
                val leagueListSize = leagueList?.size ?:0
                for (item in 0..min(NUMBER_LEAGUES, leagueListSize)) {
                    getPlayers(leagueList!![item])
                    if (playerList.isNotEmpty()) {
                        _state.value = State.Loading(
                            data = playerList,
                            progress = 100 / min(NUMBER_LEAGUES, leagueListSize) * (item + 1),
                            progressVisible = View.VISIBLE
                        )
                    }
                }
                _state.value = State.Loading(
                    data = playerList,
                    progress = 0,
                    progressVisible = View.GONE
                )
                _state.value = State.ContentList(playerList)
            }
        }
    }

    private suspend fun getLeagues() {
        val response = repository.getLeagues(LEAGUE_NAME, currentYear)
        when (response) {
            is Response.Success -> {
                leagueList = response.data
            }
            is Response.Error -> {
                _state.value = State.Error
            }
        }
    }

    private suspend fun getPlayers(leagueItem: League) {
        val leagueId = leagueItem.id.toString()
        val response = repository.getPlayers(leagueId, currentYear, "1")
        when (response) {
            is Response.Success -> {
                playerList.add(
                    League(
                        id = leagueItem.id,
                        name = leagueItem.name,
                        logo = leagueItem.logo
                    )
                )

                val players = response.data
                val limit = minOf(players.size, LIMIT_LIST)
                for (item in players.take(limit)) {
                    playerList.add(
                        item.copy(
                            leagueName = leagueItem.name,
                            leagueLogo = leagueItem.logo
                        )
                    )
                }
            }
            is Response.Error -> {
                _state.value = State.Error
            }
        }
    }

    companion object {
        const val LEAGUE_NAME = "premier league"
        const val LIMIT_LIST = 10
        const val EMPTY = ""
        const val NUMBER_LEAGUES = 3
    }

}

