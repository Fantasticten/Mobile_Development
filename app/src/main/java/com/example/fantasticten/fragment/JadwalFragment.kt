package com.example.fantasticten.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fantasticten.DetailArtikelActivity
import com.example.fantasticten.R
import com.example.fantasticten.ViewPagerAdapter
import com.example.fantasticten.databinding.FragmentJadwalBinding
import com.google.android.material.tabs.TabLayout

class JadwalFragment : Fragment(R.layout.fragment_jadwal) {

    private lateinit var binding: FragmentJadwalBinding
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJadwalBinding.bind(view)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val namaUser = binding.namaUserJa
        val emailUser = binding.emailUser
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")

        namaUser.text = "$username"
        emailUser.text = "$email"

        binding.tabLayout.apply {
            addTab(newTab().setText("Kunjungan"))
            addTab(newTab().setText("Riwayat"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        val fragmentManager = requireActivity().supportFragmentManager
        adapter = ViewPagerAdapter(fragmentManager, viewLifecycleOwner.lifecycle)
        binding.viewPager.adapter = adapter
    }

}
