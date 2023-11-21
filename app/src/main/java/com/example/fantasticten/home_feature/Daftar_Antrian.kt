package com.example.fantasticten.home_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.fantasticten.R

class Daftar_Antrian : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_antrian)
        val item = listOf("Kosdultasi Gigi","Kosdultasi Gigi","Kosdultasi Gigi","Kosdultasi Gigi","Kosdultasi Gigi","Kosdultasi Gigi","Kosdultasi Gigi",)
        val dropDown :AutoCompleteTextView = findViewById(R.id.dropdon)
        val  adpter = ArrayAdapter(this,R.layout.list_pelayanan,item)
        dropDown.setAdapter(adpter)
        dropDown.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView,view,i,l ->
            val itemSelected = adapterView?.getItemAtPosition(i)
            Toast.makeText(this,"Item $itemSelected",Toast.LENGTH_SHORT).show()
        }
    }
}