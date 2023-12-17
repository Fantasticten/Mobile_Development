package com.example.fantasticten.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.fantasticten.DetailArtikelActivity
import com.example.fantasticten.R
import com.example.fantasticten.ViewPagerAdapter
import com.example.fantasticten.adapter.RiwayatAdapter
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.databinding.FragmentJadwalBinding
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalFragment : Fragment(R.layout.fragment_jadwal) {

    private lateinit var binding: FragmentJadwalBinding
    private lateinit var riwayatAdapter: RiwayatAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJadwalBinding.bind(view)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)


        val namaUser = binding.namaUserJa
        val emailUser = binding.emailUser
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")

        namaUser.text = "$username"
        emailUser.text = "$email"

        setupRecyclerView()

        loadDataFromApi()
    }

    private fun setupRecyclerView() {
        riwayatAdapter = RiwayatAdapter { itemId ->
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = riwayatAdapter
        }
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
                    Log.e("JadwalFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e("JadwalFragment", "Network error: ${t.message}")
            }
        })
    }
}