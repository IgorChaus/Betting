package com.example.betting.di

import com.example.betting.api.RetrofitApi
import com.example.betting.source.RetrofitInstance
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePersonApi(): RetrofitApi {
        return RetrofitInstance.service
    }
}