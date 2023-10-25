package com.example.betting.source

import com.example.betting.network.RetrofitApi
import com.example.betting.model.LeaguesResponse
import com.example.betting.model.PlayersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.example.betting.wrappers.Response
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val service: RetrofitApi) {
    suspend fun getLeagues(leagueName: String, season: String): Response<LeaguesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagues(leagueName, season)
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

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getPlayers(leagueId, season, page)
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

}
