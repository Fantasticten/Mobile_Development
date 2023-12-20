package com.example.fantasticten

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fantasticten.data.DataRekamMedis
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class RekamMedis : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekam_medis)

        val id = 1 // Replace with the actual ID you want to fetch

        GlobalScope.launch(Dispatchers.Main) {
            fetchDataById(id)
        }
    }

    private suspend fun fetchDataById(rekamMedisId: Int) {
        val apiService = APIService.getService()

        try {
            val response: Response<DataRekamMedis> = apiService.getRekamMedisById(rekamMedisId)

            if (response.isSuccessful) {
                val rekamMedisData: DataRekamMedis? = response.body()
                Log.d("RekamMedisActivity", "Data retrieved successfully: $rekamMedisData")

                // Set data to TextViews
                setTextViewData(rekamMedisData)

                // TODO: Handle the retrieved data here
            } else {
                Log.e("RekamMedisActivity", "Error: ${response.code()}, ${response.message()}")
                // TODO: Handle the error case
            }
        } catch (e: Exception) {
            Log.e("RekamMedisActivity", "Exception: ${e.message}", e)
            // TODO: Handle exceptions, such as network errors
        }
    }

    private fun setTextViewData(rekamMedisData: DataRekamMedis?) {
        // Set the data to the TextViews dynamically
        findViewById<TextView>(R.id.kederekam_medis).text = rekamMedisData?.kode_rekam_medis ?: "N/A"
        findViewById<TextView>(R.id.layanan).text = rekamMedisData?.keluhan ?: "N/A"
        findViewById<TextView>(R.id.tindakan).text = rekamMedisData?.tindakan ?: "N/A"
        findViewById<TextView>(R.id.keterangan).text = rekamMedisData?.keterangan ?: "N/A"

    }
}
