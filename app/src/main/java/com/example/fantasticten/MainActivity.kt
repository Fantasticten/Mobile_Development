package com.example.fantasticten

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fantasticten.databinding.ActivityMainBinding
import com.example.fantasticten.fragment.HomeFragment
import com.example.fantasticten.fragment.JadwalFragment
import com.example.fantasticten.fragment.NotifikasiFragment
import com.example.fantasticten.fragment.ProfilFragment
import com.example.fantasticten.utils.NetworkUtils
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private val checkDelay: Long = 7000
    private var isAlertShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkInternetConnection()

        binding.bottomBar.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomBar.itemIconTintList = createColorStateList()
        binding.bottomBar.itemTextColor = createColorStateList()

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.jadwal -> replaceFragment(JadwalFragment())
                R.id.notifikasi -> replaceFragment(NotifikasiFragment())
                R.id.profil -> replaceFragment(ProfilFragment())
                else -> {}
            }
            true
        }

        binding.frameLayout.setOnTouchListener(hide_nav(this, binding.bottomBar))

        val sharedPreferences = getSharedPreferences("NavigationPrefs", Context.MODE_PRIVATE)
        val isProfileNavigationNeeded = sharedPreferences.getBoolean("notificationNavigationNeeded", false)

        if (isProfileNavigationNeeded) {
            binding.bottomBar.selectedItemId = R.id.notifikasi
            sharedPreferences.edit().putBoolean("notificationNavigationNeeded", false).apply()
        } else {
            binding.bottomBar.selectedItemId = R.id.home
        }
    }

    private fun createColorStateList(): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.hijau),
            ContextCompat.getColor(this, R.color.abu)
        )
        return ColorStateList(states, colors)
    }

    override fun onResume() {
        super.onResume()
        startCheckingInternet()
    }

    override fun onPause() {
        super.onPause()
        stopCheckingInternet()
    }

    private fun checkInternetConnection() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog()
        } else {
            if (isAlertShowing) {
                isAlertShowing = false
            }
        }
    }

    private fun showNoInternetDialog() {
        if (!isAlertShowing) {
            isAlertShowing = true
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Tidak Ada Koneksi Internet")
            builder.setMessage("Mohon periksa koneksi internet Anda dan coba lagi.")
            builder.setIcon(R.drawable.no_internet)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                isAlertShowing = false
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    private fun startCheckingInternet() {
        handler.postDelayed(checkInternetRunnable, checkDelay)
    }

    private fun stopCheckingInternet() {
        handler.removeCallbacks(checkInternetRunnable)
    }

    private val checkInternetRunnable = object : Runnable {
        override fun run() {
            checkInternetConnection()
            handler.postDelayed(this, checkDelay)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}

