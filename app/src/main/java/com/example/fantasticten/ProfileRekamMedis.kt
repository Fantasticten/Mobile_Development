package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ProfileRekamMedis : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_rekam_medis)
        imageView = findViewById(R.id.btnRkmMedis)
        imageView.setOnClickListener{
            onBackPressed()
        }
    }
}