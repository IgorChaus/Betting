package com.example.betting.data.models

data class LeaguesDTO (
    val response: List<LeagueItem>
){
    data class LeagueItem(
        val league: League,
    )

    data class League (
        val id: Int,
        val name: String,
        val logo: String
    )
}








