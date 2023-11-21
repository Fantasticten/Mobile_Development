package com.example.fantasticten

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.databinding.RiwayatItemBinding


class RiwayatAdafter(private val list: List<RiwayatModels>, private val context: Context) :
    RecyclerView.Adapter<RiwayatAdafter.ViewHolder>() {

    class ViewHolder(val binding: RiwayatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RiwayatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tvDate.text = date
                binding.tvDoctor.text = doctor
                binding.tvAction.text = action

                binding.button18.setOnClickListener {
                    // Memulai aktivitas RekamMedis dan meneruskan data
                    val intent = Intent(context, RekamMedis::class.java)
                    intent.putExtra("date", date)
                    intent.putExtra("doctor", doctor)
                    intent.putExtra("action", action)
                    context.startActivity(intent)
                }
            }
        }
    }
}
