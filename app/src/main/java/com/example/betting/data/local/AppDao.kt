package com.example.betting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betting.data.models.PlayerEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {

    @Query("SELECT * FROM favoritePlayers")
    fun getPlayersList(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM favoritePlayers WHERE id = :id")
    suspend fun getPlayer(id: Int): PlayerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(playerEntity: PlayerEntity)

    @Query("DELETE FROM favoritePlayers WHERE id = :id")
    suspend fun deletePlayer(id: Int)
}
