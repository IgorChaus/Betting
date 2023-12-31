package com.example.betting.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.betting.model.PlayerItemDbModel


@Dao
interface FavoritePlayersDao {

    @Query("SELECT * FROM favoritePlayers")
    fun getPlayersList(): LiveData<List<PlayerItemDbModel>>

    @Query("SELECT * FROM favoritePlayers WHERE id = :id")
    suspend fun getPlayer(id: Int): PlayerItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(playerItemDbModel: PlayerItemDbModel)

    @Query("DELETE FROM favoritePlayers WHERE id = :id")
    suspend fun deletePlayer(id: Int)
}
