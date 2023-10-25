package com.example.betting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.betting.view.SplashScreen
import com.example.betting.view.WelcomeScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val prefs = getSharedPreferences("com.example.betting", MODE_PRIVATE)
            if (prefs.getBoolean(FIRST_LAUNCH, true)) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_activity, SplashScreen.getInstance())
                    .commit()
                prefs.edit().putBoolean(FIRST_LAUNCH, false).apply()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_activity, WelcomeScreen.getInstance())
                    .commit()
            }
        }
    }


    companion object{
        private const val FIRST_LAUNCH = "first_launch"
    }
}