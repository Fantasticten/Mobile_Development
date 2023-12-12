package com.example.fantasticten.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fantasticten.NomerAntrian
import com.example.fantasticten.R
import com.example.fantasticten.detai_notifikasi
import com.google.android.material.card.MaterialCardView

class NotifikasiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifikasi, container, false)

        val arNotif: MaterialCardView = view.findViewById(R.id.notif)

        arNotif.setOnClickListener {
            val intent = Intent(requireContext(), detai_notifikasi::class.java)
            startActivity(intent)
        }

        return view
    }

}