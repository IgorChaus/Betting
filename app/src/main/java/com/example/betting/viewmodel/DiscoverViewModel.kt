package com.example.betting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betting.model.LeaguesResponse
import com.example.betting.model.PlayersResponse
import com.example.betting.source.DataRepository
import com.example.betting.source.RetrofitInstance
import com.example.betting.wrappers.Response
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class DiscoverViewModel: ViewModel() {

    private val dataRepository = DataRepository(RetrofitInstance.service)

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

    fun fetchPersons() {
        viewModelScope.launch {
  //          val listLeagues = dataRepository.getLeagues(LEAGUE_NAME, currentYear) as Response.Success<LeaguesResponse.LeagueItem>
  //          Log.i("MyTag", listLeagues.data.toString())
             val listPlayers = dataRepository.getPlayers("570", currentYear,"1") as Response.Success<PlayersResponse.PlayerData>
            Log.i("MyTag", listPlayers.data.toString())
        }
    }

    companion object{
        const val LEAGUE_NAME = "premier league"
    }

}