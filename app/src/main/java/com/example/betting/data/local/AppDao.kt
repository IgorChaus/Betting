package com.example.betting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.betting.data.models.PlayerItemDbModel


@Dao
interface AppDao {

    @Query("SELECT * FROM favoritePlayers")
    fun getPlayersList(): LiveData<List<PlayerItemDbModel>>

    @Query("SELECT * FROM favoritePlayers WHERE id = :id")
    suspend fun getPlayer(id: Int): PlayerItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(playerItemDbModel: PlayerItemDbModel)

    @Query("DELETE FROM favoritePlayers WHERE id = :id")
    suspend fun deletePlayer(id: Int)
}
