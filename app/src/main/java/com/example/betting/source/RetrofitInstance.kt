package com.example.betting.source

import com.example.betting.api.RetrofitApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private const val BASE_URL = "https://v3.football.api-sports.io/"

    val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = builder.create(RetrofitApi::class.java)

}