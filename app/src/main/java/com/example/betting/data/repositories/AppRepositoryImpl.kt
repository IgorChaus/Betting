package com.example.betting.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.betting.data.local.AppDao
import com.example.betting.data.models.LeaguesResponse
import com.example.betting.data.models.PlayersResponse
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.mapper.PlayerMapper
import com.example.betting.domain.models.PlayerAdapterItem
import com.example.betting.wrappers.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val service: RetrofitApi,
    private val appDao: AppDao,
    private val mapper: PlayerMapper
): AppRepository {

    override suspend fun getLeagues(leagueName: String, season: String): Response<LeaguesResponse> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getLeagues(leagueName, season)
            }
        }
    }

    override suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersResponse> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getPlayers(leagueId, season, page)
            }
        }
    }

    override fun getFavoritePlayerList(): LiveData<List<PlayerAdapterItem>> {
        return appDao.getPlayersList().map {
            mapper.mapListDbModelToListAdapterItem(it)
        }
    }

    override suspend fun isPlayerFavorite(id: Int): Boolean {
        val favorite = appDao.getPlayer(id)
        return favorite != null
    }

    override suspend fun addFavoritePlayer(playerAdapterItem: PlayerAdapterItem) {
        val playerItemDbModel = mapper.mapAdapterItemToDbModel(playerAdapterItem)
        appDao.addPlayer(playerItemDbModel)
    }

    override suspend fun deleteFavoritePlayer(id: Int) {
        appDao.deletePlayer(id)
    }

    private suspend fun <T> handleApiCall(call: suspend () -> retrofit2.Response<T>): Response<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Response.Success(data = response.body()!!)
            } else {
                Response.Error(response.code().toString())
            }
        } catch (e: HttpException) {
            Response.Error(e.message ?: "HttpException")
        } catch (e: IOException) {
            Response.Error("IOException")
        } catch (e: Exception) {
            Response.Error(e.message ?: "Exception")
        }
    }

}