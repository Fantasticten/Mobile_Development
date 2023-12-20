package com.example.fantasticten.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.fantasticten.R
import com.example.fantasticten.data.ProgramData
import com.example.fantasticten.grid_item
import java.security.AccessControlContext

class grid_adapter (var context: Context, var arrayList: ArrayList<ProgramData>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return arrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.grid_item, null)
        val icon: ImageView = view.findViewById(R.id.imageViewprogram)
        val text: TextView = view.findViewById(R.id.textViewpageprogram)
        val programItem: ProgramData = arrayList[position]
        Glide.with(context)
            .load("https://keydentalcare.isepwebtim.my.id/img/${programItem.thumbnail}")
            .placeholder(R.drawable.program)
            .error(R.drawable.program)
            .into(icon)
        text.text = programItem.harga_program
        return view
    }
}