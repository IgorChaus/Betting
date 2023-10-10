package com.example.betting.model

import com.google.gson.annotations.SerializedName

data class LeaguesResponse (
    val request: String,
    val parameters: Parameters,
    val errors: List<String>,
    val results: Int,
    val paging: Paging,
    val response: List<LeagueItem>
){

    data class Paging(
        val current: Int,
        val total: Int
    )

    data class Parameters(
        val name: String
    )

    data class LeagueItem(
        val league: League,
        val country: Country,
        val seasons: List<Season>
    )

    data class League (
        val id: Int,
        val name: String,
        val type: String,
        val logo: String
    )

    data class Country (
        val name: String,
        val code: String,
        val flag: String
    )

    data class Season (
        val year: String,
        val start: String,
        val end: String,
        val current: Boolean,
        val coverage: Coverage
    )

    data class Coverage (
        val fixtures: Fixtures,
        val standings: Boolean,
        val players: Boolean,

        @SerializedName("top_scorers")
        val topScorers: Boolean,

        @SerializedName("top_assists")
        val topAssists: Boolean,

        @SerializedName("top_cards")
        val topCards: Boolean,

        val injuries: Boolean,
        val predictions: Boolean,
        val odds: Boolean
    )

    data class Fixtures (
        val events: Boolean,
        val lineups: Boolean,

        @SerializedName("statistics_fixtures")
        val statisticsFixtures: Boolean,

        @SerializedName("statistics_players")
        val statisticsPlayers: Boolean
    )
}








