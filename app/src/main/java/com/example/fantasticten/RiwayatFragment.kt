package com.example.fantasticten

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val service = APIService.getService()
        val call = service.getPatientHistory(1) // Ganti 1 dengan ID yang sesuai

        call.enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(call: Call<ResponseRiwayat>, response: Response<ResponseRiwayat>) {
                if (response.isSuccessful) {
                    val data = response.body()?.completedPatients ?: emptyList<CompletedPatientsItem>()

                    Log.d("RiwayatFragment", "Data from API: $data")

                    adapter.setData(data.filterNotNull()) // Pastikan method setData di dalam adapter disesuaikan
                } else {
                    // Handle unsuccessful response
                    Log.e("RiwayatFragment", "Unsuccessful API response: ${response.code()}")
                }
            }


            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e("RiwayatFragment", "API Call Failed: ${t.message}", t)
                // Handle network request failure
            }
        })

    }
}
