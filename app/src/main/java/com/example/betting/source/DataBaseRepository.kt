package com.example.betting.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.betting.database.FavoritePlayersDao
import com.example.betting.mapper.PlayerMapper
import com.example.betting.wrappers.AdapterItems
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val favoritePlayersDao: FavoritePlayersDao,
    private val mapper: PlayerMapper
) {

    fun getPlayersList(): LiveData<List<AdapterItems>> {
        return favoritePlayersDao.getPlayersList().map {
            mapper.mapListDbModelToListAdapterItem(it)
        }
    }
}