package com.example.betting.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.model.LeaguesResponse
import com.example.betting.source.DataRepository
import com.example.betting.source.RetrofitInstance
import com.example.betting.wrappers.*
import kotlinx.coroutines.launch
import java.util.*

class DiscoverViewModel: ViewModel() {

    private val dataRepository = DataRepository(RetrofitInstance.service)

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

    var leagueList: List<LeaguesResponse.LeagueItem>? = null
    private val playerList = arrayListOf<AdapterItems>()

    fun getPlayersFromAllLeagues() {
        viewModelScope.launch {
            getLeagues()
            if (leagueList != null) {
                playerList.clear()
                _progressBarVisible.value = View.VISIBLE
                for(item in 0..2) {
                    _progressBar.value = 100 / 3 * (item + 1)
                    getPlayers(leagueList!![item])
                    if (playerList.isNotEmpty()) {
                        _state.value = State.Content(playerList)
                    }
                }
                _progressBarVisible.value = View.INVISIBLE
            }
        }
    }

    private suspend fun getLeagues() {
        val response = dataRepository.getLeagues(LEAGUE_NAME, currentYear)
        when (response) {
            is Response.Success -> {
                leagueList = response.data.response
            }
            is Response.Error -> {
                _state.value = State.Error(response.errorMessage)
            }
        }
    }

    private suspend fun getPlayers(leagueItem: LeaguesResponse.LeagueItem) {
        val leagueId = leagueItem.league.id.toString()
        val response = dataRepository.getPlayers(leagueId, currentYear, "1")
        when (response) {
            is Response.Success -> {
                playerList.add(
                    LeagueItem(
                        name = leagueItem.league.name,
                        logo = leagueItem.league.logo
                    )
                )

                val players = response.data.response
                val limit = minOf(players.size, LIMIT_LIST)
                for (item in players.take(limit)) {
                    playerList.add(
                        PlayerItem(
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
                _state.value = State.Error(response.errorMessage)
            }
        }
    }

    companion object{
        const val LEAGUE_NAME = "premier league"
        const val LIMIT_LIST = 10
    }

}

