package com.example.betting.data.models

data class PlayersDTO(
    val response: List<PlayerData>
) {

    data class PlayerData(
        val player: Player,
        val statistics: List<Statistics>
    )

    data class Player(
        val id: Int,
        val name: String,
        val firstname: String,
        val lastname: String,
        val age: Int,
        val birth: Birth,
        val nationality: String,
        val height: String,
        val weight: String,
        val photo: String
    )

    data class Birth(
        val date: String,
        val place: String,
        val country: String
    )

    data class Statistics(
        val team: Team,
    )

    data class Team(
        val name: String,
        val logo: String
    )

}