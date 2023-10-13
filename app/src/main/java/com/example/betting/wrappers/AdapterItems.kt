package com.example.betting.wrappers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface AdapterItems

@Parcelize
data class PlayerItem(
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
): AdapterItems, Parcelable

data class LeagueItem(
    val name: String,
    val logo: String
): AdapterItems

