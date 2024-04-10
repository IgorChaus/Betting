package com.example.betting.domain.repositories

import androidx.lifecycle.LiveData
import com.example.betting.data.models.LeaguesResponse
import com.example.betting.data.models.PlayersResponse
import com.example.betting.domain.models.Player
import com.example.betting.wrappers.Response

interface AppRepository {

    suspend fun getLeagues(leagueName: String, season: String): Response<LeaguesResponse>

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersResponse>

    fun getFavoritePlayerList(): LiveData<List<Player>>

    suspend fun isPlayerFavorite(id: Int): Boolean

    suspend fun addFavoritePlayer(playerAdapterItem: Player)

    suspend fun deleteFavoritePlayer(id: Int)
}