package com.example.betting.di

import com.example.betting.data.local.DataBase
import com.example.betting.data.remote.RetrofitApi
import com.example.betting.data.remote.RetrofitInstance
import com.example.betting.data.repositories.AppRepositoryImpl
import com.example.betting.domain.repositories.AppRepository
import dagger.Module
import dagger.Provides
import io.realm.kotlin.Realm

@Module
class DataModule {

    @Provides
    fun provideRetrofitApi(): RetrofitApi {
        return RetrofitInstance.service
    }

    @Provides
    @ApplicationScope
    fun provideRealm(): Realm {
        return DataBase.realmInstance
    }

    @Provides
    fun provideAppRepository(service: RetrofitApi, realmInstance: Realm): AppRepository{
        return AppRepositoryImpl(service, realmInstance)
    }

}