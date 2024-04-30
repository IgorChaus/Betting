package com.example.betting.data.remote

import com.example.betting.data.models.LeaguesDTO
import com.example.betting.data.models.PlayersDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {
    @Headers(
        "x-rapidapi-key: f16208e0c2af6076fac34572f3125944",
        "x-rapidapi-host: v3.football.api-sports.io"
    )
    @GET("leagues")
    fun getLeagues(
        @Query("name") name: String,
        @Query("season") season: String
    ) : Single<LeaguesDTO>

    @Headers(
        "x-rapidapi-key: f16208e0c2af6076fac34572f3125944",
        "x-rapidapi-host: v3.football.api-sports.io"
    )
    @GET("players")
    fun getPlayers(
        @Query("league") league: String,
        @Query("season") season: String,
        @Query("page") page: String
    ) : Single<PlayersDTO>
}