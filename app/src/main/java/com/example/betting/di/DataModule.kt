package com.example.betting.di

import android.app.Application
import com.example.betting.database.AppDataBase
import com.example.betting.database.FavoritePlayersDao
import com.example.betting.network.RetrofitApi
import com.example.betting.network.RetrofitInstance
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePersonApi(): RetrofitApi {
        return RetrofitInstance.service
    }

    companion object {

        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): FavoritePlayersDao {
            return AppDataBase.getInstance(application).favoritePlayersDao()
        }
    }

}