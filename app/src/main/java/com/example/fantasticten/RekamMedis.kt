package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.fantasticten.databinding.ActivityRekamMedisBinding

class RekamMedis : AppCompatActivity() {

    private lateinit var binding: ActivityRekamMedisBinding
    private lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRekamMedisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageView = binding.btnrekammedisR
        imageView.setOnClickListener {
            onBackPressed()
        }
        val date = intent.getStringExtra("date")
        val doctor = intent.getStringExtra("doctor")
        val action = intent.getStringExtra("action")


        binding.tvDate.text = date
        binding.tvDoctor.text = doctor
        binding.tvAction.text = action
    }
}