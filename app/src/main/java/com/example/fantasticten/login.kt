package com.example.fantasticten

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val daftarLog: TextView = findViewById(R.id.daftarLog)
        val lupaSan: TextView = findViewById(R.id.lupaSan)
        val masukAkun: Button = findViewById(R.id.masukAkun)

        daftarLog.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        lupaSan.setOnClickListener {
            val intent = Intent(this, Lupa_Sandi::class.java)
            startActivity(intent)
        }

        masukAkun.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}