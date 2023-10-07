package com.example.betting.model

data class LeagueItem (
    val league: League,
    val country: Country,
    val seasons: List<Season>
){
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

        //      @Json(name = "top_scorers")
        val topScorers: Boolean,

        //      @Json(name = "top_assists")
        val topAssists: Boolean,

        //      @Json(name = "top_cards")
        val topCards: Boolean,

        val injuries: Boolean,
        val predictions: Boolean,
        val odds: Boolean
    )

    data class Fixtures (
        val events: Boolean,
        val lineups: Boolean,

        //      @Json(name = "statistics_fixtures")
        val statisticsFixtures: Boolean,

        //      @Json(name = "statistics_players")
        val statisticsPlayers: Boolean
    )
}








