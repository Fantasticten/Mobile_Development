package com.example.fantasticten

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.core.text.set
import com.example.fantasticten.data.editProfil
import com.example.fantasticten.databinding.ActivityChatAktivityBinding
import com.example.fantasticten.databinding.ActivityEditProfileBinding
import com.example.fantasticten.utils.APIService
import com.google.android.gms.common.api.Response

class Edit_Profile : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val apiService = APIService.getService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btneditprofil.setOnClickListener{
            onBackPressed()
        }
        binding.btneditprofil.setOnClickListener {
            editdata()
        }


    }

    private fun editdata() {


    }
    private fun getData(){
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 1)
        apiService.getUser(userId)
        binding.editTextNamaLengkap.setText("")
    }
}