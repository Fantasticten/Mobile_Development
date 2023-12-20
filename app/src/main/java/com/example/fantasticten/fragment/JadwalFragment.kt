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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.fantasticten.DetailArtikelActivity
import com.example.fantasticten.R
import com.example.fantasticten.ViewPagerAdapter
import com.example.fantasticten.adapter.RiwayatAdapter
import com.example.fantasticten.data.ResponseKunjung
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.databinding.FragmentJadwalBinding
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class JadwalFragment : Fragment(R.layout.fragment_jadwal) {

    private lateinit var binding: FragmentJadwalBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var emptyStateLayout: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJadwalBinding.bind(view)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        emptyStateLayout = view.findViewById(R.id.emptyStateJadwal)

        val namaUser = binding.namaUserJa
        val emailUser = binding.emailUser
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")

        namaUser.text = "$username"
        emailUser.text = "$email"

        fetchPatientKunjungan()
    }

    private fun bindDataToViews(responseKunjungan: ResponseKunjung) {

        val textViewNama: TextView = binding.kunjunganText
        val textViewPelayanan: TextView = binding.pukulRiwayat

        val approvedPatients = responseKunjungan.approvedPatients

        if (approvedPatients != null) {

            val originalDate = approvedPatients.hariTanggal ?: ""
            val formattedDate = formatDateFromDatabase(originalDate)

            val tgl = formattedDate+ " di klinik" +
                    " Kei Dental Care"

            textViewNama.text = tgl
            textViewPelayanan.text =  "Pukul " + approvedPatients.jamKunjungan ?: ""

        } else {
            textViewNama.text = ""
            textViewPelayanan.text = ""
        }
    }

    private fun fetchPatientKunjungan() {
        val token = sharedPreferences.getString("token", "")
        val userId = sharedPreferences.getInt("user_id", 1)

        val apiService = APIService.getService(token)

        val call = apiService.getPatientKunjungan(userId)
        call.enqueue(object : Callback<ResponseKunjung> {
            override fun onResponse(call: Call<ResponseKunjung>, response: Response<ResponseKunjung>) {
                if (response.isSuccessful) {
                    val responseKunjungan = response.body()
                    responseKunjungan?.let {
                        if (it.approvedPatients != null && it.approvedPatients != null) {
                            bindDataToViews(it)
                        } else {
                            showEmptyStateLayout()
                        }
                    } ?: run {
                        showEmptyStateLayout()
                    }
                } else {

                    showEmptyStateLayout()
                }
            }

            override fun onFailure(call: Call<ResponseKunjung>, t: Throwable) {

            }
        })
    }

    fun formatDateFromDatabase(dateFromDatabase: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(dateFromDatabase)
        return outputFormat.format(date!!)
    }

    private fun showEmptyStateLayout() {
        emptyStateLayout.visibility = View.VISIBLE
    }


}