package com.example.betting.domain.models

import android.os.Parcelable
import com.example.betting.presentation.adapter.PlayerListAdapter
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
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
): PlayerListAdapter.AdapterItems, Parcelable


