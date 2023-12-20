package com.example.fantasticten.home_feature

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.example.fantasticten.R
import com.example.fantasticten.adapter.grid_adapter
import com.example.fantasticten.data.ProgramData
import com.example.fantasticten.grid_item
import com.example.fantasticten.utils.APIComsumer
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class Program : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var gridView: GridView
    private lateinit var programList: ArrayList<ProgramData>
    private lateinit var gridAdapter: grid_adapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        gridView = findViewById(R.id.gridview)
        programList = ArrayList()
        gridAdapter = grid_adapter(applicationContext, programList)
        gridView.adapter = gridAdapter
        gridView.onItemClickListener = this

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val token = sharedPreferences.getString("token", "")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val token = token ?: ""

                if (token.isEmpty()) {

                    Log.e("API_ERROR", "Token is empty")
                    showToast("Token is empty")
                } else {

                    val apiService = APIService.getService(token)

                    val response = apiService.getPrograms()

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val programs = response.body()?.programs
                            programs?.let {
                                programList.addAll(it)
                                gridAdapter.notifyDataSetChanged()
                            }
                        } else {
                            val errorMessage = response.errorBody()?.string()
                            Log.e("API_ERROR", errorMessage ?: "Unknown error")
                            showToast("Failed to fetch program data from API: $errorMessage")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}")
                showToast("An error occurred: ${e.message}")
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val programItem: ProgramData = programList[position]
        showToast(programItem.nama_program)
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
