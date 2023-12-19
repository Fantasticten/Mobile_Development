package com.example.fantasticten.home_feature.chat



import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase


class adapterChat(private val clist: List<mobileChat>):RecyclerView.Adapter<adapterChat.MyViewHolder>()
     {
         private lateinit var sharedPreferences: SharedPreferences
         var firebaseUser  = FirebaseUser.NULL
         val messege_right= 0
         val messege_left= 1
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
             if (viewType == messege_right) {
                 val itemView =
                     LayoutInflater.from(parent.context).inflate(R.layout.chat_right, parent, false)
                 return MyViewHolder(itemView)
             } else {
                 val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_left,parent,false)
                 return MyViewHolder(itemView)
             }

         }

         override fun onBindViewHolder(holder: adapterChat.MyViewHolder, position: Int) {
            val chatItem = clist[position]
             holder.chat.text= chatItem.chatMobile
             val  byte = android.util.Base64.decode(chatItem.chatImage,android.util.Base64.DEFAULT)
             val bitmap = BitmapFactory.decodeByteArray(byte,0,byte.size)
             holder.chatImage.setImageBitmap(bitmap)

         }

         override fun getItemCount(): Int {
             return clist.size
         }
         class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
             val chat : TextView = itemView.findViewById(R.id.textView18)
             val chatImage : ImageView = itemView.findViewById(R.id.imageView5)


         }

         override fun getItemViewType(position: Int): Int {
             if (clist[position].senderId == firebaseUser) {
                 return messege_left
             } else {
                 return messege_right
             }

         }
     }