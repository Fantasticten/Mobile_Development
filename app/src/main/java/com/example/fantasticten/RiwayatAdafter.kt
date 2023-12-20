package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.data.CompletedPatientsItem

class RiwayatPAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onLihatRekamMedisClick: () -> Unit
) : RecyclerView.Adapter<RiwayatPAdapter.ViewHolder>() {

    private var dataList = mutableListOf<CompletedPatientsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.riwayat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tanggal: TextView = itemView.findViewById(R.id.tvDate)
        private val layanan: TextView = itemView.findViewById(R.id.tvAction)
        private val lihatRekamMedisButton: AppCompatButton = itemView.findViewById(R.id.button18)

        fun bind(dataItem: CompletedPatientsItem) {
            tanggal.text = "${dataItem.hariTanggal} "
            layanan.text = "${dataItem.layanan}"
            lihatRekamMedisButton.setOnClickListener {
                onLihatRekamMedisClick.invoke()
            }
        }
    }
}
