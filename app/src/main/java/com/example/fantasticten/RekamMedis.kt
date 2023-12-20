package com.example.fantasticten

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.fantasticten.databinding.ActivityRekamMedisBinding

class RekamMedis : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityRekamMedisBinding
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRekamMedisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")
        val userEmail = sharedPreferences.getString("email", "")

        val nameTextView: TextView = binding.namaUser
        val emailTextView: TextView = binding.emailuser

        nameTextView.text = userName
        emailTextView.text = userEmail

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
