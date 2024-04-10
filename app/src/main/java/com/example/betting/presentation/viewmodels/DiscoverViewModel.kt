package com.example.betting.presentation.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.data.models.LeaguesResponse
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.domain.models.AdapterItems
import com.example.betting.domain.models.LeagueAdapterItem
import com.example.betting.domain.models.PlayerAdapterItem
import com.example.betting.wrappers.Response
import com.example.betting.wrappers.State
import kotlinx.coroutines.launch
import java.lang.Integer.min
import java.util.Calendar
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    private var leagueList: List<LeaguesResponse.LeagueItem>? = null
    private val playerList = arrayListOf<AdapterItems>()
    private var strSearch: String = EMPTY

    init {
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

    private fun searchPlayer(strSearch: String) = playerList.filterIsInstance<PlayerAdapterItem>()
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
                leagueList = response.data.response
            }
            is Response.Error -> {
                _state.value = State.Error
            }
        }
    }

    private suspend fun getPlayers(leagueItem: LeaguesResponse.LeagueItem) {
        val leagueId = leagueItem.league.id.toString()
        val response = repository.getPlayers(leagueId, currentYear, "1")
        when (response) {
            is Response.Success -> {
                playerList.add(
                    LeagueAdapterItem(
                        name = leagueItem.league.name,
                        logo = leagueItem.league.logo
                    )
                )

                val players = response.data.response
                val limit = minOf(players.size, LIMIT_LIST)
                for (item in players.take(limit)) {
                    playerList.add(
                        PlayerAdapterItem(
                            id = item.player.id,
                            firstName = item.player.firstname,
                            lastName = item.player.lastname,
                            age = item.player.age,
                            birthPlace = item.player.birth.place,
                            birthCountry = item.player.birth.country,
                            birthDate = item.player.birth.date,
                            nationality = item.player.nationality,
                            height = item.player.height,
                            weight = item.player.weight,
                            photo = item.player.photo,
                            team = item.statistics[0].team.name,
                            leagueName = leagueItem.league.name,
                            leagueLogo = leagueItem.league.logo
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

