package com.example.betting.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.betting.data.models.LeaguesDTO
import com.example.betting.data.models.PlayerEntity
import com.example.betting.domain.models.League
import com.example.betting.domain.models.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

fun LeaguesDTO.toModel() = this.response.map {
    League(
        id = it.league.id,
        name = it.league.name,
        logo = it.league.logo
    )
}

fun <T> Flow<T>.repeatOnCreated(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            this@repeatOnCreated.collect()
        }
    }
}

fun <T> Flow<T>.repeatOnCreated(lifecycleOwner: LifecycleOwner, action: suspend (T) -> Unit) {
    onEach { action(it) }
        .repeatOnCreated(lifecycleOwner)
}