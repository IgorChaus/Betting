package com.example.betting.di

import com.example.betting.data.remote.RetrofitApi
import com.example.betting.data.remote.RetrofitInstance
import com.example.betting.data.repositories.AppRepositoryImpl
import com.example.betting.domain.repositories.AppRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRetrofitApi(): RetrofitApi {
        return RetrofitInstance.service
    }

//    @Provides
//    @ApplicationScope
//    fun provideAppDao(
//        context: Context
//    ): AppDao {
//        return AppDataBase.getInstance(context).appDao()
//    }

    @Provides
    fun provideAppRepository(service: RetrofitApi): AppRepository{
        return AppRepositoryImpl(service)
    }

}