package com.example.betting.test

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    val client = OkHttpClient()

    val urlBuilder = "https://v3.football.api-sports.io/leagues".toHttpUrlOrNull()!!.newBuilder()
    urlBuilder.addQueryParameter("name", "PREMIER LEAGUE")
    urlBuilder.addQueryParameter("season", "2023")
    val url = urlBuilder.build().toString()

    val request = Request.Builder()
        .url(url)
        .addHeader("x-rapidapi-key", "f16208e0c2af6076fac34572f3125944")
        .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
        .build()

    val response = client.newCall(request).execute()

    println(response.body?.string())
}