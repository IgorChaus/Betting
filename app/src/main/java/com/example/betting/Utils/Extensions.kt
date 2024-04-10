package com.example.betting.Utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.betting.data.models.PlayerEntity
import com.example.betting.domain.models.Player

fun Context.hideKeyboard(editText: EditText){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun PlayerEntity.toModel() = Player(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    age = this.age,
    birthPlace = this.birthPlace,
    birthCountry = this.birthCountry,
    birthDate = this.birthDate,
    nationality = this.nationality,
    height = this.height,
    weight = this.weight,
    photo = this.photo,
    team = this.team,
    leagueName = this.leagueName,
    leagueLogo = this.leagueLogo
)

fun Player.toEntity() = PlayerEntity(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    age = this.age,
    birthPlace = this.birthPlace,
    birthCountry = this.birthCountry,
    birthDate = this.birthDate,
    nationality = this.nationality,
    height = this.height,
    weight = this.weight,
    photo = this.photo,
    team = this.team,
    leagueName = this.leagueName,
    leagueLogo = this.leagueLogo
)

fun List<PlayerEntity>.toModel() = this.map{ it.toModel() }