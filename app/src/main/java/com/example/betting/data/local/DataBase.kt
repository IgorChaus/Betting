package com.example.betting.data.local

import com.example.betting.data.models.PlayerEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object DataBase {
    private val configuration = RealmConfiguration.create(schema = setOf(PlayerEntity::class))
    val realmInstance = Realm.open(configuration)
}