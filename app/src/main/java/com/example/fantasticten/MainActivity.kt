package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fantasticten.databinding.ActivityMainBinding
import com.example.fantasticten.fragment.HomeFragment
import com.example.fantasticten.fragment.JadwalFragment
import com.example.fantasticten.fragment.NotifikasiFragment
import com.example.fantasticten.fragment.ProfilFragment

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.jadwal -> replaceFragment(JadwalFragment())
                R.id.notifikasi -> replaceFragment(NotifikasiFragment())
                R.id.profil -> replaceFragment(ProfilFragment())

                else -> {
                }
            }
            true

        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManajer = supportFragmentManager
        val fragmentTransaction = fragmentManajer.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}