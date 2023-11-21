package com.example.fantasticten

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fantasticten.R
import com.example.fantasticten.RiwayatAdafter
import com.example.fantasticten.RiwayatModels
import com.example.fantasticten.databinding.ActivityRiwayatBinding

    class RiwayatActivity : AppCompatActivity() {

        private lateinit var binding: ActivityRiwayatBinding
        private lateinit var adapter: RiwayatAdafter
        private lateinit var imageView: ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)


            binding = ActivityRiwayatBinding.inflate(layoutInflater)
            setContentView(binding.root)

            imageView = binding.btnrwytkunjungan
            imageView.setOnClickListener {
                onBackPressed()
            }

            init()
            setRvAdapter()
        }

        private fun init() {
            binding.rvHistory.layoutManager = LinearLayoutManager(this@RiwayatActivity)
        }

        private fun setRvAdapter() {
            val dates = resources.getStringArray(R.array.history_dates)
            val doctors = resources.getStringArray(R.array.history_doctors)
            val actions = resources.getStringArray(R.array.history_actions)

            val dataList: MutableList<RiwayatModels> = mutableListOf()

            for (i in dates.indices) {
                dataList.add(RiwayatModels(dates[i], doctors[i], actions[i]))
            }

            adapter = RiwayatAdafter(dataList, this@RiwayatActivity)
            binding.rvHistory.adapter = adapter
        }
    }
