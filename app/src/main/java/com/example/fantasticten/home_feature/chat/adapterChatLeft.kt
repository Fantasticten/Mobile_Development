package com.example.fantasticten.home_feature.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R

class adapterChatLeft(private val  clist1: List<mobileChatLeft>): RecyclerView.Adapter<adapterChatLeft.MyViewHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chay_left,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chatItem = clist1[position]
        holder.chat.text= chatItem.chatMobile1
    }

    override fun getItemCount(): Int {
        return clist1.size
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val chat : TextView = itemView.findViewById(R.id.textView17)


    }
}