package com.example.fantasticten.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.fantasticten.DetailArtikelActivity
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

        val ar1Button: Button = view.findViewById(R.id.ar1)
        val ar2Button: Button = view.findViewById(R.id.ar2)
        val ar3Button: Button = view.findViewById(R.id.ar3)

        ar1Button.setOnClickListener {
            startDetailActivity("1")
        }

        ar2Button.setOnClickListener {
            startDetailActivity("2")
        }

        ar3Button.setOnClickListener {
            startDetailActivity("3")
        }



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

    private fun startDetailActivity(artikelId: String) {
        val intent = Intent(requireContext(), DetailArtikelActivity::class.java)
        intent.putExtra("ARTIKEL_ID", artikelId)
        startActivity(intent)
    }
}
