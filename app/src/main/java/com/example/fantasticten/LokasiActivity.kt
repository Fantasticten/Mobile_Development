package com.example.fantasticten

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LokasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi)

        val cardView: ImageView = findViewById(R.id.lokasiGo)
        cardView.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this@LokasiActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view: TextView = dialog.findViewById(R.id.textView38)
        val closeButton: ImageView = dialog.findViewById(R.id.closeButton)
        val btyes: Button = dialog.findViewById(R.id.buttonkeluar)

        view.text = "Izinkan aplikasi untuk melihat lokasi?"

        btyes.text = "Ya, Izinkan"

        btyes.setOnClickListener {
            openGoogleMaps()
            dialog.dismiss()
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun openGoogleMaps() {
        val alamatTujuan = "KeidentalCare, Jl. Sentot Ali Basa No.36, Jati Mudik, Kec. Pariaman Tengah, Kota Pariaman, Sumatera Barat 25519"
        val uri = "http://maps.google.com/maps?saddr=&daddr=$alamatTujuan"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
