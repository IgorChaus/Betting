package com.example.betting.domain.repositories

import androidx.lifecycle.LiveData
import com.example.betting.data.models.LeaguesDTO
import com.example.betting.data.models.PlayersDTO
import com.example.betting.domain.models.Player
import com.example.betting.Utils.Response
import com.example.betting.domain.models.League

interface AppRepository {

    suspend fun getLeagues(leagueName: String, season: String): Response<List<League>>

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersDTO>

    fun getFavoritePlayerList(): LiveData<List<Player>>

    suspend fun isPlayerFavorite(id: Int): Boolean

    suspend fun addFavoritePlayer(playerAdapterItem: Player)

    suspend fun deleteFavoritePlayer(id: Int)
}