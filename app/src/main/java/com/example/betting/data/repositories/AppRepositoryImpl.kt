package com.example.betting.data.repositories

import com.example.betting.common.toEntity
import com.example.betting.common.toModel
import com.example.betting.data.models.PlayerEntity
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import com.example.betting.domain.repositories.AppRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val service: RetrofitApi,
    private val realmInstance: Realm
): AppRepository {

    override fun getLeagues(leagueName: String, season: String): Single<List<League>> {
        return service.getLeagues(leagueName, season)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { leaguesDTO ->
                if (leaguesDTO != null) {
                    leaguesDTO.toModel()
                } else {
                    throw Exception("No data available")
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                Single.error(Exception("Failed to retrieve data", throwable))
            }
    }

    override fun getPlayers(leagueId: String, season: String, page: String): Single<List<Player>> {
        return service.getPlayers(leagueId, season, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { playersDTO ->
                if (playersDTO != null) {
                    playersDTO.toModel()
                } else {
                    throw Exception("No data available")
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                Single.error(Exception("Failed to retrieve data", throwable))
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

    override suspend fun isPlayerFavorite(id: Int): Flow<Boolean> {
        return realmInstance.query<PlayerEntity>("id = $0", id)
            .first()
            .asFlow()
            .map { it.obj != null }
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