package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PusatBantuan : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pusat_bantuan)
        imageView = findViewById(R.id.bckpusatBantuan)
        imageView.setOnClickListener{
            onBackPressed()
        }

    }
}