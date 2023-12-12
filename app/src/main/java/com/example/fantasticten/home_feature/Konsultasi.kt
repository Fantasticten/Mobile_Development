package com.example.fantasticten.home_feature

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fantasticten.R
import com.example.fantasticten.home_feature.chat.ChatAktivity

class Konsultasi : AppCompatActivity() {
    private lateinit var chat_button : Button
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsultasi)
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        chat_button = findViewById(R.id.buttonkonsultasi)

        chat_button.setOnClickListener {

            val inten = Intent(this,ChatAktivity::class.java)
            startActivity(inten)
        }
    }
}