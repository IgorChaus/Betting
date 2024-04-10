package com.example.betting.di

import android.content.Context
import com.example.betting.data.local.AppDao
import com.example.betting.data.local.AppDataBase
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.data.remote.RetrofitInstance
import com.example.betting.data.repositories.AppRepositoryImpl
import com.example.betting.domain.repositories.AppRepository
import com.example.betting.mapper.PlayerMapper
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRetrofitApi(): RetrofitApi {
        return RetrofitInstance.service
    }

    @Provides
    @ApplicationScope
    fun provideAppDao(
        context: Context
    ): AppDao {
        return AppDataBase.getInstance(context).appDao()
    }

    @Provides
    fun provideAppRepository(service: RetrofitApi, appDao: AppDao, mapper: PlayerMapper): AppRepository{
        return AppRepositoryImpl(service, appDao, mapper)
    }

}