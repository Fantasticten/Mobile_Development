package com.example.fantasticten

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fantasticten.data.editProfil
import com.example.fantasticten.databinding.ActivityEditProfileBinding
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class Edit_Profile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val apiService = APIService.getService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 1)
        getData()


        binding.btneditprofil.setOnClickListener{
            onBackPressed()
        }
        binding.btnsimpan.setOnClickListener {
            editdata(userId)
        }


    }
    private fun editdata(id :Int) {
        val nama =binding.editTextNamaLengkap.text.toString()
        val tanggal =binding.editTanggal.text.toString()
        val kelamin =binding.editJenisKelamin.text.toString()
        val notelp =binding.editTextNotelp.text.toString()
        val email =binding.edemail.text.toString()
        val alamat =binding.edalamat.text.toString()
        if (nama.isEmpty()||tanggal.isEmpty()||kelamin.isEmpty()||notelp.isEmpty()||email.isEmpty()||alamat.isEmpty()){
            Toast.makeText(this, "lenkapi semua data", Toast.LENGTH_SHORT).show()
        }else{
            val id = apiService.putUser(id,nama,tanggal,kelamin,notelp,email,alamat)
            id.enqueue(object  : Callback<editProfil> {
                override fun onResponse(call: Call<editProfil>, response: Response<editProfil>) {
                    if (response.isSuccessful){
                        finish()
                        Toast.makeText(this@Edit_Profile, "succes", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@Edit_Profile, "err", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<editProfil>, t: Throwable) {
                    Toast.makeText(this@Edit_Profile, "err", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun getData(){
        val userId = sharedPreferences.getInt("user_id", 1)
            val id = apiService.getUser(userId)
        id.enqueue(object :Callback<editProfil>{
            override fun onResponse(call: Call<editProfil>, response: Response<editProfil>) {
                if (response.isSuccessful){
                    val does = response.body()
                    does?.let {
                        binding.editTextNamaLengkap.setText(does.ususu?.username)
                        binding.editTanggal.setText(does.ususu?.tanggalLahir)
                        binding.editJenisKelamin.setText(does.ususu?.jenisKelamin)
                        binding.editTextNotelp.setText(does.ususu?.phoneNumber)
                        binding.edemail.setText(does.ususu?.email)
                        binding.edalamat.setText(does.ususu?.alamat)
                    }
                }else{
                    Toast.makeText(this@Edit_Profile, "err", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<editProfil>, t: Throwable) {
                Toast.makeText(this@Edit_Profile, "eroooooooor", Toast.LENGTH_SHORT).show()
            }
        })
        }
}


