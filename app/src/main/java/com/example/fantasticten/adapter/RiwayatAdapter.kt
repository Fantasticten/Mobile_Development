package com.example.fantasticten.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.data.CompletedPatientsItem

class RiwayatAdapter(
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    private var dataList = mutableListOf<CompletedPatientsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_riwayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
//        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item.id ?: -1)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(newData: List<CompletedPatientsItem>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tanggal: TextView = itemView.findViewById(R.id.kunjunganText)
        private val jam: TextView = itemView.findViewById(R.id.pukulKunjungan)

//        fun bind(dataItem: CompletedPatientsItem) {
//            hari.text = dataItem.hariTanggal + " di klinik" +
//                    "Kei Dental Care"
//            pukul.text = "Pukul " + dataItem.jamKunjungan
//        }
    }
}