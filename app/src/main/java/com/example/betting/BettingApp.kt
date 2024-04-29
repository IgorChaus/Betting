package com.example.betting

import android.app.Application
import android.content.Context
import com.example.betting.di.AppComponent
import com.example.betting.di.DaggerAppComponent
import io.realm.kotlin.Realm

class BettingApp: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)

        super.onCreate()
    }
}

// allows to take appComponent from any place with only context
val Context.appComponent: AppComponent
    get() = when (this) {
        is BettingApp -> appComponent
        else -> (this.applicationContext as BettingApp).appComponent
    }