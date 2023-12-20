package com.example.fantasticten.home_feature

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.fantasticten.R
import com.example.fantasticten.data.dataTreatment
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Treatment : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isi_tratment)
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")
        val userEmail = sharedPreferences.getString("email", "")

        val nameTextView: TextView = findViewById(R.id.namatratment)
        val emailTextView: TextView = findViewById(R.id.emailtratment)

        nameTextView.text = userName
        emailTextView.text = userEmail
        fetchTreatmentData()
    }

    private fun fetchTreatmentData() {

        val token = sharedPreferences.getString("token", "")

        val apiService = APIService.getService(token)
        val userId =  sharedPreferences.getInt("user_id", -1)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getTreatmentListByUser(userId)

                if (response.isSuccessful) {
                    val treatmentList = response.body()?.treatments ?: emptyList()
                    populateTable(treatmentList)
                } else {
                    Log.e("Treatment", "Failed to fetch treatment data. Code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Treatment", "Exception: ${e.message}")
            }
        }
    }

    private fun populateTable(treatmentList: List<dataTreatment>) {
        val tableLayout: TableLayout = findViewById(R.id.tableLayout)

        for (treatment in treatmentList) {
            val tableRow = TableRow(this)

            val layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            tableRow.layoutParams = layoutParams


            val dateTextView = createTextView(treatment.hari_tanggal)
            tableRow.addView(dateTextView)


            val descriptionTextView = createTextView(treatment.nama_layanan)
            tableRow.addView(descriptionTextView)


            tableLayout.addView(tableRow, TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT))
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        val layoutParams = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            1f
        )
        textView.layoutParams = layoutParams
        textView.text = text
        textView.setBackgroundResource(R.drawable.tblborder)
        textView.gravity = Gravity.START
        textView.setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
        return textView
    }

    private fun Int.dpToPx(): Int {
        val scale: Float = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
