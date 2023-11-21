package com.example.fantasticten

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> RiwayatFragment()
            else -> KunjunganFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
