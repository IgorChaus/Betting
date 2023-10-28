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
            handleApiCall {
                service.getLeagues(leagueName, season)
            }
        }
    }

    suspend fun getPlayers(leagueId: String, season: String, page: String): Response<PlayersResponse> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getPlayers(leagueId, season, page)
            }
        }
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
