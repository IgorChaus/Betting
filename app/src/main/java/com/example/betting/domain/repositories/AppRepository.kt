package com.example.betting.domain.repositories

import androidx.lifecycle.LiveData
import com.example.betting.data.models.LeaguesResponse
import com.example.betting.data.models.PlayersResponse
import com.example.betting.domain.models.PlayerAdapterItem
import com.example.betting.wrappers.Response

interface AppRepository {

    suspend fun getLeagues(leagueName: String, season: String): Response<LeaguesResponse>

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersResponse>

    fun getFavoritePlayerList(): LiveData<List<PlayerAdapterItem>>

    suspend fun isPlayerFavorite(id: Int): Boolean

    suspend fun addFavoritePlayer(playerAdapterItem: PlayerAdapterItem)

    suspend fun deleteFavoritePlayer(id: Int)
}