package com.example.betting.data.models

import androidx.room.PrimaryKey
import io.realm.kotlin.types.RealmObject

class PlayerEntity : RealmObject{
    @PrimaryKey
    var id: Int = 0
    var firstName: String? = null
    var lastName: String? = null
    var age: Int? = null
    var birthPlace: String? = null
    var birthCountry: String? = null
    var birthDate: String? = null
    var nationality: String? = null
    var height: String? = null
    var weight: String? = null
    var photo: String? = null
    var team: String? = null
    var leagueName: String? = null
    var leagueLogo: String? = null
}
