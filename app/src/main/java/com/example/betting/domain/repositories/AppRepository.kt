package com.example.betting.domain.repositories

import com.example.betting.data.models.PlayersDTO
import com.example.betting.domain.models.Player
import com.example.betting.domain.models.Response
import com.example.betting.domain.models.League

interface AppRepository {

    suspend fun getLeagues(leagueName: String, season: String): Response<List<League>>

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<List<Player>>

    suspend fun getFavoritePlayerList(): List<Player>

    suspend fun isPlayerFavorite(id: Int): Boolean

    suspend fun addFavoritePlayer(playerAdapterItem: Player)

    suspend fun deleteFavoritePlayer(id: Int)
}