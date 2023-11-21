package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class edit_katasandi : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_katasandi)
        imageView = findViewById(R.id.btnedtktasandi)
        imageView.setOnClickListener{
            onBackPressed()
        }
    }
}