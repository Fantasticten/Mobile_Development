package com.example.fantasticten.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.data.NotifikasiItem
import com.example.fantasticten.detai_notifikasi

class NotifAdapter(
    private var dataList: List<NotifikasiItem?>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<NotifAdapter.ViewHolder>() {

    fun updateData(newData: List<NotifikasiItem?>) {
        dataList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notifikasi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        item?.let { it ->
            holder.bind(it)
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, detai_notifikasi::class.java)
                intent.putExtra("NOTIF_ID", it.id ?: -1)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.isiNotifikasiBis)

        fun bind(dataItem: NotifikasiItem) {
            textName.text = dataItem.isiNotifikasi
        }
    }
}
