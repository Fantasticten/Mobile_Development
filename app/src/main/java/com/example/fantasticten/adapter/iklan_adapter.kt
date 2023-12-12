
package com.example.fantasticten.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.data.ArtikelData
import com.bumptech.glide.Glide
import com.example.fantasticten.DetailArtikelActivity

class ArtikelAdapter(private val context: Context, private val artikelList: List<ArtikelData>) :
    RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    inner class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewiklan)
        val textViewTitle: TextView = itemView.findViewById(R.id.textView16)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.iklan_item, parent, false)
        return ArtikelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentItem = artikelList[position]

        Glide.with(context)
            .load("https://keydentalcare.isepwebtim.my.id/img/${currentItem.gambar_artikel}")
            .placeholder(R.drawable.artikel2) // Ganti dengan placeholder yang sesuai
            .error(R.drawable.artikel2)
            .into(holder.imageView)

        holder.textViewTitle.text = currentItem.judul_artikel

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailArtikelActivity::class.java)
            intent.putExtra("artikel_id", currentItem.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return artikelList.size
    }
}
