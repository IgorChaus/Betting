package com.example.betting

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = getSharedPreferences("com.example.betting", MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(FIRST_LAUNCH, true)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity, SplashScreen.getInstance())
                .commit()
            prefs.edit().putBoolean(FIRST_LAUNCH, false).apply()
        }else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_activity, WelcomeScreen.getInstance())
                .commit()
        }
    }

    companion object{
        private const val FIRST_LAUNCH = "first_launch"
    }
}