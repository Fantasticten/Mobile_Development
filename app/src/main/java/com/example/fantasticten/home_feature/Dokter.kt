package com.example.fantasticten.home_feature

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fantasticten.R
import com.example.fantasticten.data.Doctor
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Dokter : AppCompatActivity() {
    private lateinit var namadokter: TextView
    private lateinit var spelesiasi: TextView
    private lateinit var infoklinik: TextView
    private lateinit var jadwalPraktek: TextView
    private lateinit var imageViewDokter: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dokter)

        namadokter = findViewById(R.id.namadokter)
        spelesiasi = findViewById(R.id.spelesiasi)
        infoklinik = findViewById(R.id.infoklinik)
        jadwalPraktek = findViewById(R.id.jadwal_praktek)
        imageViewDokter = findViewById(R.id.imageViewdokter)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val token = sharedPreferences.getString("token", "")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val userToken = token ?: ""

                if (userToken.isEmpty()) {
                    Log.e("API_ERROR", "Token is empty")
                } else {
                    val apiService = APIService.getService(userToken)
                    val response = apiService.getDoctors()

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val doctor = response.body()?.doctors?.get(0)
                            doctor?.let { displayDoctorInfo(it) }
                        } else {
                            Log.e("API Error", "Failed to fetch data: ${response.code()}")
                            Log.e("API Error", "Response body: ${response.errorBody()?.string()}")
                            Toast.makeText(
                                this@Dokter,
                                "Failed to fetch data from API",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("API Error", "An error occurred: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Dokter,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun displayDoctorInfo(doctor: Doctor) {
        namadokter.text = doctor.nama_dokter
        spelesiasi.text = doctor.spesialisasi
        infoklinik.text = doctor.info_klinik

        val jadwalPraktekText = "${doctor.jadwal_praktek.replace("\\s\\d", " - ")}"
        jadwalPraktek.text = jadwalPraktekText


        Glide.with(this)
            .load("https://keydentalcare.isepwebtim.my.id/img/${doctor.img_dokter}")
            .into(imageViewDokter)
    }
}







