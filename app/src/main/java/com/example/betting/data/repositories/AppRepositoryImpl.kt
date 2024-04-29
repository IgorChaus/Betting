package com.example.betting.data.repositories

import com.example.betting.common.toEntity
import com.example.betting.common.toModel
import com.example.betting.data.models.PlayerEntity
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import com.example.betting.domain.models.Response
import com.example.betting.domain.repositories.AppRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val service: RetrofitApi,
    private val realmInstance: Realm
): AppRepository {

    override suspend fun getLeagues(leagueName: String, season: String): Response<List<League>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagues(leagueName, season).toModel()
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

    override suspend fun getPlayers(leagueId: String, season: String, page: String): Response<List<Player>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPlayers(leagueId, season, page).toModel()
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

    override suspend fun getFavoritePlayerList(): Flow<List<Player>> {
        return realmInstance.query<PlayerEntity>()
            .find()
            .asFlow()
            .map { change  ->
                change.list.toList().toModel()
            }
    }

    override suspend fun isPlayerFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val favorite = realmInstance.query<PlayerEntity>("id = $0", id).first().find()
            favorite != null
        }
    }

    override suspend fun addFavoritePlayer(playerAdapterItem: Player) {
        val player = playerAdapterItem.toEntity()
        realmInstance.write {
            copyToRealm(player)
        }
    }

    override suspend fun deleteFavoritePlayer(id: Int) {
        realmInstance.write {
            val query: RealmResults<PlayerEntity> = this.query<PlayerEntity>("id = $0", id).find()
            delete(query)
        }
    }

}