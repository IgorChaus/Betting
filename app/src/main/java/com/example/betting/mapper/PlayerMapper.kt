package com.example.betting.mapper

import com.example.betting.data.models.PlayerItemDbModel
import com.example.betting.domain.models.PlayerAdapterItem
import javax.inject.Inject

class PlayerMapper @Inject constructor(){

    fun mapDbModelToAdapterItem(dbModel: PlayerItemDbModel) = PlayerAdapterItem(
        id = dbModel.id,
        firstName = dbModel.firstName,
        lastName = dbModel.lastName,
        age = dbModel.age,
        birthPlace = dbModel.birthPlace,
        birthCountry = dbModel.birthCountry,
        birthDate = dbModel.birthDate,
        nationality = dbModel.nationality,
        height = dbModel.height,
        weight = dbModel.weight,
        photo = dbModel.photo,
        team = dbModel.team,
        leagueName = dbModel.leagueName,
        leagueLogo = dbModel.leagueLogo
    )

    fun mapAdapterItemToDbModel(adapterItem: PlayerAdapterItem) = PlayerItemDbModel(
        id = adapterItem.id,
        firstName = adapterItem.firstName,
        lastName = adapterItem.lastName,
        age = adapterItem.age,
        birthPlace = adapterItem.birthPlace,
        birthCountry = adapterItem.birthCountry,
        birthDate = adapterItem.birthDate,
        nationality = adapterItem.nationality,
        height = adapterItem.height,
        weight = adapterItem.weight,
        photo = adapterItem.photo,
        team = adapterItem.team,
        leagueName = adapterItem.leagueName,
        leagueLogo = adapterItem.leagueLogo
    )

    fun mapListDbModelToListAdapterItem(list: List<PlayerItemDbModel>) = list.map{
        mapDbModelToAdapterItem(it)
    }

}