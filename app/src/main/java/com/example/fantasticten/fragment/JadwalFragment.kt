package com.example.fantasticten.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJadwalBinding.bind(view)

        val ar1Button: Button = binding.ar1
        val ar2Button: Button = binding.ar2
        val ar3Button: Button = binding.ar3

        ar1Button.setOnClickListener { startDetailActivity("1") }
        ar2Button.setOnClickListener { startDetailActivity("2") }
        ar3Button.setOnClickListener { startDetailActivity("3") }

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

    private fun startDetailActivity(artikelId: String) {
        val intent = Intent(requireContext(), DetailArtikelActivity::class.java)
        intent.putExtra("ARTIKEL_ID", artikelId)
        startActivity(intent)
    }
}
