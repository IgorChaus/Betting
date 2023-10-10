package com.example.betting.model

data class PlayerItem(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val birthPlace: String,
    val birthCountry: String,
    val birthDate: String,
    val nationality: String,
    val height: String,
    val weight: String,
    val injured: Boolean,
    val photo: String,
    val team: String,
    val leagueName: String,
    val leagueLogo: String,
    val country: String
)