package com.example.betting.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.betting.database.FavoritePlayersDao
import com.example.betting.mapper.PlayerMapper
import com.example.betting.wrappers.PlayerAdapterItem
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val favoritePlayersDao: FavoritePlayersDao,
    private val mapper: PlayerMapper
) {

    fun getPlayersList(): LiveData<List<PlayerAdapterItem>> {
        return favoritePlayersDao.getPlayersList().map {
            mapper.mapListDbModelToListAdapterItem(it)
        }
    }

    suspend fun isPlayerFavorite(id: Int): Boolean {
        val favorite = favoritePlayersDao.getPlayer(id)
        return favorite != null
    }

    suspend fun addPlayer(playerAdapterItem: PlayerAdapterItem) {
        val playerItemDbModel = mapper.mapAdapterItemToDbModel(playerAdapterItem)
        favoritePlayersDao.addPlayer(playerItemDbModel)
    }

    suspend fun deletePlayer(id: Int) {
        favoritePlayersDao.deletePlayer(id)
    }

}