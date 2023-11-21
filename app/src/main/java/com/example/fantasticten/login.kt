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

        findViewById<TextView>(R.id.daftarLog).setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        findViewById<TextView>(R.id.lupaSan).setOnClickListener {
            startActivity(Intent(this, Lupa_Sandi::class.java))
        }

        findViewById<Button>(R.id.masukAkun).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
