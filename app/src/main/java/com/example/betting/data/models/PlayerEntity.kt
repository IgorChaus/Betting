package com.example.betting.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritePlayers")
data class PlayerEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val birthPlace: String?,
    val birthCountry: String?,
    val birthDate: String?,
    val nationality: String?,
    val height: String?,
    val weight: String?,
    val photo: String?,
    val team: String?,
    val leagueName: String?,
    val leagueLogo: String?
)