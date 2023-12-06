package com.example.fantasticten.home_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import com.example.fantasticten.R
import com.example.fantasticten.adapter.grid_adapter
import com.example.fantasticten.data.ProgramData
import com.example.fantasticten.grid_item
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Program : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var gridView: GridView
    private lateinit var programList: ArrayList<ProgramData>
    private lateinit var gridAdapter: grid_adapter
    private val apiService = APIService.getService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        gridView = findViewById(R.id.gridview)
        programList = ArrayList()
        gridAdapter = grid_adapter(applicationContext, programList)
        gridView.adapter = gridAdapter
        gridView.onItemClickListener = this

        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPrograms()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val programs = response.body()?.programs
                        programs?.let {
                            programList.addAll(it)
                            gridAdapter.notifyDataSetChanged()
                        }
                    } else {
                        showToast("Failed to fetch program data from API")
                    }
                }
            } catch (e: Exception) {
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
