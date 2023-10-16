package com.example.betting

import android.app.Application
import com.example.betting.di.DaggerApplicationComponent

class BettingApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}