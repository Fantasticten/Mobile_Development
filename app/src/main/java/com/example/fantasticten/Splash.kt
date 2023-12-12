package com.example.fantasticten

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)

            val isLoggedIn = checkLoginStatus()

            val nextActivity = if (isLoggedIn) {
                MainActivity::class.java
            } else {
                login::class.java
            }

            startActivity(Intent(this@Splash, nextActivity))
            finish()
        }
    }

    private fun checkLoginStatus(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
