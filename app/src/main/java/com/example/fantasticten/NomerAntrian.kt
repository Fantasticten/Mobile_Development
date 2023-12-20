package com.example.fantasticten

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.fantasticten.data.ResponseKunjung
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class NomerAntrian : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nomer_antrian)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        fetchPatientKunjungan()
    }

    @SuppressLint("SetTextI18n")
    private fun bindDataToViews(responseKunjungan: ResponseKunjung) {
        val textViewNama: TextView = findViewById(R.id.namaPasien)
        val textViewPelayanan: TextView = findViewById(R.id.pelayananPasien)
        val textViewNomorTelepon: TextView = findViewById(R.id.nomerHpPasien)
        val textViewJadwal: TextView = findViewById(R.id.jadwalPasien)
        val textViewWaktu: TextView = findViewById(R.id.waktuPasien)

        val approvedPatients = responseKunjungan.approvedPatients

        if (approvedPatients != null) {

            val originalDate = approvedPatients.hariTanggal ?: ""
            val formattedDate = formatDateFromDatabase(originalDate)

            val waktu = approvedPatients.jamKunjungan ?: ""
            val noAntrian = approvedPatients.kodeAntrian ?: ""

            textViewNama.text = approvedPatients.nama ?: ""
            textViewPelayanan.text = approvedPatients.layanan ?: ""
            textViewNomorTelepon.text = approvedPatients.nomorTelepon ?: ""
            textViewJadwal.text = formattedDate
            textViewWaktu.text = "${noAntrian} / ${waktu}"

        } else {

            textViewNama.text = ""
            textViewPelayanan.text = ""
            textViewNomorTelepon.text = ""
            textViewJadwal.text = ""
            textViewWaktu.text = ""
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
                        bindDataToViews(it)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseKunjung>, t: Throwable) {

            }
        })
    }

    fun formatDateFromDatabase(dateFromDatabase: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE / dd-MM-yyyy", Locale.getDefault())

        val date = inputFormat.parse(dateFromDatabase)
        return outputFormat.format(date!!)
    }
}
