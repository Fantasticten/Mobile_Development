package com.example.fantasticten

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.RiwayatPAdapter
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.databinding.ActivityRiwayatBinding
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var riwayatAdapter: RiwayatPAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        imageView = binding.btnrwytkunjungan
        imageView.setOnClickListener {
            onBackPressed()
        }
        setupRecyclerView()
        loadDataFromApi()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this@RiwayatActivity)
        riwayatAdapter = RiwayatPAdapter(
            onItemClick = { itemId ->

            },
            onLihatRekamMedisClick = {

                val intent = Intent(this@RiwayatActivity, RekamMedis::class.java)
                startActivity(intent)
            }
        )
        binding.rvHistory.adapter = riwayatAdapter
    }

    private fun loadDataFromApi() {
        val apiService = APIService.getService()
        val userId = sharedPreferences.getInt("user_id", -1)

        apiService.getPatientHistory(userId).enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(
                call: Call<ResponseRiwayat>,
                response: Response<ResponseRiwayat>
            ) {
                if (response.isSuccessful) {
                    val riwayatList = response.body()?.completedPatients ?: emptyList()
                    riwayatAdapter.setData(riwayatList)
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
            }
        })
    }
}
