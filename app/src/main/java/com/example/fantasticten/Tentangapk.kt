package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Tentangapk : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentangapk)
        imageView = findViewById(R.id.btntenTang)
        imageView.setOnClickListener{
            onBackPressed()
        }
    }
}