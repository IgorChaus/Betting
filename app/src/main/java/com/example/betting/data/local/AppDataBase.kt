package com.example.betting.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.betting.data.models.PlayerItemDbModel

@Database(entities = [PlayerItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    companion object{
        private var INSTANCE: AppDataBase? = null
        private var LOCK = Any()
        private const val DB_NAME = "favoritePlayers.db"

        fun getInstance(context: Context): AppDataBase {

            INSTANCE?.let{
                return it
            }

            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }

    abstract fun appDao(): AppDao
}
