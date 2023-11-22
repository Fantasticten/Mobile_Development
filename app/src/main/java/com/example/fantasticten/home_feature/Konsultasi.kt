package com.example.fantasticten.home_feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fantasticten.R
import com.example.fantasticten.home_feature.chat.ChatAktivity

class Konsultasi : AppCompatActivity() {
    private lateinit var chat_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konsultasi)

        chat_button = findViewById(R.id.buttonkonsultasi)

        chat_button.setOnClickListener {
            val inten = Intent(this,ChatAktivity::class.java)
            startActivity(inten)
        }
    }
}