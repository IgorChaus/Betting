package com.example.betting.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.betting.data.local.AppDao
import com.example.betting.data.models.LeaguesDTO
import com.example.betting.data.models.PlayersDTO
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.domain.models.Player
import com.example.betting.Utils.Response
import com.example.betting.Utils.toEntity
import com.example.betting.Utils.toModel
import com.example.betting.domain.models.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val service: RetrofitApi,
    private val appDao: AppDao
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

    override suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPlayers(leagueId, season, page)
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

    override fun getFavoritePlayerList(): LiveData<List<Player>> {
        return appDao.getPlayersList().map { it.toModel() }
    }

    override suspend fun isPlayerFavorite(id: Int): Boolean {
        val favorite = appDao.getPlayer(id)
        return favorite != null
    }

    override suspend fun addFavoritePlayer(playerAdapterItem: Player) {
        appDao.addPlayer(playerAdapterItem.toEntity())
    }

    override suspend fun deleteFavoritePlayer(id: Int) {
        appDao.deletePlayer(id)
    }

}