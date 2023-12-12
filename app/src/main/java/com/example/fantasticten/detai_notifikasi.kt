package com.example.fantasticten

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class detai_notifikasi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detai_notifikasi)
        val btnPindah: Button = findViewById(R.id.menuju)

        btnPindah.setOnClickListener {
            // Create an Intent to navigate to DataKunjunganActivity
            val intent = Intent(this, NomerAntrian::class.java)

            // You can also pass data to th
            startActivity(intent)
        }
        }
    }
