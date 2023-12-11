package com.example.fantasticten.home_feature.chat


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R


//class adapterChat(private val cont : Context,val layoutid : Int ,val  clist: List<mobileChat>):ArrayAdapter<mobileChat>
//    (cont,layoutid,clist) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val layoutInflater :LayoutInflater = LayoutInflater.from(this.context)
//        var view:View = layoutInflater.inflate(layoutid,null)
//        var text : TextView = view.findViewById(R.id.textView18)
//        var  mobileChat  = clist.get(position)
//        text.text= mobileChat.chatMobile
//
//        return view
//    }}
class adapterChat(private val  clist: ArrayList<mobileChat>):RecyclerView.Adapter<adapterChat.MyViewHolder>()
     {


         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
             val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_right,parent,false)
             return MyViewHolder(itemView)

         }

         override fun onBindViewHolder(holder: adapterChat.MyViewHolder, position: Int) {
            val chatItem = clist[position]
             holder.chat.text= chatItem.chatMobile
         }

         override fun getItemCount(): Int {
             return clist.size
         }
         class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
             val chat : TextView = itemView.findViewById(R.id.textView18)


         }
     }