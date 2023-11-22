package com.example.fantasticten.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.grid_item
import com.example.fantasticten.iklan_item

class iklan_adapter (private val newsList: ArrayList<iklan_item>): RecyclerView.Adapter<iklan_adapter.MyViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(artikelId: String)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): iklan_adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.iklan_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: iklan_adapter.MyViewHolder, position: Int) {
        val curentitem = newsList[position]
        holder.image.setImageResource(curentitem.gambar)
        holder.tulis.text = curentitem.tulis

        holder.itemView.setOnClickListener {
            listener?.onItemClick(curentitem.id.toString())
        }

        holder.itemView.tag = curentitem.id

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    inner class MyViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.imageViewiklan)
        val tulis : TextView = itemView.findViewById(R.id.textView16)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val artikelId = newsList[position].id
                    listener?.onItemClick(artikelId.toString())
                }
            }
        }

    }



}