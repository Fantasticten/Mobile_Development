package com.example.fantasticten.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.fantasticten.R
import com.example.fantasticten.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class JadwalFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jadwal, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager2 = view.findViewById(R.id.view_pager)

        tabLayout.addTab(tabLayout.newTab().setText("Kunjungan"))
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat"))

        val fragmentManager = requireActivity().supportFragmentManager
        adapter = ViewPagerAdapter(fragmentManager, viewLifecycleOwner.lifecycle)
        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        return view
    }
}
