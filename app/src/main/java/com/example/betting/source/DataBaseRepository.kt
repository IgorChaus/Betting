package com.example.betting.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.betting.database.FavoritePlayersDao
import com.example.betting.mapper.PlayerMapper
import com.example.betting.model.PlayerItemDbModel
import com.example.betting.wrappers.AdapterItems
import com.example.betting.wrappers.PlayerItemAdapter
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

    suspend fun isPlayerFavorite(id: Int) : Boolean {
        val favorite = favoritePlayersDao.getPlayer(id)
        Log.i("MyTag", "Favorite $favorite")
        return favorite != null
    }

    suspend fun addPlayer(playerItemAdapter: PlayerItemAdapter){
        val playerItemDbModel = mapper.mapAdapterItemToDbModel(playerItemAdapter)
        favoritePlayersDao.addPlayer(playerItemDbModel)
    }

    suspend fun deletePlayer(id: Int){
        favoritePlayersDao.deletePlayer(id)
    }

}