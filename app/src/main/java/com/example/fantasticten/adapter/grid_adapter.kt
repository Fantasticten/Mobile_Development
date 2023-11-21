package com.example.fantasticten.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fantasticten.R
import com.example.fantasticten.grid_item
import java.security.AccessControlContext

class grid_adapter (var context: Context, var  arrayList: ArrayList<grid_item>): BaseAdapter(){
    override fun getCount(): Int {
        return arrayList.size

    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View = View.inflate(context,R.layout.grid_item,null)

        var icon :ImageView = view.findViewById(R.id.imageViewprogram)
        var text :TextView = view.findViewById(R.id.textViewpageprogram)
        var  gridItem : grid_item = arrayList.get(position)
        icon.setImageResource(gridItem.gambar!!)
        text.text= gridItem.name

        return view
    }


}