package com.example.betting.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.model.LeaguesResponse
import com.example.betting.source.NetworkRepository
import com.example.betting.wrappers.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    private val _progressBar: MutableLiveData<Int> = MutableLiveData()
    val progressBar: LiveData<Int>
        get() = _progressBar

    private val _progressBarVisible: MutableLiveData<Int> = MutableLiveData()
    val progressBarVisible: LiveData<Int>
        get() = _progressBarVisible

    private var leagueList: List<LeaguesResponse.LeagueItem>? = null
    private val playerList = arrayListOf<AdapterItems>()

    init {
        getPlayersFromAllLeagues()
    }

    fun setContentListState(){
        _state.value = State.ContentList(playerList)
    }

    fun setFilteredListState(strSearch: String){
        val searchList = searchPlayer(strSearch)
        _state.value = State.FilteredList(searchList)
    }

    fun setSearchResultState(strSearch: String) {
        if (strSearch == "") {
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

    private fun searchPlayer(strSearch: String) = playerList.filterIsInstance<PlayerItemAdapter>()
        .filter {
            it.firstName?.contains(strSearch, ignoreCase = true) ?: false ||
                    it.lastName?.contains(strSearch, ignoreCase = true) ?: false
        }

            fun getPlayersFromAllLeagues() {
                viewModelScope.launch {
                    getLeagues()
                    if (leagueList != null) {
                        playerList.clear()
                        _progressBarVisible.value = View.VISIBLE
                        for (item in 0..1) {
                            _progressBar.value = 100 / 3 * (item + 1)
                            getPlayers(leagueList!![item])
                            if (playerList.isNotEmpty()) {
                                _state.value = State.ContentList(playerList)
                            }
                        }
                        _progressBarVisible.value = View.GONE
                    }
                }
            }

            private suspend fun getLeagues() {
                val response = networkRepository.getLeagues(LEAGUE_NAME, currentYear)
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
                val response = networkRepository.getPlayers(leagueId, currentYear, "1")
                when (response) {
                    is Response.Success -> {
                        playerList.add(
                            LeagueItemAdapter(
                                name = leagueItem.league.name,
                                logo = leagueItem.league.logo
                            )
                        )

                        val players = response.data.response
                        val limit = minOf(players.size, LIMIT_LIST)
                        for (item in players.take(limit)) {
                            playerList.add(
                                PlayerItemAdapter(
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
        }

}

