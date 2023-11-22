package com.example.fantasticten

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Lupa_Sandi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lupa_sandi)

        val verify: Button = findViewById(R.id.verify)
        val masuk: TextView = findViewById(R.id.masukLup)

        verify.setOnClickListener {
            val intent = Intent(this, Verifikasi::class.java)
            startActivity(intent)
        }

        masuk.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}