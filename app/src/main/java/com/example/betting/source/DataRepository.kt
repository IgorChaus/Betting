package com.example.betting.source

import com.example.betting.api.RetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.example.betting.wrappers.Response
import java.io.IOException

class DataRepository(private val service: RetrofitApi) {
    suspend fun getLeagues(): Response {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagues("PREMIER LEAGUE", "2023")
                if (response.isSuccessful) {
                    Response.Success(data = response.body()!!.response)
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
