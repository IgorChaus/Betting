package com.example.betting.source

import com.example.betting.api.RetrofitApi
import com.example.betting.model.LeaguesResponse
import com.example.betting.model.PlayersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.example.betting.wrappers.Response
import java.io.IOException

class DataRepository(private val service: RetrofitApi) {
    suspend fun getLeagues(leagueName: String, season: String): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagues(leagueName, season)
                if (response.isSuccessful) {
                    Response.Success<LeaguesResponse.LeagueItem>(data = response.body()!!.response)
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

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPlayers(leagueId, season, page)
                if (response.isSuccessful) {
                    Response.Success<PlayersResponse.PlayerData>(data = response.body()!!.response)
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

    suspend fun getPlayer(playerId: String, season: String): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPlayer(playerId, season)
                if (response.isSuccessful) {
                    Response.Success<PlayersResponse.PlayerData>(data = response.body()!!.response)
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
}
