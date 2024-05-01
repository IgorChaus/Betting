package com.example.betting.domain.repositories

import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getLeagues(leagueName: String, season: String): Single<List<League>>

    fun getPlayers(leagueId: String, season: String, page: String): Single<List<Player>>

    suspend fun getFavoritePlayerList(): Flow<List<Player>>

    suspend fun isPlayerFavorite(id: Int): Flow<Boolean>

    suspend fun addFavoritePlayer(playerAdapterItem: Player)

    suspend fun deleteFavoritePlayer(id: Int)
}