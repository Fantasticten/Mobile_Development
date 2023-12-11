package com.example.fantasticten

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.adapter.RiwayatAdapter
import com.example.fantasticten.data.CompletedPatientsItem
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayat, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        adapter = RiwayatAdapter { itemId ->
            // Handle item click if needed
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fetchData()

        return view
    }


    private fun fetchData() {
        val sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", 1)


        val service = APIService.getService()
        val call = service.getPatientHistory(userId)

        call.enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(call: Call<ResponseRiwayat>, response: Response<ResponseRiwayat>) {
                if (response.isSuccessful) {
                    val data = response.body()?.completedPatients ?: emptyList<CompletedPatientsItem>()

                    Log.d("RiwayatFragment", "Data from API: $data")

                    adapter.setData(data.filterNotNull())
                } else {
                    Log.e("RiwayatFragment", "Unsuccessful API response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e("RiwayatFragment", "API Call Failed: ${t.message}", t)
            }
        })
    }

}
