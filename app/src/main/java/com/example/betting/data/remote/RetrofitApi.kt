package com.example.betting.data.remote

import com.example.betting.data.models.LeaguesResponse
import com.example.betting.data.models.PlayersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {
    @Headers(
        "x-rapidapi-key: f16208e0c2af6076fac34572f3125944",
        "x-rapidapi-host: v3.football.api-sports.io"
    )
    @GET("leagues")
    suspend fun getLeagues(
        @Query("name") name: String,
        @Query("season") season: String
    ) : Response<LeaguesResponse>

    @Headers(
        "x-rapidapi-key: f16208e0c2af6076fac34572f3125944",
        "x-rapidapi-host: v3.football.api-sports.io"
    )
    @GET("players")
    suspend fun getPlayers(
        @Query("league") league: String,
        @Query("season") season: String,
        @Query("page") page: String
    ) : Response<PlayersResponse>
}