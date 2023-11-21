package com.example.fantasticten.home_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import com.example.fantasticten.R
import com.example.fantasticten.adapter.grid_adapter
import com.example.fantasticten.grid_item

class Program : AppCompatActivity() , AdapterView.OnItemClickListener{
    private lateinit var gridView: GridView
    private lateinit var arrayList: ArrayList<grid_item>
    private lateinit var gridAdapter: grid_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        gridView = findViewById(R.id.gridview)
        arrayList = ArrayList()
        arrayList= setdataList()
        gridAdapter = grid_adapter(applicationContext,arrayList!!)
        gridView?.adapter =gridAdapter
        gridView?.onItemClickListener = this
    }
    private fun setdataList():ArrayList<grid_item>{

        var arrayList : ArrayList<grid_item> = ArrayList()
        arrayList.add(grid_item(R.drawable.tableprogram,"Free"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Free"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 50.000 - 100.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 150.000 - 350.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 100.000 - 150.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 200.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 1Jt - 2Jt"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 220.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 230.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 240.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 250.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 260.000 - 500.000"))
        arrayList.add(grid_item(R.drawable.tableprogram,"Rp 100.000 - 500.000"))
        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var gridItem :grid_item =arrayList!!.get(position)
        Toast.makeText(applicationContext,gridItem.name,Toast.LENGTH_SHORT).show()
    }
}