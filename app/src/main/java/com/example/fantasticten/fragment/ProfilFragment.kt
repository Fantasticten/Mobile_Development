package com.example.fantasticten.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.fantasticten.Edit_Profile
import com.example.fantasticten.ProfileRekamMedis
import com.example.fantasticten.PusatBantuan
import com.example.fantasticten.R
import com.example.fantasticten.RiwayatActivity
import com.example.fantasticten.Splash
import com.example.fantasticten.Tentangapk
import com.example.fantasticten.edit_katasandi
import com.example.fantasticten.login


class ProfilFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val namaUser = view.findViewById<TextView>(R.id.namaUser)
        val username = sharedPreferences.getString("username", "")

        namaUser.text = "$username"

        val edtprofil = view.findViewById<RelativeLayout>(R.id.EdtProfile)
        edtprofil.setOnClickListener {
            val intent = Intent(requireContext(), Edit_Profile::class.java)
            startActivity(intent)
        }
        val rwytkunjungan = view.findViewById<RelativeLayout>(R.id.RytKunjungan)
        rwytkunjungan.setOnClickListener {
            val intent = Intent(requireContext(), RiwayatActivity::class.java)
            startActivity(intent)
        }
        val rkmMedis = view.findViewById<RelativeLayout>(R.id.RkmMedis)
        rkmMedis.setOnClickListener {
            val intent = Intent(requireContext(), ProfileRekamMedis::class.java)
            startActivity(intent)
        }
        val edtKtaSandi = view.findViewById<RelativeLayout>(R.id.EdtKataSandi)
        edtKtaSandi.setOnClickListener {
            val intent = Intent(requireContext(), edit_katasandi::class.java)
            startActivity(intent)
        }
        val pustBantuan = view.findViewById<RelativeLayout>(R.id.edtPstBantuan)
        pustBantuan.setOnClickListener {
            val intent = Intent(requireContext(), PusatBantuan::class.java)
            startActivity(intent)
        }
        val pTentang = view.findViewById<RelativeLayout>(R.id.pttentang)
        pTentang.setOnClickListener {
            val intent = Intent(requireContext(), Tentangapk::class.java)
            startActivity(intent)
        }
        val btnKeluar = view.findViewById<RelativeLayout>(R.id.keluarr)
        btnKeluar.setOnClickListener {
            val message: String? = "Apakah kamu yakin Ingin Keluar.?"
            showCustomDialog(message)
        }


        return view
    }
    private fun showCustomDialog(message: String?) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view: TextView = dialog.findViewById(R.id.textView38)
        val closeButton: ImageView = dialog.findViewById(R.id.closeButton)
        val btyes: TextView = dialog.findViewById(R.id.buttonkeluar)
        view.text = message

        btyes.setOnClickListener {
            logoutUser()
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun logoutUser() {

        val sharedPreferences = requireActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        val intent = Intent(requireActivity(), login::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }

}