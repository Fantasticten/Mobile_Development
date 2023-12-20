package com.example.fantasticten.home_feature

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fantasticten.MainActivity
import com.example.fantasticten.R
import com.example.fantasticten.data.QueueData
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class Daftar_Antrian : AppCompatActivity() {

    private lateinit var namaLengkapEditText: EditText
    private lateinit var noTlpEditText: EditText
    private lateinit var hariTanggalEditText: EditText
    private lateinit var pelayananDropdown: AutoCompleteTextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_antrian)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        val phoneNumber = sharedPreferences.getString("phone_number", "")

        namaLengkapEditText = findViewById(R.id.editTextNamaLengkap1)
        noTlpEditText = findViewById(R.id.editTextNoTlp1)
        hariTanggalEditText = findViewById(R.id.editTextharitanggal)
        pelayananDropdown = findViewById(R.id.dropdon)


        namaLengkapEditText.setText(username)
        noTlpEditText.setText(phoneNumber)

        hariTanggalEditText.setOnClickListener {
            showDatePicker()
        }

        val item = listOf(
            "Konsultasi Gigi",
            "Tambal Gigi",
            "Pasang Behel"
        )
        val adapter = ArrayAdapter(this, R.layout.list_pelayanan, item)
        pelayananDropdown.setAdapter(adapter)

        val daftarButton = findViewById<Button>(R.id.buttonpelayanan)
        daftarButton.setOnClickListener {
            tambahAntrian()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, yearSelected, monthOfYear, dayOfMonthSelected ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(yearSelected, monthOfYear, dayOfMonthSelected)
                hariTanggalEditText.setText(formatDate(selectedDate.timeInMillis))
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun formatDate(millis: Long): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(millis)
    }

    private fun tambahAntrian() {
        val userId = sharedPreferences.getInt("user_id", 1)
        val namaLengkap = namaLengkapEditText.text.toString()
        val noTlp = noTlpEditText.text.toString()
        val hariTanggal = hariTanggalEditText.text.toString()
        val pelayanan = pelayananDropdown.text.toString()

        if (namaLengkap.isEmpty() || noTlp.isEmpty() || hariTanggal.isEmpty() || pelayanan.isEmpty()) {
            Toast.makeText(this@Daftar_Antrian, "Harap isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = ProgressDialog(this@Daftar_Antrian)
        progressDialog.setMessage("Antrian sedang di proses ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val queueData = QueueData(namaLengkap, userId, pelayanan, noTlp, hariTanggal)

        val token = sharedPreferences.getString("token", "")
        val apiService = APIService.getService(token)

        apiService.addQueue(queueData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()

                if (response.isSuccessful) {
                    val alertDialogBuilder = AlertDialog.Builder(this@Daftar_Antrian)
                    alertDialogBuilder
                        .setTitle("Berhasil Daftar")
                        .setMessage("Silahkan Tunggu Respond Dokter!!\nCek Nomor Antrian Kamu Di Notifikasi")
                        .setIcon(R.drawable.check_circle_24)
                        .setPositiveButton("OK") { dialog, _ ->
                            val intent = Intent(this@Daftar_Antrian, MainActivity::class.java)
                            startActivity(intent)

                            val sharedPreferences = getSharedPreferences("NavigationPrefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putBoolean("notificationNavigationNeeded", true).apply()

                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                } else {
                    Toast.makeText(this@Daftar_Antrian, "Gagal menambahkan antrian", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.dismiss()

                Toast.makeText(this@Daftar_Antrian, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
